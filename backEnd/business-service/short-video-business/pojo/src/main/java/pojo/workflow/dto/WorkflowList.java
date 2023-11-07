package pojo.workflow.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 工作流清单
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class WorkflowList
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 是否上传到云存储 */
    private boolean uploadToCloud;

    /** 是否需要转换格式 */
    private boolean transform;

    /** 输出视频格式 */
    private String[] outputType;

    /** 是否推流输出 */
    private boolean streamOutput;

    /** 输出流类型 (dash / hls / flv) */
    private String[] streamType;
}