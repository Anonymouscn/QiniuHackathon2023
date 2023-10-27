package cn.net.anonymous.gateway.controller.user.base;

import api.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vo.Result;

/**
 * 用户基本信息 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Tag(name = "用户基本信息接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/base")
public class BaseInfoController {

    @DubboReference
    private IUserService userService;

    @GetMapping("/greet/{name}")
    public String greet(@PathVariable("name") String name) {
        return userService.hello(name);
    }

    /**
     * 获取用户基本信息 (用户id, 姓名, 地区)
     *
     * @param id 用户id
     * @return 用户基本信息
     */
    @Operation(summary = "获取用户基本信息 (用户id, 姓名, 地区)")
    @Parameters(
            @Parameter(name = "id", allowReserved = true)
    )
    @GetMapping("/info/{id}")
    public Result<?> getBaseInfo(@PathVariable("id") String id) {
        return Result.success();
    }

    /**
     * 上传用户头像
     *
     * @param avatar 用户头像
     * @return 用户头像 hash
     */
    @PostMapping("avatar/upload")
    public Result<?> uploadAvatar(@RequestParam(value = "avatar")
                                      MultipartFile avatar) {
        return Result.success(userService.uploadAvatar(avatar));
    }

    /**
     * 获取用户头像
     *
     * @param id 用户id
     * @return 用户头像链接
     */
    @Operation(summary = "获取用户头像")
    @Parameters(
            @Parameter(name = "id", allowReserved = true)
    )
    @GetMapping("/avatar/{id}")
    public Result<?> getAvatar(@PathVariable("id") String id) {
        return Result.success();
    }

    /**
     * 获取用户手机号
     *
     * @param id 用户id
     * @return 用户手机号
     */
    @Operation(summary = "获取用户手机号")
    @Parameters(
            @Parameter(name = "id", allowReserved = true)
    )
    @GetMapping("/phone/{id}")
    public Result<?> getPhone(@PathVariable("id") String id) {
        // todo 手机号脱敏展示
        return Result.success();
    }
}