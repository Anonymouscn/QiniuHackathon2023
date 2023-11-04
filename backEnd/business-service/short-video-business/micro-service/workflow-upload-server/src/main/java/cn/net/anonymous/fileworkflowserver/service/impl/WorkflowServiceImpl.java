package cn.net.anonymous.fileworkflowserver.service.impl;

import api.workflow.IWorkflowService;
import com.alibaba.fastjson2.JSONObject;
import dao.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.multipart.MultipartFile;
import pojo.workflow.dto.WorkflowTask;
import pojo.workflow.vo.ServerInfo;
import util.FileUtil;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.UUID;

/**
 * 工作流服务实现
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
@RequiredArgsConstructor
public class WorkflowServiceImpl
        implements IWorkflowService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /** 服务器运行端口 */
    @Value("${server.port}")
    private Integer port;

    private String basePath = "/Users/anonymous/Downloads/tmp/";

    private File baseFolder = FileUtil.getFolder(basePath);

    private final RedissonClient redissonClient;

    private final PostRepository postRepository;

    /**
     * 获取服务器信息
     *
     * @return 服务器信息
     */
    @SneakyThrows
    @Override
    public ServerInfo getServerInfo() {
        ArrayList<String> res = new ArrayList<>();
        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        while (allNetInterfaces.hasMoreElements()){
            NetworkInterface netInterface = allNetInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()){
                InetAddress ip = addresses.nextElement();
                if (ip instanceof Inet4Address
                        && !ip.isLoopbackAddress()
                        && !ip.getHostAddress().contains(":")){
                    res.add(ip.getHostAddress());
                }
            }
        }
        final String key = UUID.randomUUID().toString();
        // 存储value为: 随机生成UUID凭证
        Long timestamp = System.currentTimeMillis();
        RBucket<Long> bucket = redissonClient.getBucket(key);
        bucket.set(timestamp, Duration.of(15, ChronoUnit.MINUTES));
        System.out.println(bucket.get());
        return new ServerInfo()
                .setIp(res.toArray(new String[0]))
                .setPort(port)
                .setAuth(key);
    }

    /**
     * 提交工作流
     *
     * @param multipartFile 待处理文件
     * @return 工作流 id
     */
    @SneakyThrows
    @Override
    public String submitWorkflow(MultipartFile multipartFile, String auth, String postId) {
        RBucket<Long> keyPwd = redissonClient.getBucket(auth);
        // 校验上传凭证
        Long time = keyPwd.get();
        System.out.println(time);
        if(time == null) throw new RuntimeException("非法上传凭证");
        if(System.currentTimeMillis() - time > 30 * 60 * 1000)
            throw new RuntimeException("上传凭证已过期, 请重新获取");
        keyPwd.delete();
        // 校验帖子 id 是否有效
        if(postRepository.getPostById(postId) == null)
            throw new RuntimeException("帖子不存在或已清除");
        String name = multipartFile.getOriginalFilename();
        assert name != null;
        // 提取文件名信息
        String[] info = FileUtil.getFileInfo(name);
        String fake = UUID.randomUUID().toString();
        // 创建工作文件夹
        File folder = FileUtil.getFolder(basePath + "/" + fake);
        // 存储文件
        File file = FileUtil.multipartFileToFile(multipartFile,
                folder.getPath() + "/" + fake + "." + info[1]);
        // 生成任务流
        WorkflowTask task = WorkflowTask.generateTask(file, info[0]);
        task.setPostId(postId);
        // 提交任务给工作流监视器
        kafkaTemplate.send("workflow_send_task_topic", JSONObject.toJSONString(task));
        return task.getTaskId();
    }
}