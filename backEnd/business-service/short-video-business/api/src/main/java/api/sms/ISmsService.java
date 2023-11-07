package api.sms;

import pojo.sms.dto.SmsDto;

/**
 * 短信服务接口
 *
 * @author anonymous
 * @version 1.0
 */
public interface ISmsService {

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     * @param operation 操作代码
     * @return 是否发送成功
     */
    String sendSms(String phone, Integer operation);

    /**
     * 校验短信验证码
     *
     * @param smsDto 短信 dto
     * @return 短信验证码是否正确
     */
    Boolean checkSms(SmsDto smsDto);
}