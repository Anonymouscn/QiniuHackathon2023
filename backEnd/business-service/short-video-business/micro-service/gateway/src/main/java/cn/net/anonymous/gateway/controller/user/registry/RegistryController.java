package cn.net.anonymous.gateway.controller.user.registry;

import api.user.IUserService;
import com.google.common.base.Strings;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
 * 用户注册 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Tag(name = "用户注册 API 接口")
@RestController
@RequestMapping("/user/registry")
public class RegistryController {

    @DubboReference
    private IUserService userService;

    /**
     * 用户短信注册接口
     *
     * @param userDto 用户 dto
     * @return 注册结果
     */
    @Operation(summary = "用户短信注册接口")
    @Parameters(
            @Parameter(schema = @Schema(implementation = UserDto.class))
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "注册成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "注册失败", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @PostMapping("/sms")
    public Result<?> registryBySms(@RequestBody @Validated({ValidGroup.Registry.class})
                                       UserDto userDto) {
        return !Strings.isNullOrEmpty(userService.registryBySms(userDto)) ?
                Result.success().setMessage("注册成功") :
                Result.serverError().setMessage("注册失败");
    }
}