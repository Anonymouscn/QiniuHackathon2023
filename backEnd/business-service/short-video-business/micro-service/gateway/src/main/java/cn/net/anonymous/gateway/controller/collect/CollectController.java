package cn.net.anonymous.gateway.controller.collect;

import api.user.ICollectService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import pojo.common.vo.Result;

/**
 * 用户收藏信息 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @DubboReference
    private ICollectService collectService;

    /**
     * 收藏帖子
     *
     * @param postId 帖子
     * @return 是否收藏成功
     */
    @PostMapping("/{postId}")
    public Result<?> collectPost(@PathVariable("postId") String postId) {
        return collectService.collectPost(new ObjectId(postId), new ObjectId()) ?
                Result.success() : Result.businessDeleteError();
    }

    /**
     * 取消收藏帖子
     *
     * @param postId 帖子 id
     * @return 是否取消成功
     */
    @DeleteMapping("/cancel/{postId}")
    public Result<?> cancelCollectPost(@PathVariable("postId") String postId) {
        return collectService.cancelCollectPost(new ObjectId(postId), new ObjectId()) ?
                Result.success() : Result.businessDeleteError();
    }
}