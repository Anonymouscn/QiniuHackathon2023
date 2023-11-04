package pojo.sms.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.Accessors;
import pojo.common.dto.BaseDto;
import system.valid.UserValidGroup;
import system.valid.ValidGroup;
import java.io.Serial;
import java.io.Serializable;

/**
 * sms 短信 dto
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SmsDto
        extends BaseDto
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 认证凭证 */
    @NotEmpty(message = "非法请求",
            groups = {ValidGroup.Registry.class})
    private String auth;

    /** 手机号 */
    @NotEmpty(message = "手机号不能为空",
            groups = {ValidGroup.Registry.class})
    private String phone;

    /** 短信验证码 */
    @NotEmpty(message = "短信验证码不能为空",
            groups = {UserValidGroup.Registry.class})
    private String code;
}