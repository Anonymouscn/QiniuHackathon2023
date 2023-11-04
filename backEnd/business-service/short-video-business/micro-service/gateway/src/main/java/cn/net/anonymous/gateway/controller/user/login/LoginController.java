package cn.net.anonymous.gateway.controller.user.login;

import api.user.IUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.common.vo.Result;
import pojo.user.dto.UserDto;
import system.valid.ValidGroup;

/**
 * 用户登录与退出 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@RestController
@RequestMapping("/user/login")
public class LoginController {

    @DubboReference
    private IUserService userService;

    /**
     * 短信登录用户接口
     *
     * @param userDto 用户 dto
     * @return 登录结果
     */
    @PostMapping("/sms")
    public Result<?> loginBySms(@RequestBody @Validated({ValidGroup.Login.class})
                                UserDto userDto) {
        return Result.success();
    }

    /**
     * 密码登录用户接口
     *
     * @param userDto 用户 dto
     * @return 登录结果
     */
    @PostMapping("/passwd")
    public Result<?> loginByPassword(@RequestBody @Validated({ValidGroup.Login.class})
                                     UserDto userDto) {

        return Result.success();
    }
}