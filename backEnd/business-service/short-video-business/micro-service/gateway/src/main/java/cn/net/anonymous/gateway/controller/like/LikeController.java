package cn.net.anonymous.gateway.controller.like;

import api.user.ILikeService;
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
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import pojo.common.vo.Result;

/**
 * 用户点赞信息 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Tag(name = "用户点赞信息 API 接口")
@RestController
@RequestMapping("/like")
public class LikeController {

    @DubboReference
    private ILikeService likeService;

    /**
     * 点赞帖子接口
     *
     * @param postId 帖子 id
     * @return 是否点赞成功
     */
    @Operation(summary = "点赞帖子接口")
    @Parameters(
            @Parameter(name = "postId", required = true,
                    description = "帖子id", in = ParameterIn.PATH,
                    example = "6546909e5ac07c29104c550e")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    }),
                    @ApiResponse(responseCode = "5011", description = "业务插入失败", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @PostMapping("/{postId}")
    public Result<?> likePost(@PathVariable("postId") String postId) {
        return likeService.likePost(new ObjectId(postId), new ObjectId()) ?
                Result.success() : Result.businessInsertError();
    }

    /**
     * 取消点赞帖子接口
     *
     * @param postId 帖子 id
     * @return 是否取消成功
     */
    @Operation(summary = "取消点赞帖子接口")
    @Parameters(
            @Parameter(name = "postId", required = true,
                    description = "帖子id", in = ParameterIn.PATH,
                    example = "6546909e5ac07c29104c550e")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    }),
                    @ApiResponse(responseCode = "5014", description = "业务添加失败", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @DeleteMapping("/cancel/{postId}")
    public Result<?> cancelLikePost(@PathVariable("postId") String postId) {
        return likeService.cancelLikePost(new ObjectId(postId), new ObjectId()) ?
                Result.success() : Result.businessDeleteError();
    }
}