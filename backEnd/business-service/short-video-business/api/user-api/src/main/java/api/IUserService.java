package api;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.User;

/**
 * 用户基本信息服务接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface IUserService
        extends IService<User> {

    String hello(String name);
}