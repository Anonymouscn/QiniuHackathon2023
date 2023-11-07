package cn.net.anonymous.workflowstreamserver.workflow;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pojo.workflow.dto.WorkflowTask;
import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executor;

/**
 * 工作流 - 流转换处理器
 *
 * @author anonymous
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StreamHandler
        implements IStreamHandler {

    @Qualifier("service-executor")
    private final Executor serviceExecutor;

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 流转换任务消费
     *
     * @param record kafka record
     */
    @SneakyThrows
    @KafkaListener(topics = {"workflow_stream_task_topic"})
    public void stream(ConsumerRecord<String, String> record) {
        log.info("消费成功 - {}", record.toString());
        WorkflowTask task = JSONObject.parseObject(record.value(), WorkflowTask.class);
        Path m3u8 = Paths.get(task.getFolder() + "/" + "m3u8");
        File folder = Files.createDirectories(m3u8).toFile();
        // 异步流转换
        serviceExecutor.execute(() -> {
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(task.getFolder() + "/" + task.getMergedName() + "." + task.getMergeType());
            ffmpeg.addArgument("-c:v");
            ffmpeg.addArgument("libx264");
            ffmpeg.addArgument("-c:a");
            ffmpeg.addArgument("aac");
            ffmpeg.addArgument("-strict");
            ffmpeg.addArgument("-2");
            ffmpeg.addArgument("-f");
            ffmpeg.addArgument("hls");
            ffmpeg.addArgument("-hls_list_size");
            ffmpeg.addArgument("0");
            ffmpeg.addArgument("-hls_time");
            ffmpeg.addArgument("5");
            ffmpeg.addArgument(folder + "/" + task.getTargetName() + "." + task.getTargetType());
            try {
                ffmpeg.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                block(br);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            log.info("[流转换完成]");
            // 已完成通知 Monitor
            kafkaTemplate.send("workflow_stream_ack_topic", JSONObject.toJSONString(task));
        });
    }

    /* 阻塞读取标准输出内容 */
    @SneakyThrows
    private static void block(BufferedReader reader) {
        String s;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
    }
}