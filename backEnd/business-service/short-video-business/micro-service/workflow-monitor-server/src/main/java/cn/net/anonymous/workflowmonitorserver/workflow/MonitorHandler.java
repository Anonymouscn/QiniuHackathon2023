package cn.net.anonymous.workflowmonitorserver.workflow;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.api.RBitSet;
import org.redisson.api.RBucket;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pojo.file.dto.WorkStepMeta;
import pojo.file.dto.WorkflowTask;
import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工作流 - 监视服务处理器
 *
 * @author anonymous
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MonitorHandler
        implements IMonitorHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final RedissonClient redissonClient;

    /**
     * 分发任务消费
     *
     * @param record kafka record
     */
    @KafkaListener(topics = {"workflow_send_task_topic"})
    public void sendTask(ConsumerRecord<String, String> record) {
        log.info("[收到消息] - {}", record.toString());
        WorkflowTask task = JSONObject.parseObject(record.value(), WorkflowTask.class);
        // 获取元数据
        RBucket<WorkStepMeta> meta = redissonClient.getBucket(task.getTaskStateKey());
        // 重复消费
        if(meta.isExists()) {
            handleRepeatConsume(task);
            return;
        }
        meta.set(WorkStepMeta.generateWorkMeta(), Duration.of(15, ChronoUnit.MINUTES));
        // 发送消息给切片处理器
        kafkaTemplate.send("workflow_slice_task_topic", JSONObject.toJSONString(task));
    }

    /* 重复消费处理 */
    private void handleRepeatConsume(WorkflowTask task) {
        RBucket<WorkStepMeta> metaBucket = redissonClient.getBucket(task.getTaskStateKey());
        WorkStepMeta meta = metaBucket.get();
        if(meta.getEndpoint() == WorkStepMeta.ENDPOINT.EMPTY) {
            log.warn("[重复消费] - 任务已存在");
            return;
        }
        // 失败任务重复消费 - 版本变更
        log.warn("[重复消费] - 任务重试");
        int version = meta.getVersion() + 1;
        meta.setVersion(version)
                .setTimesOfFail(0)
                .setEndpoint(WorkStepMeta.ENDPOINT.EMPTY);
        metaBucket.set(meta, Duration.of(15, ChronoUnit.MINUTES));
        // 发送消息给失败断点处理器重做
        sendToRetry(task);
    }

    /* 失败重试 */
    private void sendToRetry(WorkflowTask task) {
        RBucket<WorkStepMeta> metaBucket = redissonClient.getBucket(task.getTaskStateKey());
        WorkStepMeta meta = metaBucket.get();
        switch (meta.getEndpoint()) {
            case CREATE -> {
                metaBucket.delete();
            }
            case SLIDE -> {
                meta.setState(WorkStepMeta.STATE.SLIDING);
                metaBucket.set(meta, Duration.of(15, ChronoUnit.MINUTES));
                kafkaTemplate.send("workflow_slice_task_topic", JSONObject.toJSONString(task));
            }
            case CONVERT -> {
                meta.setState(WorkStepMeta.STATE.CONVERTING);
                metaBucket.set(meta, Duration.of(15, ChronoUnit.MINUTES));
                kafkaTemplate.send("workflow_convert_task_topic", JSONObject.toJSONString(task));
            }
            case MERGE -> {
                meta.setState(WorkStepMeta.STATE.MERGING);
                metaBucket.set(meta, Duration.of(15, ChronoUnit.MINUTES));
                kafkaTemplate.send("workflow_merge_task_topic", JSONObject.toJSONString(task));
            }
            case STREAM -> {
                meta.setState(WorkStepMeta.STATE.STREAMING);
                metaBucket.set(meta, Duration.of(15, ChronoUnit.MINUTES));
                kafkaTemplate.send("workflow_stream_task_topic", JSONObject.toJSONString(task));
            }
            case PUSH -> {
                meta.setState(WorkStepMeta.STATE.PUSHING);
                metaBucket.set(meta, Duration.of(15, ChronoUnit.MINUTES));
                kafkaTemplate.send("workflow_push_task_topic", JSONObject.toJSONString(task));
            }
        }
    }

    /**
     * 切片任务完成应答
     *
     * @param record kafka record
     */
    @KafkaListener(topics = {"workflow_slide_ack_topic"})
    public void slideAck(ConsumerRecord<String, String> record) {
        log.info("[切片完成通知] - {}", record.toString());
        WorkflowTask task = JSONObject.parseObject(record.value(), WorkflowTask.class);
        ConcurrentLinkedDeque<String> list = new ConcurrentLinkedDeque<>();
        File folder = new File(task.getFolder());
        Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .parallel()
                .filter(File::isFile)
                .forEach(s -> {
                    String n = s.getName();
                    Pattern pattern = Pattern.compile("[0-9]+-");
                    Matcher matcher = pattern.matcher(n);
                    if(matcher.find() && matcher.start() == 0) {
                        list.add(n);
                    }
                });
        // 分发转码任务
        list.stream()
                .sorted()
                .forEach(s -> {
                    String pieceName;
                    int end = s.lastIndexOf('.');
                    if(end > 0) {
                        pieceName = s.substring(0, end);
                    } else {
                        pieceName = s;
                    }
                    kafkaTemplate.send("workflow_convert_task_topic",
                            JSONObject.toJSONString(
                                    task.setPieces((long) list.size())
                                            .setPieceName(pieceName)
                                            .setCurrentPiece(getPieceNo(s))
                                            .setConvertedName("formatted-" + pieceName)
                            )
                    );
                });
    }

    /* 获取片段号 */
    private Long getPieceNo(String pieceName) {
        int idx = pieceName.indexOf('-');
        if(idx < 0) throw new RuntimeException("非法片段名");
        return Long.parseLong(pieceName.substring(0, idx));
    }

    /**
     * 转码任务完成应答
     *
     * @param record kafka record
     */
    @KafkaListener(topics = {"workflow_convert_ack_topic"})
    public void convertAck(ConsumerRecord<String, String> record) {
        log.info("[转码完成通知] - {}", record.toString());
        WorkflowTask task = JSONObject.parseObject(record.value(), WorkflowTask.class);
        RReadWriteLock stateSetLock = redissonClient.getReadWriteLock("converted-state-lock-" + task.getTaskId());
        // 上写锁
        while(!stateSetLock.writeLock().tryLock());
        try {
            RBitSet stateSet = redissonClient.getBitSet("converted-state-" + task.getTaskId());
            stateSet.expire(Duration.of(15, ChronoUnit.MINUTES));
            stateSet.set(task.getCurrentPiece());
            long count = stateSet.cardinality();
            // 已完成所有任务片段
            if(count == task.getPieces()) {
                log.info("所有转码任务完成");
                stateSet.clear();
                stateSet.delete();
                // 发送消息给 merge Handler
                kafkaTemplate.send("workflow_merge_task_topic", JSONObject.toJSONString(task));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            // 释放写锁
            stateSetLock.writeLock().unlock();
        }
    }

    /**
     * 合并任务完成应答
     *
     * @param record kafka record
     */
    @KafkaListener(topics = {"workflow_merge_ack_topic"})
    public void mergeAck(ConsumerRecord<String, String> record) {
        log.info("[合并完成通知] - {}", record.toString());
        // 转发消息给流转换处理器
        kafkaTemplate.send("workflow_stream_task_topic", record.value());
    }

    /**
     * 流转换任务完成应答
     *
     * @param record kafka record
     */
    @KafkaListener(topics = {"workflow_stream_ack_topic"})
    public void streamAck(ConsumerRecord<String, String> record) {
        log.info("[流转换完成通知] - {}", record.toString());
        // 转发消息给推送处理器
        kafkaTemplate.send("workflow_push_task_topic", record.value());
    }

    /**
     * 推送任务完成应答
     *
     * @param record kafka record
     */
    @KafkaListener(topics = {"workflow_push_ack_topic"})
    public void pushAck(ConsumerRecord<String, String> record) {
        log.info("[推送完成通知] - {}", record.toString());
        // todo 视频流文件信息持久化
        // todo 告知 Websocket Monitor 通知用户 - 回调接口
        // 转发消息给清理处理器
        kafkaTemplate.send("workflow_clean_task_topic", record.value());
    }

    /**
     * 清理任务完成应答
     * @param record kafka record
     */
    @KafkaListener(topics = {"workflow_clean_ack_topic"})
    public void cleanAck(ConsumerRecord<String, String> record) {
        log.info("[清理完成通知] - {}", record.toString());
    }

    /**
     * 任务出错处理
     *
     * @param record kafka record
     */
    @KafkaListener(topics = {"workflow_err_topic"})
    public void handleErr(ConsumerRecord<String, String> record) {
        log.error("[任务出错通知] - {}", record.toString());
        // todo 对出错次数判断 重试还是结束? - 消息幂等
    }
}