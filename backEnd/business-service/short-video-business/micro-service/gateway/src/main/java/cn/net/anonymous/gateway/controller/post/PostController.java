package cn.net.anonymous.gateway.controller.post;

import api.post.IPostService;
import api.user.IUserService;
import dao.post.entity.Post;
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
import pojo.post.dto.PostDto;
import pojo.post.vo.PostVo;

/**
 * 帖子信息 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Tag(name = "帖子信息 API 接口")
@RestController
@RequestMapping("/post")
public class PostController {

    @DubboReference
    private IPostService postService;

    @DubboReference
    private IUserService userService;

    /**
     * 获取 or 搜索推荐帖子
     *
     * @return 帖子 vo
     */
    @Operation(summary = "获取 or 搜索推荐帖子接口")
    @Parameters(
            value = {
                    @Parameter(name = "no", required = true,
                            description = "分页页码", in = ParameterIn.PATH,
                            example = "1"),
                    @Parameter(name = "size", required = true,
                            description = "页面大小", in = ParameterIn.PATH,
                            example = "10"),
                    @Parameter(name = "keyword", description = "搜索关键词",
                            in = ParameterIn.PATH, example = "iPhone 15")
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @GetMapping("/recommend/{no}/{size}/{keyword}")
    public Result<Page<PostVo>> queryRecommendPost(@PathVariable("no") Integer no,
                                                   @PathVariable("size") Integer size,
                                                   @PathVariable(value = "keyword", required = false) String keyword) {
        return Result.success(postService.queryRecommendPost(no, size, "65464dcc41cd6b3f3186f766", keyword));
    }

    /**
     * 获取 or 搜索标签相关帖子 +
     *
     * @param tags 标签
     * @return 帖子 vo
     */
    @Operation(summary = "获取 or 搜索标签相关帖子接口")
    @Parameters(
            value = {
                    @Parameter(name = "no", required = true,
                            description = "分页页码", in = ParameterIn.PATH,
                            example = "1"),
                    @Parameter(name = "size", required = true,
                            description = "分页大小", in = ParameterIn.PATH,
                            example = "10"),
                    @Parameter(name = "tag_id", description = "标签id",
                            in = ParameterIn.QUERY, example = "65464dcc41cd6b3f3186f766"),
                    @Parameter(name = "keyword", description = "搜索关键词", in = ParameterIn.QUERY,
                            example = "世界杯")
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @GetMapping({"/tag/{no}/{size}"})
    public Result<Page<Post>> queryOnTag(@PathVariable("no") Integer no,
                                           @PathVariable("size") Integer size,
                                           @RequestParam(value = "tag_id", required = false) String[] tags,
                                           @RequestParam(value = "keyword", required = false) String keyword) {
        return Result.success(postService.queryOnTag(tags, no, size, keyword));
    }

    /**
     * 获取 or 搜索已点赞帖子 +
     *
     * @return 帖子 vo
     */
    @Operation(summary = "获取 or 搜索已点赞帖子接口")
    @Parameters(
            value = {
                    @Parameter(name = "no", required = true,
                            description = "分页页码", in = ParameterIn.PATH,
                            example = "1"),
                    @Parameter(name = "size", required = true,
                            description = "分页大小", in = ParameterIn.PATH,
                            example = "10"),
                    @Parameter(name = "keyword", description = "搜索关键词",
                            in = ParameterIn.QUERY, example = "AI")
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @GetMapping({"/like/{no}/{size}/{keyword}", "/like/{no}/{size}/", "/like/{no}/{size}"})
    public Result<Page<Post>> queryLikedPost(@PathVariable("no") Integer no,
                                             @PathVariable("size") Integer size,
                                             @PathVariable(value = "keyword", required = false) String keyword) {
        return Result.success(postService.queryPostsLiked(no, size, "65464dcc41cd6b3f3186f766", keyword));
    }

    /**
     * 获取 or 搜索已收藏帖子 +
     *
     * @return 帖子 vo
     */
    @Operation(summary = "获取 or 搜索已收藏帖子接口")
    @Parameters(
            value = {
                    @Parameter(name = "no", required = true,
                            description = "分页页码", in = ParameterIn.PATH,
                            example = "1"),
                    @Parameter(name = "size", required = true,
                            description = "分页大小", in = ParameterIn.PATH,
                            example = "10"),
                    @Parameter(name = "keyword", description = "搜索关键词",
                            in = ParameterIn.QUERY, example = "GC")
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @GetMapping({"/collect/{no}/{size}/{keyword}", "/collect/{no}/{size}/", "/collect/{no}/{size}"})
    public Result<Page<Post>> queryCollectedPost(@PathVariable("no") Integer no,
                                                   @PathVariable("size") Integer size,
                                                   @PathVariable(value = "keyword", required = false) String keyword) {
        return Result.success(postService.queryPostsCollected(no, size, "65464dcc41cd6b3f3186f766", keyword));
    }

    /**
     * 创建草稿帖子接口 +
     *
     * @return 帖子 vo
     */
    @Operation(summary = "创建草稿帖子接口")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @GetMapping("/create")
    public Result<PostVo> createDraftPost() {
        return Result.success(postService.createPost(9L));
    }

    /**
     * 提交 (发布) 帖子接口
     *
     * @return 帖子 vo
     */
    @Operation(summary = "提交 (发布) 帖子接口")
    @Parameters(
            value = {
                    @Parameter(schema = @Schema(implementation = PostDto.class))
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @PostMapping("/submit")
    public Result<PostVo> submitPost(@RequestBody PostDto postDto) {
        return Result.success(postService.submitPost(postDto));
    }

    /**
     * 修改帖子接口
     *
     * @return 帖子 vo
     */
    @Operation(summary = "修改帖子接口")
    @Parameters(
            value = {
                    @Parameter(schema = @Schema(implementation = PostDto.class))
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    }),
                    @ApiResponse(responseCode = "5013", description = "业务更新失败", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @PutMapping("/edit")
    public Result<PostVo> editPost(@RequestBody PostDto postDto) {
        return postService.editPost(postDto) ?
                Result.success() : Result.businessUpdateError();
    }

    /**
     * 删除帖子接口
     *
     * @return 帖子 vo
     */
    @Operation(summary = "删除帖子接口")
    @Parameters(
            value = {
                    @Parameter(name = "post_id_arr", required = true,
                            description = "帖子id数组", in = ParameterIn.PATH,
                            example = "6546909e5ac07c29104c550e, 6546909e5ac98c29104c550e")
            }
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
    @DeleteMapping("/delete/{post_id_arr}")
    public Result<PostVo> deletePost(@PathVariable("post_id_arr") String[] postIds) {
        return postService.deletePost(postIds) ?
                Result.success() : Result.businessDeleteError();
    }
}