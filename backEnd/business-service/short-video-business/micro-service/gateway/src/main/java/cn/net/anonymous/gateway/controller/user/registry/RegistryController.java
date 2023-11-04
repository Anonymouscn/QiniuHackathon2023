package cn.net.anonymous.gateway.controller.user.registry;

import api.user.IUserService;
import com.google.common.base.Strings;
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
 * 用户注册 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@RestController
@RequestMapping("/user/registry")
public class RegistryController {

    @DubboReference
    private IUserService userService;

    /**
     * 短信注册用户接口
     *
     * @param userDto 用户 dto
     * @return 注册结果
     */
    @PostMapping("/sms")
    public Result<?> registryBySms(@RequestBody @Validated({ValidGroup.Registry.class})
                                       UserDto userDto) {
        return !Strings.isNullOrEmpty(userService.registryBySms(userDto)) ?
                Result.success().setMessage("注册成功") :
                Result.serverError().setMessage("注册失败");
    }
}