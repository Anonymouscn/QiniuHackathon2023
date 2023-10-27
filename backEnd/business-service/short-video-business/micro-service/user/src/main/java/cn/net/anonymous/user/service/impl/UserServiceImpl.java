package cn.net.anonymous.user.service.impl;

import api.file.IFileService;
import api.user.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dao.user.entity.User;
import dao.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.web.multipart.MultipartFile;
import pojo.file.vo.FileVo;

/**
 * 用户基本信息服务实现
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
@RequiredArgsConstructor
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements IUserService {

    @DubboReference
    private IFileService fileService;

    @Override
    public String hello(String name){
        return "hello " + name;
    }

    /**
     * 上传用户头像
     *
     * @param avatar 用户头像
     * @return 是否上传成功
     */
    @Override
    public FileVo uploadAvatar(MultipartFile avatar) {
        return fileService.uploadImageToOSS(avatar);
    }
}