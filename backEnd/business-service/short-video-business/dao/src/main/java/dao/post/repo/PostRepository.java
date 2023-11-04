package dao.post.repo;

import dao.post.entity.Post;
import pojo.common.vo.Page;

/**
 * 帖子数据接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface PostRepository {

    /**
     * 分页查询标签相关帖子
     *
     * @param tagIds  分类标签 id 列表
     * @param no      页码
     * @param size    页面大小
     * @param keyword 关键字
     * @return 标签相关帖子分页结果
     */
    Page<Post> queryOnTag(Integer no, Integer size, String[] tagIds, String keyword);

    /**
     * 获取/查询用户点赞的帖子
     *
     * @param no      页码
     * @param size    页面大小
     * @param keyword 关键词
     * @return 用户点赞的帖子分页数据
     */
    Page<Post> queryPostsLiked(Integer no, Integer size, String userId, String keyword);

    /**
     * 获取/查询用户收藏的帖子
     *
     * @param no 页码
     * @param size 页面大小
     * @param keyword 关键词
     * @return 用户收藏的帖子分页数据
     */
    Page<Post> queryPostsCollected(Integer no, Integer size, String userId, String keyword);


    /**
     * 根据 id 获取帖子
     *
     * @param postId 帖子 id
     * @return 帖子
     */
    Post getPostById(String postId);

    /**
     * 保存或修改帖子
     *
     * @param post 帖子
     * @return 保存/修改后帖子
     */
   Post saveOrUpdatePost(Post post);

    /**
     * 删除帖子
     *
     * @param postId 帖子 id
     * @return 是否删除成功
     */
    boolean deletePost(String postId);

    /**
     * 批量删除帖子
     *
     * @param postIds 帖子 id 集合
     * @return 是否删除成功
     */
    boolean deletePost(String[] postIds);
}