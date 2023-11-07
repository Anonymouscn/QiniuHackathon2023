package pojo.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 合并信息
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MergeMessage
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long taskId;

    private String folder;

    private String originName;

    private String targetType;
}