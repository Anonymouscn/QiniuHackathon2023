package pojo.file.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 文件元数据
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class FileMeta
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 文件名 */
    private String filename;

    /** 原文件名 */
    private String originFilename;

    /** Content-Type */
    private String contentType;

    /** 生成序列号 */
    private String sequence;
}