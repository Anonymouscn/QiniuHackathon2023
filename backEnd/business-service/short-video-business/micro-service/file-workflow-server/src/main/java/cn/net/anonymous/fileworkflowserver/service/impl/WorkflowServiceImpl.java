package cn.net.anonymous.fileworkflowserver.service.impl;

import api.file.IWorkflowService;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.multipart.MultipartFile;
import pojo.file.dto.WorkflowTask;
import pojo.file.vo.ServerInfo;
import util.FileUtil;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

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
        return new ServerInfo().setIp(res.toArray(new String[0])).setPort(port);
    }

    /**
     * 提交工作流
     *
     * @param multipartFile 待处理文件
     * @return 工作流 id
     */
    @SneakyThrows
    @Override
    public String submitWorkflow(MultipartFile multipartFile) {
        String name = multipartFile.getOriginalFilename();
        assert name != null;
        // 提取文件名信息
        String[] info = FileUtil.getFileInfo(name);
        // 创建工作文件夹
        File folder = FileUtil.getFolder(basePath + "/" + info[0]);
        // 存储文件
        File file = FileUtil.multipartFileToFile(multipartFile, folder.getPath() + "/" + info[0] + "." + info[1]);
        // 生成任务流
        WorkflowTask task = WorkflowTask.generateTask(file);
        // 提交任务给工作流监视器
        kafkaTemplate.send("workflow_send_task_topic", JSONObject.toJSONString(task));
        return task.getTaskId();
    }
}