package dao.post.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import config.JsonConfig;
import dao.common.entity.BaseData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论实体
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Document("doc_comment")
@CompoundIndexes({
        @CompoundIndex(
                name = "idx_time_like",
                def = "{'gmt_create': -1, likes: 1, content: 1, post_by: 1, replies: 1}"
        )
})
public class Comment
        extends BaseData
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 评论 id */
    @Id
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    @JsonProperty("comment_id")
    private ObjectId commentId;

    /** 帖子 id */
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    @JsonProperty("post_id")
    @Field("post_id")
    private ObjectId postId;

    /** 评论者 id */
    @Field("post_by")
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    @JsonProperty("post_by")
    private ObjectId postBy;

    /** 评论内容 */
    @Field("content")
    private String content;

    /** 点赞人数 */
    @Field("likes")
    private Long likes;

    /** 评论回复 */
    @Field("replies")
    private List<Comment> replies;

    public Comment() {
        this.replies = new ArrayList<>();
    }
}