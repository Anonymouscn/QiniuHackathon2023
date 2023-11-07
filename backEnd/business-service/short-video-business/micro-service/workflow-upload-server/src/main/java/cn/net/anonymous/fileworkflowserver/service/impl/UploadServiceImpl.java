package cn.net.anonymous.fileworkflowserver.service.impl;

import cn.net.anonymous.fileworkflowserver.config.OssAccountConfig;
import cn.net.anonymous.fileworkflowserver.service.IUploadService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传服务实现
 *
 * @author anonymous
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UploadServiceImpl
        implements IUploadService {

    private final OssAccountConfig config;

    /**
     * 上传源文件
     *
     * @param file 源文件
     */
    @SneakyThrows
    @Override
    public void uploadSource(MultipartFile file) {

    }
}