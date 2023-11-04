package dao.post.repo.impl;

import dao.post.entity.Comment;
import dao.post.repo.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pojo.common.vo.Page;
import pojo.post.dto.CommentDto;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 评论数据接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl
        implements CommentRepository {

    private final MongoTemplate mongoTemplate;

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
        if(!Pattern.matches("^[a-f0-9]{24}$", postId))
            throw new RuntimeException("非法id");
        Query query = Query.query(Criteria.where("post_id").is(postId));
        List<Comment> comments = mongoTemplate.find(query.skip((long) (no - 1) * size).limit(size), Comment.class);
        long total = mongoTemplate.count(query, Comment.class);
        return new Page<Comment>()
                .setNo(no)
                .setSize(size)
                .setRecords(comments)
                .setTotal(total);
    }

    /**
     * 添加评论
     *
     * @param commentDto 评论 dto
     * @return 添加的评论信息
     */
    @Override
    public Comment addComment(CommentDto commentDto) {
        return mongoTemplate.insert(
                new Comment()
                        .setContent(commentDto.getContent())
                        .setPostId(commentDto.getPostId())
                        .setPostBy(commentDto.getPostBy())
        );
    }

    /**
     * 回复评论
     *
     * @param commentDto 评论 dto
     * @return 回复的评论信息
     */
    @Override
    public Comment replyComment(CommentDto commentDto) {

        return null;
    }

    /**
     * 删除评论
     *
     * @param commentId 评论 id
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteComment(String commentId) {

        return null;
    }
}