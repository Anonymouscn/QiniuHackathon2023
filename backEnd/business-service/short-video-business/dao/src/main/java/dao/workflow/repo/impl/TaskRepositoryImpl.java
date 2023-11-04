package dao.workflow.repo.impl;

import dao.workflow.entity.Task;
import dao.workflow.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * 工作流任务信息数据接口实现
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl
        implements TaskRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * 获取任务
     *
     * @param taskId 任务 id
     * @return 任务
     */
    @Override
    public Task getTask(String taskId) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("taskId").is(taskId)),
                Task.class
        );
    }

    /**
     * 创建并保存任务
     *
     * @return 任务
     */
    @Override
    public Task createTask() {
        return mongoTemplate.save(new Task());
    }

    /**
     * 设置任务状态
     *
     * @param taskId 任务 id
     * @param state  任务状态
     */
    @Override
    public void setState(String taskId, Integer state) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("taskId").is(taskId)),
                Update.update("state", state),
                Task.class
        );
    }

    /**
     * 迭代任务版本
     *
     * @param taskId 任务 id
     */
    @Override
    public void iterativeVersion(String taskId) {
        Task task = getTask(taskId);
        mongoTemplate.save(task.setLatest(task.getLatest() + 1).setState(0));
    }
}