package cn.net.anonymous.user.service.impl;

import api.user.ILikeService;
import dao.user.repo.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.bson.types.ObjectId;

/**
 * 点赞服务接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
@RequiredArgsConstructor
public class LikeServiceImpl
        implements ILikeService {

    private final LikeRepository likesRepository;

    /**
     * 点赞帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否点赞成功
     */
    @Override
    public boolean likePost(ObjectId postId, ObjectId userId) {
        return likesRepository.likePost(postId, userId);
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
        return likesRepository.cancelLikePost(postId, userId);
    }

    /**
     * 统计帖子点赞数
     *
     * @param postId 帖子 id
     * @return 帖子点赞总数
     */
    @Override
    public Long countLikesOfPost(ObjectId postId) {
        return likesRepository.countLikesOfPost(postId);
    }
}