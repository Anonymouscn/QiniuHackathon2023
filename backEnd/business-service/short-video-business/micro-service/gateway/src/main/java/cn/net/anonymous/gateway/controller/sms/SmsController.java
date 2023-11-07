package cn.net.anonymous.gateway.controller.sms;

import api.sms.ISmsService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.common.vo.Result;

/**
 * 短信 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Tag(name = "短信 API 接口")
@RequestMapping("/sms")
@RestController
public class SmsController {

    @DubboReference
    private ISmsService smsService;

    /**
     * 获取验证码
     *
     * @param phone 手机号
     * @param operation 操作码
     * @return 认证 key
     */
    @Operation(summary = "获取验证码接口")
    @Parameters(
            value = {
                    @Parameter(name = "operation", required = true,
                            description = "验证操作码", in = ParameterIn.PATH,
                            example = "1"),
                    @Parameter(name = "phone", required = true,
                            description = "手机号", in = ParameterIn.PATH,
                            example = "1325764872")
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @GetMapping("/send/{operation}/{phone}")
    public Result<?> getSms(@PathVariable("phone") String phone,
                            @PathVariable("operation") Integer operation) {
        return Result.success(smsService.sendSms(phone, operation));
    }
}