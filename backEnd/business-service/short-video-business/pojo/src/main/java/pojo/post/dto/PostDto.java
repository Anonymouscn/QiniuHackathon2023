package pojo.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pojo.common.dto.BaseDto;
import java.io.Serial;
import java.io.Serializable;

/**
 * 帖子 dto
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PostDto
        extends BaseDto
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 帖子 id */
    @JsonProperty(value = "post_id")
    private String postId;

    /** 发布用户 id */
    @JsonProperty(value = "post_by")
    private String postBy;

    /** 帖子文本内容 */
    @JsonProperty(value = "content")
    private String content;
}