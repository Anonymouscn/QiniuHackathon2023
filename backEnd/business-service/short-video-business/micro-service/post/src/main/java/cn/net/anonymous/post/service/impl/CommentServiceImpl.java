package cn.net.anonymous.post.service.impl;

import api.post.ICommentService;
import dao.post.entity.Comment;
import dao.post.repo.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import pojo.common.vo.Page;
import pojo.post.dto.CommentDto;

/**
 * 评论内容服务接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
@RequiredArgsConstructor
public class CommentServiceImpl
        implements ICommentService {

    private final CommentRepository commentRepository;

    /**
     * 分页获取评论
     *
     * @param postId 帖子 id
     * @param no     页码
     * @param size   页面大小
     * @return 评论分页信息
     */
    @Override
    public Page<Comment> queryComment(String postId, Integer no, Integer size) {
        return commentRepository.queryComment(postId, no, size);
    }

    /**
     * 添加评论
     *
     * @param commentDto 评论 dto
     * @return 添加的评论信息
     */
    @Override
    public Comment addComment(CommentDto commentDto) {
        return commentRepository.addComment(commentDto);
    }

    /**
     * 回复评论
     *
     * @param commentDto 评论 dto
     * @return 回复的评论信息
     */
    @Override
    public Comment replyComment(CommentDto commentDto) {
        return commentRepository.replyComment(commentDto);
    }

    /**
     * 删除评论
     *
     * @param commentId 评论 id
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteComment(String commentId) {
        return commentRepository.deleteComment(commentId);
    }
}