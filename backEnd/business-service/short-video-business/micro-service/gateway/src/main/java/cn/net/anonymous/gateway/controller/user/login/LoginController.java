package cn.net.anonymous.gateway.controller.user.login;

import api.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.common.vo.Result;
import pojo.user.dto.UserDto;
import pojo.vaild.ValidGroup;

/**
 * 用户登录 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Tag(name = "用户登录 API 接口")
@RestController
@RequestMapping("/user/login")
public class LoginController {

    @DubboReference
    private IUserService userService;

    /**
     * 用户短信登录接口
     *
     * @param userDto 用户 dto
     * @return 登录结果
     */
    @Operation(summary = "用户短信登录接口")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @PostMapping("/sms")
    public Result<?> loginBySms(@RequestBody @Validated({ValidGroup.Login.class})
                                UserDto userDto) {
        return Result.success();
    }

    /**
     * 用户密码登录接口
     *
     * @param userDto 用户 dto
     * @return 登录结果
     */
    @Operation(summary = "用户密码登录接口")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @PostMapping("/passwd")
    public Result<?> loginByPassword(@RequestBody @Validated({ValidGroup.Login.class})
                                     UserDto userDto) {
        return Result.success();
    }
}