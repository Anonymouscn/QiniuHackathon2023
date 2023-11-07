package dao.workflow.repo;

import dao.workflow.entity.Task;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * 工作流任务信息数据接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface TaskRepository {

    /**
     * 获取任务
     *
     * @param taskId 任务 id
     * @return 任务
     */
    Task getTask(String taskId);

    /**
     * 创建并保存任务
     *
     * @return 任务
     */
    Task createTask();

    /**
     * 设置任务状态
     *
     * @param taskId 任务 id
     * @param state 任务状态
     */
    void setState(String taskId, Integer state);

    /**
     * 迭代任务版本
     *
     * @param taskId 任务 id
     */
    void iterativeVersion(String taskId);


    boolean updateResources(String taskId, String playlistUrl, List<String> resources);
}