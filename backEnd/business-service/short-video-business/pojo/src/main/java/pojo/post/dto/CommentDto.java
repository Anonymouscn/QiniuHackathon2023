package pojo.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import config.JsonConfig;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import pojo.common.dto.BaseDto;
import pojo.vaild.ValidGroup;
import java.io.Serial;
import java.io.Serializable;

/**
 * 评论 dto
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CommentDto
        extends BaseDto
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 回复评论 id */
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    private ObjectId parent;

    /** 帖子 id */
    @NotEmpty(message = "发布人id不能为空", groups = {ValidGroup.Insert.class})
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    @JsonProperty("post_by")
    private ObjectId postBy;

    /** 评论内容 */
    @NotEmpty(message = "评论内容不能为空", groups = {ValidGroup.Insert.class})
    private String content;

    /** 发表用户id */
    @NotEmpty(message = "帖子id不能为空", groups = {ValidGroup.Insert.class})
    @JsonSerialize(using = JsonConfig.ObjectIdSerializer.class)
    @JsonDeserialize(using = JsonConfig.ObjectIdDeserializer.class)
    @JsonProperty("post_id")
    private ObjectId postId;
}