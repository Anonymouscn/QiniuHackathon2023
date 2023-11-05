package api.user;

import org.bson.types.ObjectId;

/**
 * 收藏服务接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface ICollectService {

    /**
     * 收藏帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否收藏成功
     */
    boolean collectPost(ObjectId postId, ObjectId userId);

    /**
     * 取消收藏帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否取消收藏成功
     */
    boolean cancelCollectPost(ObjectId postId, ObjectId userId);

    /**
     * 统计帖子收藏总数
     *
     * @param postId 帖子 id
     * @return 帖子收藏总数
     */
    Long countCollectsOfPost(ObjectId postId);
}