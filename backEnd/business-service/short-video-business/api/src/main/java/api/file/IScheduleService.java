package api.file;

import pojo.file.dto.FileMeta;
import pojo.file.vo.ServerInfo;

/**
 * 文件上传服务接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface IScheduleService {

    /**
     * 申请工作流服务器
     *
     * @param userId 用户id
     * @param meta 文件元数据
     * @return 上传服务器地址信息
     */
    ServerInfo applyUpload(Long userId, FileMeta meta);
}