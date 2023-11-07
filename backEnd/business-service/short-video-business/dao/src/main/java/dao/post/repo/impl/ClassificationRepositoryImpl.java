package dao.post.repo.impl;

import dao.post.entity.Classification;
import dao.post.repo.ClassificationRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * 帖子标签分类接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
public class ClassificationRepositoryImpl
        implements ClassificationRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * 绑定视频分类标签
     *
     * @param postId post id
     * @param tagId  tag id
     * @return 是否绑定成功
     */
    @Override
    public boolean addClassification(ObjectId postId, ObjectId tagId) {
        mongoTemplate.save(new Classification().setPostId(postId).setTagId(tagId));
        return true;
    }

    /**
     * 删除视频分类标签
     *
     * @param postId post id
     * @param tagId  tag id
     * @return 是否解除成功
     */
    @Override
    public boolean removeClassification(ObjectId postId, ObjectId tagId) {
        return mongoTemplate.remove(
                Query.query(
                        Criteria.where("post_id")
                                .is(postId)
                                .and("tag_id")
                                .is(tagId)
                ), "doc_classification_post_tag")
                .getDeletedCount() > 0;
    }
}