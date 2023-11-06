package cn.net.anonymous.gateway.controller.user.base;

import api.user.IUserService;
import dao.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pojo.common.vo.Result;
import pojo.user.dto.UserDto;
import pojo.vaild.ValidGroup;

/**
 * 用户基本信息 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Tag(name = "用户信息 API 接口")
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
    @Operation(summary = "获取用户基本信息接口")
    @Parameters(
            @Parameter(name = "user_id", required = true,
                    description = "用户id", in = ParameterIn.PATH,
                    example = "6546909e5ac07c29104c550e")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @GetMapping("/info/{user_id}")
    public Result<User> getBaseInfo(@PathVariable("user_id")
                                     @NotEmpty(message = "用户id不能为空") String id) {
        return Result.success(userService.getUserInfo(id));
    }

    /**
     * 更新用户基本信息 +
     *
     * @param userDto 用户 dto
     * @return 用户基本信息是否更新成功
     */
    @Operation(summary = "更新用户基本信息接口")
    @Parameters(
            @Parameter(schema = @Schema(implementation = UserDto.class))
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    }),
                    @ApiResponse(responseCode = "5013", description = "业务更新失败", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @PostMapping("/info/update")
    public Result<?> updateBaseInfo(@RequestBody @Validated({ValidGroup.Update.class})
                                        UserDto userDto) {
        return userService.updateProfile(userDto) ?
                Result.success() : Result.businessUpdateError();
    }

    /**
     * 分页搜索用户 +
     *
     * @param no 页码
     * @param size 页面大小
     * @param keyword 关键字
     * @return 分页搜索结果
     */
    @Operation(summary = "分页搜索用户接口")
    @Parameters(
            value = {
                    @Parameter(name = "no", required = true,
                            description = "分页页码", in = ParameterIn.PATH,
                            example = "1"),
                    @Parameter(name = "size", required = true,
                            description = "分页大小", in = ParameterIn.PATH,
                            example = "10"),
                    @Parameter(name = "keyword", description = "搜索关键词",
                            in = ParameterIn.QUERY, example = "GC")
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @GetMapping({"/search/{no}/{size}/{keyword}", "/search/{no}/{size}/",
            "/search/{no}/{size}"})
    public Result<?> queryUser(@PathVariable("no") Integer no,
                               @PathVariable("size") Integer size,
                               @PathVariable(value = "keyword", required = false)
                                   String keyword) {
        return Result.success(userService.queryUser(no, size, keyword));
    }
}