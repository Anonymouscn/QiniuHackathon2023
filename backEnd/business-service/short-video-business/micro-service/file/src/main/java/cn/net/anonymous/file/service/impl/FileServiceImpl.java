package cn.net.anonymous.file.service.impl;

import api.file.IFileService;
import cn.net.anonymous.file.config.OssAccountConfig;
import com.qiniu.util.Auth;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.web.multipart.MultipartFile;
import pojo.file.vo.FileVo;
import java.util.UUID;

/**
 * 文件上传服务实现
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
@RequiredArgsConstructor
public class FileServiceImpl
        implements IFileService {

    private final OssAccountConfig ossAccountConfig;

    /**
     * 上传文件到oss对象存储
     *
     * @param file 文件
     * @return 是否上传成功
     */
    @Override
    public Long uploadToOSS(MultipartFile file) {
        file.getName();
        String token = getAuth()
                .uploadToken(
                        ossAccountConfig.getBucket(),
                        file.getOriginalFilename() != null ?
                                file.getOriginalFilename() :
                                UUID.randomUUID().toString()
                );
        System.out.println(token);
        return null;
    }

    /**
     * 上传图片到oss对象存储
     *
     * @param image 图片
     * @return 是否上传成功
     */
    @Override
    public FileVo uploadImageToOSS(MultipartFile image) {
        uploadToOSS(image);
        return null;
    }

    /**
     * 上传视频到oss对象存储
     *
     * @param video 视频
     * @return 是否上传成功
     */
    @Override
    public Long uploadVideoToOSS(MultipartFile video) {
        return null;
    }

    /* 获取凭证 */
    private Auth getAuth() {
        return Auth.create(ossAccountConfig.getAccessKey(), ossAccountConfig.getSecretKey());
    }
}