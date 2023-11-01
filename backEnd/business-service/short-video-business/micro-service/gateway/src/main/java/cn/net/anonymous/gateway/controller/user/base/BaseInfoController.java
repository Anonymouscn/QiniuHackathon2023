package cn.net.anonymous.gateway.controller.user.base;

import api.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pojo.common.dto.SerializableStream;
import vo.Result;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用户基本信息 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Slf4j
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
    @SneakyThrows
    @PostMapping("/avatar/upload")
    public Result<Void> uploadAvatar(@RequestParam("file") MultipartFile avatar) throws IOException {
        // 缓冲区大小
        long bufSize = 8192L;
        // 数据长度
        long len = avatar.getSize();
        // 数据块数量
        long sum = (long) Math.ceil((double) len / bufSize);
        System.out.println("数据块数量: " + sum);
        int threads = (int) Math.ceil((double) sum / 1024);
        if(threads > 4) threads = 4;
        System.out.println("使用线程数: " + threads);
        while(Runtime.getRuntime().freeMemory() < threads * 8192L)
            Thread.sleep(500);
        byte[][] buf = new byte[4][8192];
        InputStream in = avatar.getInputStream();
        // try to get healthy file-server list
        for(int i = 0; i < threads; ++i) {
            int finalI = i;
            new Thread(() -> {
                try {
                    while ((buf[finalI] = in.readNBytes(8192)).length > 0) {
                        userService.uploadAvatar(new SerializableStream(buf[finalI], 8192));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        return Result.success();
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