package pojo.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 响应结果
 *
 * @author anonymous
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class Result<T>
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 业务状态码 */
    private Integer code;

    /** 响应信息 */
    private String message;

    /** 响应数据 */
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private T data;

    /** 响应时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    /** 自定义状态码 */
    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public Result(Integer code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public Result(Code code) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.time = LocalDateTime.now();
    }

    public Result(Code code, T data) {
        this(code);
        this.data = data;
    }

    /** 响应成功 */
    public static <T> Result<T> success() {
        return new Result<>(Code.SUCCESS);
    }

    /** 响应成功 */
    public static <T> Result<T> success(T data) {
        return new Result<>(Code.SUCCESS, data);
    }

    /** 业务查询无结果 */
    public static <T> Result<T> noResult() {
        return new Result<>(Code.NO_RESULT);
    }

    /** 用户被封禁 */
    public static <T> Result<T> userBanned() {
        return new Result<>(Code.USER_BANNED);
    }

    /** 用户未登录 */
    public static <T> Result<T> noLogin() {
        return new Result<>(Code.NO_LOGIN);
    }

    /** 无权限访问 */
    public static <T> Result<T> noPermission() {
        return new Result<>(Code.NO_PERMISSION);
    }

    /** 未知 api 服务 */
    public static <T> Result<T> apiNotFound() {
        return new Result<>(Code.API_NOT_FOUND);
    }

    /** 非法请求 */
    public static <T> Result<T> illegalRequest() {
        return new Result<>(Code.ILLEGAL_REQUEST);
    }

    /** 非法参数 */
    public static <T> Result<T> illegalArguments() {
        return new Result<>(Code.ILLEGAL_ARGUMENTS);
    }

    /** 服务器未知错误 */
    public static <T> Result<T> serverError() {
        return new Result<>(Code.SEVER_ERROR);
    }

    /** 服务器未知错误 */
    public static <T> Result<T> serverError(String message) {
        return new Result<>(500, message);
    }

    /** 服务器网络错误 */
    public static <T> Result<T> gatewayError() {
        return new Result<>(Code.NETWORK_ERROR);
    }

    /** 业务插入失败 */
    public static <T> Result<T> businessInsertError() {
        return new Result<>(Code.BUSINESS_INSERT_ERROR);
    }

    /** 业务查询失败 */
    public static <T> Result<T> businessQueryError() {
        return new Result<>(Code.BUSINESS_QUERY_ERROR);
    }

    /** 业务更新失败 */
    public static <T> Result<T> businessUpdateError() {
        return new Result<>(Code.BUSINESS_UPDATE_ERROR);
    }

    /**  业务删除失败 */
    public static <T> Result<T> businessDeleteError() {
        return new Result<>(Code.BUSINESS_DELETE_ERROR);
    }

    /** 数据已存在 */
    public static <T> Result<T> dataExistError() {
        return new Result<T>(Code.DATA_EXIST);
    }

    /** 登录账号错误 */
    public static <T> Result<T> identityError() {
        return new Result<T>(Code.IDENTITY_ERROR);
    }

    /** 登录密码错误 */
    public static <T> Result<T> passwordError() {
        return new Result<T>(Code.PASSWORD_ERROR);
    }

    /** 数据不存在 */
    public static <T> Result<T> dataNotExistError() {
        return new Result<T>(Code.DATA_NOT_EXIST);
    }
}