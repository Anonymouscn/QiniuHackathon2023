package cn.net.anonymous.gateway.controller.file;

import api.file.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.common.vo.Result;
import pojo.file.dto.FileMeta;

/**
 * 文件调度 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    @DubboReference
    private IScheduleService fileService;

    /**
     * 申请文件上传
     *
     * @param filename       文件名
     * @param originFilename 原文件名
     * @param contentType    Content-Type
     * @return 文件上传服务器地址
     */
    @GetMapping("/upload/apply")
    public Result<?> applyUpload(@RequestParam("filename") String filename,
                                 @RequestParam("origin-filename") String originFilename,
                                 @RequestParam("content-type") String contentType) {
        return Result.success(fileService.applyUpload(12345L,
                new FileMeta()
                        .setFilename(filename)
                        .setOriginFilename(originFilename)
                        .setContentType(contentType)
        ));
    }
}