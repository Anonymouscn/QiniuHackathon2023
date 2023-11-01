package cn.net.anonymous.workflowmergeserver.workflow;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pojo.file.dto.WorkflowTask;
import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 合并处理器
 *
 * @author anonymous
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MergeHandler
        implements IMergeHandler {

    @Qualifier("service-executor")
    private final Executor serviceExecutor;

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 合并任务处理器
     *
     * @param record kafka record
     */
    @KafkaListener(topics = {"workflow_merge_task_topic"})
    public void merge(ConsumerRecord<String, String> record) {
        log.info("消费成功 - {}", record.toString());
        WorkflowTask task = JSONObject.parseObject(record.value(), WorkflowTask.class);
        // 生成合并列表文件
        File folder = new File(task.getFolder());
        File mergeList = generateMergeList(folder);
        // 异步合并
        serviceExecutor.execute(() -> {
            ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
            ffmpeg.addArgument("-f");
            ffmpeg.addArgument("concat");
            ffmpeg.addArgument("-safe");
            ffmpeg.addArgument("0");
            ffmpeg.addArgument("-i");
            ffmpeg.addArgument(mergeList.getAbsolutePath());
            ffmpeg.addArgument("-c");
            ffmpeg.addArgument("copy");
            ffmpeg.addArgument("-y");
            ffmpeg.addArgument(task.getFolder() + "/" + task.getMergedName() + "." + task.getMergeType());
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
            // 合并完成
            log.info("[合并完成]");
            kafkaTemplate.send("workflow_merge_ack_topic", JSONObject.toJSONString(task));
        });
    }

    /* 生成合并列表文件 */
    @SneakyThrows
    private File generateMergeList(File folder) {
        if(!folder.exists() || !folder.isDirectory())
            throw new RuntimeException("非法目录路径");
        Path pathOfMergeList = Paths.get(folder.getPath() + "/" + "merge.list");
        Path mergeList = Files.createFile(pathOfMergeList);
        BufferedWriter writer = Files.newBufferedWriter(mergeList, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        ConcurrentLinkedDeque<String> list = new ConcurrentLinkedDeque<>();
        Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(File::isFile)
                .forEach(s -> {
                    String n = s.getName();
                    Pattern pattern = Pattern.compile("formatted-+[0-9]+-");
                    Matcher matcher = pattern.matcher(n);
                    if(matcher.find() && matcher.start() == 0) {
                        list.add(s.getName());
                    }
                });
        list.stream()
                .sorted(Comparator.comparingInt(MergeHandler::getPieceNo))
                .forEach(s -> {
                    try {
                        writer.write("file ");
                        writer.write(s);
                        writer.write("\r\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        writer.close();
        return mergeList.toFile();
    }

    /* 获取片段编号 */
    private static Integer getPieceNo(String name) {
        name = name.replace("formatted-", "");
        int idx = name.indexOf("-");
        if(idx < 0) throw new RuntimeException("非法命名");
        return Integer.parseInt(name.substring(0, idx));
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