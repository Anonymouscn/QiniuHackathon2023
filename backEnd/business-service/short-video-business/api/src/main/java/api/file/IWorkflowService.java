package api.file;

import org.springframework.web.multipart.MultipartFile;
import pojo.file.vo.ServerInfo;

/**
 * 工作流服务接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface IWorkflowService {

    /**
     * 获取服务器信息
     *
     * @return 服务器信息
     */
    ServerInfo getServerInfo();

    /**
     * 提交工作流
     *
     * @param file 待处理文件
     * @return 工作流 uuid
     */
    String submitWorkflow(MultipartFile file);
}