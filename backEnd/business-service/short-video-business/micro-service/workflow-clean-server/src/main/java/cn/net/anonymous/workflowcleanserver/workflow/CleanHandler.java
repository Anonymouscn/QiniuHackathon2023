package cn.net.anonymous.workflowcleanserver.workflow;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pojo.file.dto.WorkflowTask;
import util.FileUtil;
import java.io.File;

/**
 * 工作流 - 清理处理器
 *
 * @author anonymous
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CleanHandler
        implements ICleanHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 清理任务消费
     *
     * @param record kafka record
     */
    @SneakyThrows
    @KafkaListener(topics = {"workflow_clean_task_topic"})
    public void clean(ConsumerRecord<String, String> record) {
        log.info("消费成功 - {}", record.toString());
        WorkflowTask task = JSONObject.parseObject(record.value(), WorkflowTask.class);
        File folder = new File(task.getFolder());
        FileUtil.forceRemoveFileOrDic(folder);
        log.info("[文件清理成功]");
        kafkaTemplate.send("workflow_clean_ack_topic", JSONObject.toJSONString(task));
    }
}