package api.post;

import dao.post.entity.Post;
import pojo.common.vo.Page;
import pojo.post.dto.PostDto;
import pojo.post.vo.PostVo;

/**
 * 帖子服务接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface IPostService {

    /**
     * 分页查询推荐帖子
     *
     * @param no 页码
     * @param size 页面大小
     * @return 推荐帖子分页结果
     */
    Page<PostVo> queryRecommendPost(Integer no, Integer size, String userId, String keyword);

    /**
     * 分页查询标签相关帖子
     *
     * @param tagIds 分类标签 id 列表
     * @param no 页码
     * @param size 页面大小
     * @param keyword 关键字
     * @return 标签相关帖子分页结果
     */
    Page<Post> queryOnTag(String[] tagIds, Integer no, Integer size, String keyword);

    /**
     * 分页查询已点赞帖子
     *
     * @param no 页码
     * @param size 页码大小
     * @return 已点赞帖子分页结果
     */
    Page<Post> queryPostsLiked(Integer no, Integer size, String userId, String keyword);

    /**
     * 分页查询已收藏帖子
     *
     * @param no 页码
     * @param size 页面大小
     * @return 已收藏帖子分页结果
     */
    Page<Post> queryPostsCollected(Integer no, Integer size,String userId, String keyword);

    /**
     * 创建帖子
     *
     * @param userId 用户 id
     * @return 帖子 vo
     */
    PostVo createPost(Long userId);

    /**
     * 提交帖子
     *
     * @param postDto 帖子 dto
     * @return 帖子 vo
     */
    PostVo submitPost(PostDto postDto);

    /**
     * 修改帖子
     *
     * @param postDto 帖子 dto
     * @return 帖子 vo
     */
    Boolean editPost(PostDto postDto);

    /**
     * 删除帖子 (可批量)
     * @param idList 帖子 id 列表
     * @return 是否删除成功
     */
    Boolean deletePost(String[] idList);
}