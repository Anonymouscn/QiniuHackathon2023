package system.valid;

/**
 * 短信模块分组校验
 *
 * @author anonymous
 * @version 1.0
 */
public interface SmsValidGroup
        extends ValidGroup {

    /** 用户注册 */
    interface Registry{}

    /** 用户登录 */
    interface Login{}
}