package dao.user.repo.impl;

import dao.user.entity.Likes;
import dao.user.repo.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

/**
 * 点赞信息数据接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl
        implements LikeRepository {

    private final MongoTemplate mongoTemplate;

    // 缓存同步队列
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 点赞帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否点赞成功
     */
    @Override
    public boolean likePost(ObjectId postId, ObjectId userId) {
        mongoTemplate.save(new Likes()
                .setPostId(postId)
                .setUserId(userId),
                "doc_likes");
        return true;
    }

    /**
     * 取消点赞帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否取消点赞成功
     */
    @Override
    public boolean cancelLikePost(ObjectId postId, ObjectId userId) {
        mongoTemplate.remove(
                Query.query(
                        Criteria.where("post_id")
                                .is(postId)
                                .and("user_id")
                                .is(userId)
                ), Likes.class
        );
        return true;
    }

    /**
     * 统计帖子点赞数
     *
     * @param postId 帖子 id
     * @return 帖子点赞总数
     */
    @Override
    public Long countLikesOfPost(ObjectId postId) {
        return mongoTemplate.count(
                Query.query(
                        Criteria.where("post_id").is(postId)),
                Likes.class
        );
    }
}