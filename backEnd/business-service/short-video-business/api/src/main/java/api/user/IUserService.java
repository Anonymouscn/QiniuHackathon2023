package api.user;

import com.baomidou.mybatisplus.extension.service.IService;
import dao.user.entity.User;
import pojo.common.dto.SerializableStream;

/**
 * 用户基本信息服务接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface IUserService
        extends IService<User> {

    // just for test connect
    String hello(String name);

    /**
     * 上传用户头像
     *
     * @param avatar 用户头像
     * @return 是否上传成功
     */
    Long uploadAvatar(SerializableStream avatar);
}