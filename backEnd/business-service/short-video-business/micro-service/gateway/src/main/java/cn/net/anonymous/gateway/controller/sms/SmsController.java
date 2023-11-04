package cn.net.anonymous.gateway.controller.sms;

import api.sms.ISmsService;
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
    @GetMapping("/send/{operation}/{phone}")
    public Result<?> getSms(@PathVariable("phone") String phone, @PathVariable("operation") Integer operation) {
        return Result.success(smsService.sendSms(phone, operation));
    }
}