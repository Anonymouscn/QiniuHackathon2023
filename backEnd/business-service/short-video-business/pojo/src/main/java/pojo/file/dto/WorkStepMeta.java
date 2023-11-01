package pojo.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;

/**
 * 转换元数据
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class WorkStepMeta
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 任务状态 */
    private STATE state;

    /** 任务版本号 */
    private Integer version;

    /** 任务失败次数 */
    private Integer timesOfFail;

    /** 任务失败最大重试次数 */
    private Integer maxRetries;

    /** 任务失败断点 */
    private ENDPOINT endpoint;

    private WorkStepMeta() {}

    public static WorkStepMeta generateWorkMeta() {
        return new WorkStepMeta()
                .setMaxRetries(3)
                .setState(STATE.CREATED)
                .setVersion(0)
                .setEndpoint(ENDPOINT.EMPTY)
                .setTimesOfFail(0);
    }

    /** 失败断点枚举 */
    @AllArgsConstructor
    public enum ENDPOINT {

        EMPTY("空", 0),

        CREATE("创建", 1),

        SLIDE("切片", 2),

        CONVERT("转码", 3),

        MERGE("合并", 4),

        STREAM("流转换", 5),

        PUSH("云上传", 6);

        /** 断点信息 */
        private final String message;

        /** 断点枚举 */
        private final Integer endpoint;
    }

    /** 状态枚举 */
    @AllArgsConstructor
    public enum STATE {

        CREATED("创建", 0),

        SLIDING("切片中", 1),

        SLIDED("切片完成", 2),

        CONVERTING("转码中", 3),

        CONVERTED("转码完成", 4),

        MERGING("合并中", 5),

        MERGED("合并完成", 6),

        STREAMING("流转化中", 7),

        STREAMED("流转化完成", 8),

        PUSHING("云上传中", 9),

        PUSHED("云上传完成", 10),

        FINISH("任务完成", 11),

        CANCELING("任务取消中", 12),

        CANCELED("任务已取消", 13),

        FAIL("任务失败", 14);

        /** 状态信息 */
        private final String message;

        /** 状态枚举 */
        private final Integer state;
    }
}