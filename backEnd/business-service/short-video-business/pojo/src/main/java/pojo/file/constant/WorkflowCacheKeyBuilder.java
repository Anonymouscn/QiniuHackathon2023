package pojo.file.constant;

/**
 * 工作流缓存键常量
 *
 * @author anonymous
 * @version 1.0
 */
public class WorkflowCacheKeyBuilder {

    public static String buildSliceKey(String taskName) {
        return "slice-" + taskName;
    }

    public static String buildConvertKey(String taskName) {
        return "convert-" + taskName;
    }

    public static String buildStreamKey(String taskName) {
        return "stream-" + taskName;
    }

    public static String buildPushKey(String taskName) {
        return "push-" + taskName;
    }
}