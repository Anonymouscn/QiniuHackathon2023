package pojo.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import pojo.common.dto.BaseDto;
import pojo.vaild.ValidGroup;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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

    /** 视频上传(提交 workflow) task id */
    @NotBlank(message = "任务id不能为空", groups = {ValidGroup.Insert.class})
    @JsonProperty(value = "task_id")
    private String taskId;

    /** 帖子文本内容 */
    @NotBlank(message = "帖子内容不能为空", groups = {ValidGroup.Insert.class})
    @JsonProperty(value = "content")
    private String content;

    /** 标签名列表 */
    @NotEmpty(message = "标签列表不能为空", groups = {ValidGroup.Insert.class})
    @JsonProperty(value = "tag_name_list")
    private List<String> tagNameList;
}