package pojo.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import pojo.vaild.ValidGroup;
import java.io.Serial;
import java.io.Serializable;

/**
 * 用户 dto
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class UserDto
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 认证凭证 */
    @NotEmpty(message = "非法请求",
            groups = {ValidGroup.Registry.class})
    @JsonProperty("auth")
    private String auth;

    /** 手机号 */
    @NotEmpty(message = "手机号不能为空",
            groups = {ValidGroup.Registry.class})
    @JsonProperty("phone")
    private String phone;

    /** 短信验证码 */
    @NotEmpty(message = "短信验证码不能为空",
            groups = {ValidGroup.Registry.class})
    @JsonProperty("code")
    private String code;

    /** 密码 */
    @NotEmpty(message = "密码不能为空",
            groups = {ValidGroup.Registry.class})
    @JsonProperty("password")
    private String password;

    /** id */
    @NotEmpty(message = "用户id不能为空", groups = {ValidGroup.Update.class})
    @JsonProperty("user_id")
    private String userId;

    private String nickname;

    private Integer sex;
}