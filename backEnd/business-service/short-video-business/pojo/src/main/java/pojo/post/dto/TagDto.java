package pojo.post.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pojo.common.dto.BaseDto;
import system.valid.ValidGroup;
import java.io.Serial;
import java.io.Serializable;

/**
 * 标签 dto
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TagDto
        extends BaseDto
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 标签名 */
    @NotEmpty(groups = {ValidGroup.Insert.class})
    private String[] tags;
}