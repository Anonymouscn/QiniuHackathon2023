package pojo.workflow.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 文件 vo
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class FileVo
        implements Serializable {

    /** 文件id */
    @JsonProperty(value = "file_id")
    private Long fileId;

    /** 文件下载路径 */
    @JsonProperty(value = "url")
    private String url;
}