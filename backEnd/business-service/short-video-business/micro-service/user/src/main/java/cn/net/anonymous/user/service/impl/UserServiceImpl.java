package cn.net.anonymous.user.service.impl;

import api.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.User;
import mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 用户基本信息服务实现
 *
 * @author anonymous
 * @version 1.0
 */
@DubboService
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements IUserService {

    @Override
    public String hello(String name){
        return "hello " + name;
    }
}