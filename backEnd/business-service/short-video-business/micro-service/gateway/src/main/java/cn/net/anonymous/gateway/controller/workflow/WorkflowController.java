package cn.net.anonymous.gateway.controller.workflow;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件调度 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@RestController
@RequestMapping("/workflow")
@RequiredArgsConstructor
public class WorkflowController {

//    @DubboReference
//    private IScheduleService fileService;

//    /**
//     * 申请文件上传
//     *
//     * @param filename       文件名
//     * @param originFilename 原文件名
//     * @param contentType    Content-Type
//     * @return 文件上传服务器地址
//     */
//    @GetMapping("/upload/apply")
//    public Result<?> applyUpload(@RequestParam("filename") String filename,
//                                 @RequestParam("origin-filename") String originFilename,
//                                 @RequestParam("content-type") String contentType) {
//        return Result.success(fileService.applyUpload(12345L,
//                new FileMeta()
//                        .setFilename(filename)
//                        .setOriginFilename(originFilename)
//                        .setContentType(contentType)
//        ));
//    }
}