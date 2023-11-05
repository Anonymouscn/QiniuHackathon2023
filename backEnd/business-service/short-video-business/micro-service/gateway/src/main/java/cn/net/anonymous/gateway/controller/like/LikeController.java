package cn.net.anonymous.gateway.controller.like;

import api.user.ILikeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import pojo.common.vo.Result;

/**
 * 用户点赞信息 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@RestController
@RequestMapping("/like")
public class LikeController {

    @DubboReference
    private ILikeService likeService;

    /**
     * 点赞帖子
     *
     * @param postId 帖子 id
     * @return 是否点赞成功
     */
    @PostMapping("/{postId}")
    public Result<?> likePost(@PathVariable("postId") String postId) {
        return likeService.likePost(new ObjectId(postId), new ObjectId()) ?
                Result.success() : Result.businessInsertError();
    }

    /**
     * 取消点赞帖子
     *
     * @param postId 帖子 id
     * @return 是否取消成功
     */
    @DeleteMapping("/cancel/{postId}")
    public Result<?> cancelLikePost(@PathVariable("postId") String postId) {
        return likeService.cancelLikePost(new ObjectId(postId), new ObjectId()) ?
                Result.success() : Result.businessDeleteError();
    }
}