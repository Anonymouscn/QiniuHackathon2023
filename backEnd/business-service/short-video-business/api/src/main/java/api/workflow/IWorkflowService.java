package api.workflow;

import org.springframework.web.multipart.MultipartFile;
import pojo.workflow.vo.ServerInfo;

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
     *
     * @return 工作流 uuid
     */

    /**
     * 提交工作流
     *
     * @param file 待处理视频文件
     * @param auth 上传认证凭证
     * @param postId 草稿帖子 id
     * @return 上传任务id
     */
    String submitWorkflow(MultipartFile file, String auth, String postId);
}