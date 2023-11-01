package cn.net.anonymous.workflowconvertserver.workflow;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pojo.file.dto.WorkflowTask;
import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;
import java.io.*;
import java.util.concurrent.Executor;

/**
 * 工作流 - 转换处理器
 *
 * @author anonymous
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ConvertHandler
        implements IConvertHandler{

    private final RedissonClient redissonClient;

    @Qualifier("service-executor")
    private final Executor serviceExecutor;

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 转换任务消费
     *
     * @param record kafka record
     */
    @SneakyThrows
    @KafkaListener(topics = {"workflow_convert_task_topic"})
    public void convert(ConsumerRecord<?, ?> record) {
        log.info("消费成功 - {}", record.toString());
        // 工作流任务
        WorkflowTask task = JSONObject.parseObject((String) record.value(), WorkflowTask.class);
        // 异步转码 - 统一格式
        serviceExecutor.execute(() -> {
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-hwaccel");
            ffmpeg.addArgument("videotoolbox");
            ffmpeg.addArgument("-ss");
            ffmpeg.addArgument("00:00:00");
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(task.getFolder() + "/" + task.getPieceName() + "." + task.getPieceType());
            ffmpeg.addArgument("-vcodec");
            ffmpeg.addArgument("libx264");
            ffmpeg.addArgument("-q");
            ffmpeg.addArgument("0");
//            ffmpeg.addArgument("-s");
//            ffmpeg.addArgument("1280x720");
//            ffmpeg.addArgument("-c:v");
//            ffmpeg.addArgument("libx264");
//            ffmpeg.addArgument("-b:v");
//            ffmpeg.addArgument("800k");
//            ffmpeg.addArgument("-s");
//            ffmpeg.addArgument("1280x720");
//            ffmpeg.addArgument("-r");
//            ffmpeg.addArgument("15");
//            ffmpeg.addArgument("-g");
//            ffmpeg.addArgument("15");
//            ffmpeg.addArgument("-ar");
//            ffmpeg.addArgument("48000");
//            ffmpeg.addArgument("-b:a");
//            ffmpeg.addArgument("128k");
//            ffmpeg.addArgument("-y");
            ffmpeg.addArgument(task.getFolder() + "/" + task.getConvertedName() + "." + task.getConvertedType());
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
            // 转码成功后通知 Monitor
            kafkaTemplate.send("workflow_convert_ack_topic", JSONObject.toJSONString(task));
        });
    }

    /* 阻塞读取标准输出内容 */
    @SneakyThrows
    private void block(BufferedReader reader) {
        String s;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
    }
}