package dao.post.repo;

import dao.post.entity.Comment;
import pojo.common.vo.Page;
import pojo.post.dto.CommentDto;

/**
 * 评论数据接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface CommentRepository {

    /**
     * 分页获取评论
     *
     * @param postId 帖子 id
     * @param no 页码
     * @param size 页面大小
     * @return 评论分页信息
     */
    Page<Comment> queryComment(String postId, Integer no, Integer size);

    /**
     * 添加评论
     *
     * @param commentDto 评论 dto
     * @return 添加的评论信息
     */
    Comment addComment(CommentDto commentDto);

    /**
     * 回复评论
     *
     * @param commentDto 评论 dto
     * @return 回复的评论信息
     */
    Comment replyComment(CommentDto commentDto);

    /**
     * 删除评论
     *
     * @param commentId 评论 id
     * @return 是否删除成功
     */
    Boolean deleteComment(String commentId);
}