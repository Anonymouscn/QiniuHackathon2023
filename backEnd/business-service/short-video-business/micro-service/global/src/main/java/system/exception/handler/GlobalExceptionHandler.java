package system.exception.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vo.Result;

/**
 * 全局异常处理
 *
 * @author anonymous
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return Result.serverError(e.getMessage());
    }

    /**
     * 运行异常捕获
     *
     * @param e 运行异常
     * @return 服务器错误信息
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result<?> runtimeExceptionHandler(RuntimeException e) {
        if(e.getMessage().contains("DuplicateKeyException"))
            return Result.dataExistError();
        return Result.serverError(e.getMessage());
    }

    /**
     * 未知异常捕获
     * @param e 异常
     * @return 服务器错误信息
     */
    @ExceptionHandler(value = Exception.class)
    public Result<Void> exceptionHandler(Exception e) {
        return Result.serverError(e.getMessage());
    }
}