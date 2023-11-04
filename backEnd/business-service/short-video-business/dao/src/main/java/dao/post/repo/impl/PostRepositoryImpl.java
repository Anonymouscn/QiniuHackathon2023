package dao.post.repo.impl;

import com.google.common.base.Strings;
import dao.post.entity.Classification;
import dao.post.entity.Post;
import dao.post.repo.PostRepository;
import dao.user.entity.Collects;
import dao.user.entity.Likes;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pojo.common.vo.Page;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 帖子数据接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl
        implements PostRepository {

    private final MongoTemplate mongoTemplate;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Qualifier("system-executor")
    private final Executor systemExecutor;

    @Qualifier("log-executor")
    private final Executor logExecutor;

    /**
     * 分页查询标签相关帖子
     *
     * @param no      页码
     * @param size    页面大小
     * @param tagIds  分类标签 id 列表
     * @param keyword 关键字
     * @return 标签相关帖子分页结果
     */
    @Override
    public Page<Post> queryOnTag(Integer no, Integer size, String[] tagIds, String keyword) {
        Query queryClassifications = new Query();
        if(tagIds != null && tagIds.length > 0) {
            if(Arrays.stream(tagIds)
                    .noneMatch(s -> Pattern.matches("^[a-f0-9]{24}$", s))) {
                throw new RuntimeException("非法id");
            }
            queryClassifications.addCriteria(Criteria.where("tag_id")
                    .in(Arrays.stream(tagIds)
                            .map(ObjectId::new)
                            .collect(Collectors.toList())
                    ));
        }
        List<Classification> classifications =
                mongoTemplate.find(queryClassifications, Classification.class);
        Query query = Query.query(
                Criteria.where("post_id")
                        .in(classifications
                                .parallelStream()
                                .map(Classification::getPostId)
                                .collect(Collectors.toList())));
        List<Post> list = mongoTemplate.find(query.skip((long) (no - 1) * size), Post.class);
        long total = mongoTemplate.count(query, Post.class);
        return new Page<Post>()
                .setNo(no)
                .setSize(size)
                .setRecords(list)
                .setTotal(total);
    }

    /**
     * 获取/查询用户点赞的帖子
     *
     * @param no      页码
     * @param size    页面大小
     * @param keyword 关键词
     * @return 用户点赞的帖子分页数据
     */
    @Override
    public Page<Post> queryPostsLiked(Integer no, Integer size, String userId, String keyword) {
        List<Likes> likes = mongoTemplate.find(
                Query.query(
                        Criteria.where("user_id")
                                .is(new ObjectId(userId))
                ), Likes.class
        );
        List<ObjectId> list = likes.parallelStream()
                .map(Likes::getPostId)
                .toList();
        Criteria criteria = new Criteria("_id").in(list);
        if(!Strings.isNullOrEmpty(keyword))
            criteria.and("content")
                    .regex(".*?\\" + keyword + ".*");
        Query query = new Query(criteria).skip((long) (no - 1) * size).limit(size);
        long count = mongoTemplate.count(new Query(criteria), Post.class);
        return new Page<Post>()
                .setNo(no)
                .setSize(size)
                .setTotal(count)
                .setRecords(mongoTemplate.find(query, Post.class));
    }

    /**
     * 获取/查询用户收藏的帖子
     *
     * @param no      页码
     * @param size    页面大小
     * @param keyword 关键词
     * @return 用户收藏的帖子分页数据
     */
    @Override
    public Page<Post> queryPostsCollected(Integer no, Integer size, String userId, String keyword) {
        List<Collects> collects = mongoTemplate.find(
                Query.query(
                        Criteria.where("user_id")
                                .is(new ObjectId(userId))
                ), Collects.class
        );
        List<ObjectId> list = collects.parallelStream()
                .map(Collects::getPostId)
                .toList();
        Criteria criteria = new Criteria("_id").in(list);
        if(!Strings.isNullOrEmpty(keyword))
            criteria.and("content")
                    .regex(".*?\\" + keyword + ".*");
        Query query = new Query(criteria).skip((long) (no - 1) * size).limit(size);
        long count = mongoTemplate.count(new Query(criteria), Post.class);
        return new Page<Post>()
                .setNo(no)
                .setSize(size)
                .setTotal(count)
                .setRecords(mongoTemplate.find(query, Post.class));
    }

    /**
     * 根据 id 获取帖子
     *
     * @param postId 帖子 id
     * @return 帖子
     */
    @Override
    public Post getPostById(String postId) {
        Query query = Query.query(
                Criteria.where("postId")
                        .is(postId)
        );
        return mongoTemplate.findOne(query, Post.class);
    }

    /**
     * 保存或修改帖子
     *
     * @param post 帖子
     * @return 保存/修改后帖子
     */
    @Override
    public Post saveOrUpdatePost(Post post) {
        return mongoTemplate.save(post);
    }

    /**
     * 删除帖子
     *
     * @param postId 帖子 id
     * @return 是否删除成功
     */
    @Override
    public boolean deletePost(String postId) {
        Query query = Query.query(
                Criteria.where("postId")
                        .is(postId)
                        .and("is_delete")
                        .is(0)
        );
        Update update = Update.update("is_delete", 1);
        if(mongoTemplate.updateFirst(query, update, Post.class).getModifiedCount() <= 0) {
            return false;
        }
        return mongoTemplate.updateFirst(query, update, Post.class).getModifiedCount() > 0;
    }

    /**
     * 批量删除帖子
     *
     * @param postIds 帖子 id 集合
     * @return 是否删除成功
     */
    @Override
    @Transactional
    public boolean deletePost(String[] postIds) {
        Query query = Query.query(
                Criteria.where("postId")
                        .in(postIds, String.class)
                        .and("is_delete")
                        .is(0)
        );
        Update update = Update.update("is_delete", 1);
        if(mongoTemplate.updateMulti(query, update, Post.class).getModifiedCount() <= 0) {
            return false;
        }
        if(mongoTemplate.updateFirst(query, update, Post.class).getModifiedCount() != postIds.length) {
            throw new RuntimeException("帖子删除失败");
        }
        return true;
    }
}