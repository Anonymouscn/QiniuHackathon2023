package cn.net.anonymous.fileworkflowserver.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传服务接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface IUploadService {

    /**
     * 上传源文件
     *
     * @param file 源文件
     */
    void uploadSource(MultipartFile file);
}