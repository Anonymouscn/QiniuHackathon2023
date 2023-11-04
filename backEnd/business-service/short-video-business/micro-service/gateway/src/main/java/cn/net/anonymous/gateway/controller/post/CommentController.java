package cn.net.anonymous.gateway.controller.post;

import api.post.ICommentService;
import dao.post.entity.Comment;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import pojo.common.vo.Page;
import pojo.common.vo.Result;
import pojo.post.dto.CommentDto;

/**
 * 帖子 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@RestController
@RequestMapping("/post/comment")
public class CommentController {

    @DubboReference
    private ICommentService commentService;

    /**
     * 分页获取评论 +
     *
     * @return 评论信息
     */
    @GetMapping("/{postId}/{no}/{size}")
    public Result<Page<Comment>> queryComment(@PathVariable("postId") String postId,
                                              @PathVariable("no") Integer no,
                                              @PathVariable("size") Integer size) {
        return Result.success(commentService.queryComment(postId, no, size));
    }

    /**
     * 添加评论
     *
     * @return 评论信息
     */
    @PostMapping("/add")
    public Result<?> addComment(@RequestBody CommentDto commentDto) {
        return Result.success(commentService.addComment(commentDto));
    }

    /**
     * 回复评论
     *
     * @param commentDto 评论 dto
     * @return 评论回复信息
     */
    @PostMapping("/reply")
    public Result<?> replyComment(@RequestBody CommentDto commentDto) {
        return Result.success(commentService.replyComment(commentDto));
    }

    /**
     * 删除评论
     *
     * @param id 评论id
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteComment(@PathVariable("id") String id) {
        return commentService.deleteComment(id) ?
                Result.success() : Result.businessDeleteError();
    }
}