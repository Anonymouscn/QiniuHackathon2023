package cn.net.anonymous.gateway.controller.post;

import api.post.ICommentService;
import dao.post.entity.Comment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import pojo.common.vo.Page;
import pojo.common.vo.Result;
import pojo.post.dto.CommentDto;

/**
 * 帖子评论信息 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Tag(name = "帖子评论信息 API 接口")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @DubboReference
    private ICommentService commentService;

    /**
     * 分页获取评论信息接口 +
     *
     * @return 评论信息
     */
    @Operation(summary = "分页获取评论信息接口")
    @Parameters(
            value = {
                    @Parameter(name = "post_id", required = true,
                            description = "帖子id", in = ParameterIn.PATH,
                            example = "6546909e5ac07c29104c550e"),
                    @Parameter(name = "no", required = true,
                            description = "分页页码", in = ParameterIn.PATH,
                            example = "1"),
                    @Parameter(name = "size", required = true, in = ParameterIn.PATH,
                            example = "10")
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @GetMapping("/{post_id}/{no}/{size}")
    public Result<Page<Comment>> queryComment(@PathVariable("post_id") String postId,
                                              @PathVariable("no") Integer no,
                                              @PathVariable("size") Integer size) {
        return Result.success(commentService.queryComment(postId, no, size));
    }

    /**
     * 添加评论
     *
     * @return 评论信息
     */
    @Operation(summary = "添加评论接口")
    @Parameters(
            @Parameter(schema = @Schema(implementation = CommentDto.class))
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
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
    @Operation(summary = "回复评论接口")
    @Parameters(
            @Parameter(schema = @Schema(implementation = CommentDto.class))
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
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
    @Operation(summary = "删除评论接口")
    @Parameters(
            @Parameter(name = "id", required = true,
                    description = "待删除帖子id", in = ParameterIn.PATH,
                    example = "6546909e5ac07c29104c550e")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    }),
                    @ApiResponse(responseCode = "5014", description = "业务删除失败", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteComment(@PathVariable("id") String id) {
        return commentService.deleteComment(id) ?
                Result.success() : Result.businessDeleteError();
    }
}