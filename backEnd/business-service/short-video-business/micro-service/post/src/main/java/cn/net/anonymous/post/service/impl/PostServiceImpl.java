package cn.net.anonymous.post.service.impl;

import api.workflow.IWorkflowService;
import api.post.IPostService;
import dao.post.entity.Post;
import dao.post.repo.ClassificationRepository;
import dao.post.repo.PostRepository;
import dao.post.repo.TagRepository;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import pojo.common.vo.Page;
import pojo.post.dto.PostDto;
import pojo.workflow.vo.ServerInfo;
import pojo.post.vo.PostVo;

import java.util.ArrayList;

/**
 * 视频内容接口实现类
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
@RequiredArgsConstructor
public class PostServiceImpl
        implements IPostService {

    @DubboReference
    private IWorkflowService workflowService;

    private final PostRepository postRepository;

    private final TagRepository tagRepository;

    private final ClassificationRepository classificationRepository;

    /**
     * 分页查询推荐帖子
     *
     * @param no   页码
     * @param size 页面大小
     * @return 推荐帖子分页结果
     */
    @Override
    public Page<PostVo> queryRecommendPost(Integer no, Integer size, String userId, String keyword) {

        return null;
    }

    /**
     * 分页查询标签相关帖子
     *
     * @param tagIds  分类标签 id 列表
     * @param no      页码
     * @param size    页面大小
     * @param keyword 关键字
     * @return 标签相关帖子分页结果
     */
    @Override
    public Page<Post> queryOnTag(String[] tagIds, Integer no, Integer size, String keyword) {
        return postRepository.queryOnTag(no, size, tagIds, keyword);
    }

    /**
     * 获取/查询用户点赞的帖子
     *
     * @param no      页码
     * @param size    页面大小
     * @param keyword 关键词
     * @return 用户点赞的帖子分页数据
     */
    @Override
    public Page<Post> queryPostsLiked(Integer no, Integer size, String userId, String keyword) {
        return postRepository.queryPostsLiked(no, size, userId, keyword);
    }

    /**
     * 获取/查询用户收藏的帖子
     *
     * @param no      页码
     * @param size    页面大小
     * @param keyword 关键词
     * @return 用户收藏的帖子分页数据
     */
    @Override
    public Page<Post> queryPostsCollected(Integer no, Integer size, String userId, String keyword) {
        return postRepository.queryPostsCollected(no, size, userId, keyword);
    }

    /**
     * 创建帖子
     *
     * @param userId 用户 id
     * @return 帖子 id
     */
    @Override
    public PostVo createPost(Long userId) {
        Post post = postRepository.saveOrUpdatePost(new Post());
        ServerInfo info = workflowService.getServerInfo();
        return new PostVo()
                .setPostId(post.getPostId().toHexString())
                .setUploadServer(info);
    }

    /**
     * 提交帖子
     *
     * @param postDto 帖子 dto
     * @return 帖子 id
     */
    @Override
    public PostVo submitPost(PostDto postDto) {
        Post post = postRepository.getPostById(postDto.getPostId());
        post.setContent(post.getContent());
        // 设置标签
        ArrayList<String> tags = new ArrayList<>();

        return new PostVo()
                .setPostId(postRepository.saveOrUpdatePost(post)
                        .getPostId().toHexString());
    }

    /**
     * 修改帖子
     *
     * @param postDto 帖子 dto
     * @return 帖子 vo
     */
    @Override
    public Boolean editPost(PostDto postDto) {
        Post post = postRepository.getPostById(postDto.getPostId());
        BeanUtils.copyProperties(postDto, post);
        return postRepository.saveOrUpdatePost(post) != null;
    }

    /**
     * 删除帖子 (可批量)
     *
     * @param idList 帖子 id 列表
     * @return 是否删除成功
     */
    @Override
    public Boolean deletePost(String[] idList) {
        return postRepository.deletePost(idList);
    }
}