package cn.net.anonymous.gateway.controller.post;

import api.post.IPostService;
import api.user.IUserService;
import dao.post.entity.Post;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import pojo.common.vo.Page;
import pojo.common.vo.Result;
import pojo.post.dto.PostDto;
import pojo.post.vo.PostVo;

/**
 * 帖子 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@RestController
@RequestMapping("/post")
public class PostController {

    @DubboReference
    private IPostService postService;

    @DubboReference
    private IUserService userService;

    /**
     * 获取/搜索推荐帖子
     *
     * @return 帖子 vo
     */
    @GetMapping("/recommend/{no}/{size}/{keyword}")
    public Result<Page<PostVo>> queryRecommendPost(@PathVariable("no") Integer no,
                                                   @PathVariable("size") Integer size,
                                                   @PathVariable(value = "keyword", required = false) String keyword) {
        return Result.success(postService.queryRecommendPost(no, size, "65464dcc41cd6b3f3186f766", keyword));
    }

    /**
     * 获取/搜索标签相关帖子 +
     *
     * @param tags 标签
     * @return 帖子 vo
     */
    @GetMapping({"/tag/{no}/{size}"})
    public Result<Page<Post>> queryOnTag(@PathVariable("no") Integer no,
                                           @PathVariable("size") Integer size,
                                           @RequestParam(value = "tag-id", required = false) String[] tags,
                                           @RequestParam(value = "keyword", required = false) String keyword) {
        return Result.success(postService.queryOnTag(tags, no, size, keyword));
    }

    /**
     * 获取/搜索已点赞帖子 +
     *
     * @return 帖子 vo
     */
    @GetMapping({"/like/{no}/{size}/{keyword}", "/like/{no}/{size}/", "/like/{no}/{size}"})
    public Result<Page<Post>> queryLikedPost(@PathVariable("no") Integer no,
                                             @PathVariable("size") Integer size,
                                             @PathVariable(value = "keyword", required = false) String keyword) {
        return Result.success(postService.queryPostsLiked(no, size, "65464dcc41cd6b3f3186f766", keyword));
    }

    /**
     * 获取/搜索已收藏帖子 +
     *
     * @return 帖子 vo
     */
    @GetMapping({"/collect/{no}/{size}/{keyword}", "/collect/{no}/{size}/", "/collect/{no}/{size}"})
    public Result<Page<Post>> queryCollectedPost(@PathVariable("no") Integer no,
                                                   @PathVariable("size") Integer size,
                                                   @PathVariable(value = "keyword", required = false) String keyword) {
        return Result.success(postService.queryPostsCollected(no, size, "65464dcc41cd6b3f3186f766", keyword));
    }

    /**
     * 收藏帖子
     *
     * @param postId 帖子 id
     * @return 是否收藏成功
     */
    @PostMapping("/collect/{postId}")
    public Result<?> collectPost(@PathVariable("postId") String postId) {
        return Result.success();
    }

    /**
     * 取消收藏帖子
     *
     * @param postId 帖子 id
     * @return 是否取消收藏成功
     */
    @PostMapping("/collect/cancel/{postId}")
    public Result<?> cancelCollectPost(@PathVariable("postId") String postId) {
        return Result.success();
    }

    /**
     * 创建草稿帖子 +
     *
     * @return 帖子 vo
     */
    @GetMapping("/create")
    public Result<PostVo> createDraftPost() {
        return Result.success(postService.createPost(9L));
    }

    /**
     * 提交/发布帖子
     *
     * @return 帖子 vo
     */
    @PostMapping("/submit")
    public Result<PostVo> submitPost(@RequestBody PostDto postDto) {
        return Result.success(postService.submitPost(postDto));
    }

    /**
     * 修改帖子
     *
     * @return 帖子 vo
     */
    @PutMapping("/edit")
    public Result<PostVo> editPost(@RequestBody PostDto postDto) {
        return postService.editPost(postDto) ?
                Result.success() : Result.businessUpdateError();
    }

    /**
     * 删除帖子
     *
     * @return 帖子 vo
     */
    @DeleteMapping("/delete/{postIds}")
    public Result<PostVo> deletePost(@PathVariable("postIds") String[] postIds) {
        return postService.deletePost(postIds) ?
                Result.success() : Result.businessDeleteError();
    }
}