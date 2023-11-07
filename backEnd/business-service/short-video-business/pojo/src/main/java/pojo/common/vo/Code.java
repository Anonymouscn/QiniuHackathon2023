package pojo.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务状态码
 *
 * @author anonymous
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum Code {

    /** 响应成功 */
    SUCCESS(200, "响应成功"),

    /** 业务查询无结果 */
    NO_RESULT(210, "业务查询无结果"),

    /** 用户被封禁 */
    USER_BANNED(4001, "用户被封禁"),

    /** 用户未登录 */
    NO_LOGIN(4002, "用户未登录"),

    /** 无权限访问 */
    NO_PERMISSION(4003, "无权限访问"),

    /** 未知 api 服务 */
    API_NOT_FOUND(4004, "未知 api 服务"),

    /** 非法请求 */
    ILLEGAL_REQUEST(4005, "非法请求"),

    ILLEGAL_ARGUMENTS(4006, "非法参数"),

    /** 请求方法不支持 */
    REQUEST_METHOD_NOT_SUPPORTED(4007, "此接口不支持该请求方式"),

    /** 登录账号错误 */
    IDENTITY_ERROR(4008,"登陆账号错误"),

    /** 登陆密码错误 */
    PASSWORD_ERROR(4009,"登陆密码错误"),

    /** 服务器未知错误 */
    SEVER_ERROR(5000, "服务器未知错误"),

    /** 服务器网络错误 */
    NETWORK_ERROR(5002, "服务网络错误"),

    /** 业务插入失败 */
    BUSINESS_INSERT_ERROR(5011, "业务插入失败"),

    /** 业务查询失败 */
    BUSINESS_QUERY_ERROR(5012, "业务查询失败"),

    /** 业务更新失败 */
    BUSINESS_UPDATE_ERROR(5013, "业务更新失败"),

    /**  业务删除失败 */
    BUSINESS_DELETE_ERROR(5014, "业务删除失败"),

    /** 数据已存在 */
    DATA_EXIST(5015, "数据已存在"),

    /** 教师注册的凭证过期或已被使用 */
    ERROR_VOUCHER(5016,"错误凭证"),

    OPERATE_NOT_ALLOW(5017, "不允许的操作"),

    DATA_NOT_EXIST(5018, "数据不存在");

    /** 业务状态码 */
    private final int code;

    /** 响应信息 */
    private final String message;
}