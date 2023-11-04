package cn.net.anonymous.workflowpushserver.workflow;

import cn.net.anonymous.workflowpushserver.config.OssAccountConfig;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.qiniu.storage.Configuration;
import pojo.workflow.dto.WorkflowTask;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工作流 - 推送处理器
 *
 * @author anonymous
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PushHandler
        implements IPushHandler {

    @Qualifier("service-executor")
    private final Executor serviceExecutor;

    private final OssAccountConfig config;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private UploadManager manager;

    {
        Configuration cfg = new Configuration(Region.region2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        manager = new UploadManager(cfg);
    }

    /**
     * 推送文件流到 oss 对象存储
     * @param stream 文件流
     * @param bucket bucket
     * @param filename 文件名 (名称.类型)
     * @return oss key & hash
     */
    @SneakyThrows
    public DefaultPutRet push(InputStream stream, String bucket, String filename) {
        String upToken = Auth.create(config.getAccessKey(), config.getSecretKey()).uploadToken(bucket);
        Response response = manager.put(
                new BufferedInputStream(stream),
                filename, upToken, null, null
        );
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        log.info("[存储成功] - file: {} - key: {} - hash: {}", filename, putRet.key, putRet.hash);
        return putRet;
    }

    /**
     * 推送流任务消费
     *
     * @param record kafka record
     */
    @SneakyThrows
    @KafkaListener(topics = {"workflow_push_task_topic"})
    public void pushStream(ConsumerRecord<String, String> record) {
        log.info("消费成功 - {}", record.toString());
        WorkflowTask task = JSONObject.parseObject(record.value(), WorkflowTask.class);
        // 缓存数据文件访问路径
        ConcurrentHashMap<String, String> uploadInfo = new ConcurrentHashMap<>();
        File folder = new File(task.getFolder() + "/m3u8");
        if(!folder.exists() || !folder.isDirectory()) {
            log.error("非法m3u8列表路径");
            return;
        }
        // 获取 m3u8 播放列表文件
        Path pathToListFile = Paths.get(folder.getAbsolutePath() + "/" + task.getTargetName() + ".m3u8");
        File listFile = pathToListFile.toFile();
        if(!listFile.exists()) {
            log.error("找不到 m3u8 列表文件");
            return;
        }
        // fake - 文件路径防重复
        String fake = UUID.randomUUID().toString();
        // 异步推送到oss
        serviceExecutor.execute(() -> {
            // 获取待上传 .ts 数据文件
            Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                    .parallel()
                    .forEach(s -> {
                        String name = s.getName();
                        log.warn("上传 - {} - 是否匹配: {}", name, matchDataFile(name));
                        if(matchDataFile(name)) {
                            // 上传文件到 oss
                            File file = new File(folder.getAbsoluteFile() + "/" + name);
                            if(file.exists()) {
                                try {
                                    DefaultPutRet ret = push(
                                            new FileInputStream(file),
                                            config.getBucket(),
                                            fake + "/" + name
                                    );
                                    DownloadUrl url = new DownloadUrl(
                                            config.getDomain(),
                                            false,
                                            ret.key
                                    );
                                    uploadInfo.put(name, url.buildURL());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
            // 修改 m3u8 列表文件
            String uri = "";
            try {
                FileReader reader = new FileReader(listFile);
                BufferedReader bufferedReader = new BufferedReader(reader);;
                CharArrayWriter fixCache = new CharArrayWriter();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if(matchDataFile(line)) {
                        uri = uploadInfo.get(line);
                        line = uri != null ? uri : line;
                    }
                    fixCache.write(line);
                    fixCache.append(System.getProperty("line.separator"));
                }
                bufferedReader.close();
                FileWriter store = new FileWriter(listFile);
                fixCache.writeTo(store);
                store.close();
                // 上传播放列表文件
                DefaultPutRet ret = push(
                        new FileInputStream(listFile),
                        config.getBucket(),
                        fake + "/" + listFile.getName()
                );
                DownloadUrl url = new DownloadUrl(config.getDomain(), false, ret.key);
                uri = url.buildURL();
            } catch (Exception e) {
                log.info("[播放列表上传成功] - {}", uri);
            }
            // 通知 Monitor 推送任务完成
            log.info("[播放列表上传成功] - {}", uri);
//            kafkaTemplate.send("workflow_push_ack_topic", JSONObject.toJSONString(task));
        });
    }

    /* 匹配数据文件 */
    private boolean matchDataFile(String name) {
        Pattern pattern = Pattern.compile("product-+([\\s\\S]*)+.ts");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
}