package dao.user.repo;

import org.bson.types.ObjectId;

/**
 * 收藏信息数据接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface CollectsRepository {

    /**
     * 收藏帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否点赞成功
     */
    boolean collectPost(ObjectId postId, ObjectId userId);

    /**
     * 取消收藏帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否取消点赞成功
     */
    boolean cancelCollectPost(ObjectId postId, ObjectId userId);

    /**
     * 统计帖子收藏数
     *
     * @param postId 帖子 id
     * @return 帖子收藏总数
     */
    Long countCollectsOfPost(ObjectId postId);
}