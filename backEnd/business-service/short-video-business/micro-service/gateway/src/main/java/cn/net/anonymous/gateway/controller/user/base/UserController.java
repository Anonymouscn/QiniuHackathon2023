package cn.net.anonymous.gateway.controller.user.base;

import api.user.IUserService;
import dao.user.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pojo.user.dto.UserDto;
import system.valid.ValidGroup;
import vo.Result;

/**
 * 用户基本信息 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @DubboReference
    private IUserService userService;

    /**
     * 获取用户基本信息 (用户id, 姓名, 地区) +
     *
     * @param id 用户id
     * @return 用户基本信息
     */
    @GetMapping("/info/{id}")
    public Result<User> getBaseInfo(@PathVariable("id")
                                     @NotEmpty(message = "用户id不能为空") String id) {
        return Result.success(userService.getUserInfo(id));
    }

    /**
     * 更新用户基本信息 +
     *
     * @param userDto 用户 dto
     * @return 用户基本信息是否更新成功
     */
    @PostMapping("/info/update")
    public Result<?> updateBaseInfo(@RequestBody @Validated({ValidGroup.Update.class}) UserDto userDto) {
        return userService.updateProfile(userDto) ?
                Result.success() : Result.businessUpdateError();
    }

    /**
     * 分页搜索用户
     *
     * @param no 页码
     * @param size 页面大小
     * @param keyword 关键字
     * @return 分页搜索结果
     */
    @GetMapping("/search/{no}/{size}/{keyword}")
    public Result<?> queryUser(@PathVariable("no") Integer no,
                               @PathVariable("size") Integer size,
                               @PathVariable("keyword") String keyword) {
        return Result.success();
    }
}