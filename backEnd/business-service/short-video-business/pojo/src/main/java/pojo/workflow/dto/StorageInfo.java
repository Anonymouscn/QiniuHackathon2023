package pojo.workflow.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 文件存储信息
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class StorageInfo
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** key */
    private String key;

    /** hash */
    private String hash;
}