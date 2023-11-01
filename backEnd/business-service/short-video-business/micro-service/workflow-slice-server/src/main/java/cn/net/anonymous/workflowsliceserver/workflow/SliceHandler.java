package cn.net.anonymous.workflowsliceserver.workflow;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pojo.file.dto.WorkStepMeta;
import pojo.file.dto.WorkflowTask;
import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executor;

/**
 * 工作流 - 切片处理器
 *
 * @author anonymous
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SliceHandler
        implements ISliceHandler{

    @Qualifier("service-executor")
    private final Executor serviceExecutor;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final RedissonClient redissonClient;

    /**
     * 切片任务消费
     *
     * @param record kafka record
     */
    @KafkaListener(topics = "workflow_slice_task_topic")
    public void slice(ConsumerRecord<String, String> record) {
        log.info("消费成功 - {}", record.toString());
        WorkflowTask task = JSONObject.parseObject(record.value(), WorkflowTask.class);
        RBucket<WorkStepMeta> metaBucket = redissonClient.getBucket(task.getTaskStateKey());
        WorkStepMeta meta = metaBucket.get();
        // 异步分片
        serviceExecutor.execute(() -> {
            metaBucket.set(meta.setState(WorkStepMeta.STATE.SLIDING));
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(task.getFolder() + "/" + task.getSourceName() + "." + task.getSourceType());
            ffmpeg.addArgument("-acodec");
            ffmpeg.addArgument("copy");
            ffmpeg.addArgument("-vcodec");
            ffmpeg.addArgument("copy");
            ffmpeg.addArgument("-f");
            ffmpeg.addArgument("segment");
            ffmpeg.addArgument("-segment_time");
            ffmpeg.addArgument("20");
            ffmpeg.addArgument("-reset_timestamps");
            ffmpeg.addArgument("1");
            ffmpeg.addArgument("-map");
            ffmpeg.addArgument("0:0");
            ffmpeg.addArgument("-map");
            ffmpeg.addArgument("0:1");
            ffmpeg.addArgument(task.getFolder() + "/" + "%d-" + task.getSourceName() + "." + task.getSourceType());
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
            // 切片失败处理
            if(ffmpeg.getProcessExitCode() != 0) {
                int fail = meta.getTimesOfFail() + 1;
                meta.setTimesOfFail(fail);
                // 超过最大重试次数 - 任务失败
                if(fail > meta.getMaxRetries()) {
                    log.error("[切片失败] - 结束");
                    meta.setEndpoint(WorkStepMeta.ENDPOINT.SLIDE);
                    metaBucket.set(meta, Duration.of(15, ChronoUnit.MINUTES));
                    return;
                }
                log.error("[切片失败] - 重试");
            }
            metaBucket.set(meta.setState(WorkStepMeta.STATE.SLIDED));
            log.info("[切片完成]");
            // 已完成通知 Monitor
            kafkaTemplate.send("workflow_slide_ack_topic", JSONObject.toJSONString(task));
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