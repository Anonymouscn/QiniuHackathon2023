package dao.post.repo;

import org.bson.types.ObjectId;

/**
 * 帖子标签分类数据接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface ClassificationRepository {

    /**
     * 绑定视频分类标签
     *
     * @param postId post id
     * @param tagId tag id
     * @return 是否绑定成功
     */
    boolean addClassification(ObjectId postId, ObjectId tagId);

    /**
     * 删除视频分类标签
     *
     * @param postId post id
     * @param tagId tag id
     * @return 是否解除成功
     */
    boolean removeClassification(ObjectId postId, ObjectId tagId);
}