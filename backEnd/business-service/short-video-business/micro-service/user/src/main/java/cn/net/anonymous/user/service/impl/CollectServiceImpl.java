package cn.net.anonymous.user.service.impl;

import api.user.ICollectService;
import dao.user.repo.CollectsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.bson.types.ObjectId;

/**
 * 收藏服务接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
@RequiredArgsConstructor
public class CollectServiceImpl
        implements ICollectService {

    private final CollectsRepository collectsRepository;

    /**
     * 收藏帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否收藏成功
     */
    @Override
    public boolean collectPost(ObjectId postId, ObjectId userId) {
        return collectsRepository.collectPost(postId, userId);
    }

    /**
     * 取消收藏帖子
     *
     * @param postId 帖子 id
     * @param userId 用户 id
     * @return 是否取消收藏成功
     */
    @Override
    public boolean cancelCollectPost(ObjectId postId, ObjectId userId) {
        return collectsRepository.cancelCollectPost(postId, userId);
    }

    /**
     * 统计帖子收藏总数
     *
     * @param postId 帖子 id
     * @return 帖子收藏总数
     */
    @Override
    public Long countCollectsOfPost(ObjectId postId) {
        return collectsRepository.countCollectsOfPost(postId);
    }
}