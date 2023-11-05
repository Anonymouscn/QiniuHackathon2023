package api.user;

import org.bson.types.ObjectId;

/**
 * 点赞服务接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface ILikeService {

    /**
     *  点赞帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否点赞成功
     */
    boolean likePost(ObjectId postId, ObjectId userId);

    /**
     * 取消点赞帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否取消点赞成功
     */
    boolean cancelLikePost(ObjectId postId, ObjectId userId);

    /**
     * 统计帖子点赞数
     *
     * @param postId 帖子 id
     * @return 帖子点赞总数
     */
    Long countLikesOfPost(ObjectId postId);
}