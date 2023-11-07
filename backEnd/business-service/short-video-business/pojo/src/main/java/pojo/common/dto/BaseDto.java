package pojo.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 基础 dto
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class BaseDto
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}