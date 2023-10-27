package api.file;

import org.springframework.web.multipart.MultipartFile;
import pojo.file.vo.FileVo;

/**
 * 文件上传服务接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface IFileService {

    /**
     * 上传文件到oss对象存储
     *
     * @param file 文件
     * @return 是否上传成功
     */
    Long uploadToOSS(MultipartFile file);

    /**
     * 上传图片到oss对象存储
     *
     * @param image 图片
     * @return 是否上传成功
     */
    FileVo uploadImageToOSS(MultipartFile image);

    /**
     * 上传视频到oss对象存储
     * @param video 视频
     * @return 是否上传成功
     */
    Long uploadVideoToOSS(MultipartFile video);
}