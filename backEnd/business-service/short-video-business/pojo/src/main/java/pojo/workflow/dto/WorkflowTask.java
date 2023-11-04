package pojo.workflow.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * 转码信息
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class WorkflowTask
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 待整合帖子 id */
    private String postId;

    /** 任务id - */
    private String taskId;

    /** 原文件名 */
    private String originName;

    /** 任务状态键 */
    private String taskStateKey;

    /** 分片片数 */
    private Long pieces;

    /** 当前片段 */
    private Long currentPiece;

    /** 处理目录 - */
    private String folder;

    /** 源文件名 - */
    private String sourceName;

    /** 源类型 - */
    private String sourceType;

    /** 片段名 */
    private String pieceName;

    /** 片段类型 - */
    private String pieceType;

    /** 转换文件名 */
    private String convertedName;

    /** 转换文件类型 - */
    private String convertedType;

    /** 合并文件名 */
    private String mergedName;

    /** 合并文件类型 */
    private String mergeType;

    /** 目标文件名 (流转换文件名) */
    private String targetName;

    /** 目标文件类型 (流转换文件类型) - */
    private String targetType;

    /** 任务版本号 */
    private Integer version;

    private WorkflowTask() {}

    // =================== 工厂构造 ==================== //

    public static WorkflowTask generateTask(File file, String originName) {
        return generateTask(file, originName,"m3u8");
    }

    public static WorkflowTask generateTask(File file, String originName, String targetType) {
        return generateTask(file, originName, "mp4", targetType);
    }

    public static WorkflowTask generateTask(File file, String originName, String convertType, String targetType) {
        String fullName = file.getName();
        int endIdx = fullName.lastIndexOf('.');
        String name = fullName;
        String type = "unknown";
        if(endIdx > 0) {
            name = fullName.substring(0, endIdx);
            type = fullName.substring(endIdx + 1);
        }
        return new WorkflowTask()
                .setOriginName(originName)
                .setTaskStateKey("state-" + originName)
                .setTaskId(UUID.randomUUID().toString())
                .setFolder(file.getParent())
                .setSourceName(name)
                .setSourceType(type)
                .setPieceType(type)
                .setConvertedType(convertType)
                .setMergedName("merged-" +name)
                .setMergeType(convertType)
                .setTargetName("product-" + originName)
                .setTargetType(targetType)
                .setVersion(0);
    }
}