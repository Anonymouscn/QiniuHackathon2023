package dao.user.repo.impl;

import dao.user.entity.Collects;
import dao.user.repo.CollectsRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * 收藏信息数据接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
public class CollectsRepositoryImpl
        implements CollectsRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * 收藏帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否点赞成功
     */
    @Override
    public boolean collectPost(ObjectId postId, ObjectId userId) {
        mongoTemplate.save(new Collects()
                .setPostId(postId)
                .setUserId(userId), "doc_collects");
        return true;
    }

    /**
     * 取消收藏帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否取消点赞成功
     */
    @Override
    public boolean cancelCollectPost(ObjectId postId, ObjectId userId) {
        mongoTemplate.remove(
                Query.query(
                        Criteria.where("post_id")
                                .is(postId)
                                .and("user_id")
                                .is(userId)
                ), Collects.class);
        return true;
    }

    /**
     * 统计帖子收藏数
     *
     * @param postId 帖子 id
     * @return 帖子点赞总数
     */
    @Override
    public Long countCollectsOfPost(ObjectId postId) {
        return mongoTemplate.count(
                Query.query(
                        Criteria.where("post_id").is(postId)),
                Collects.class
        );
    }
}