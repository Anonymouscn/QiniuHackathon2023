package cn.net.anonymous.fileworkflowserver.controller;

import api.file.IWorkflowService;
import cn.net.anonymous.fileworkflowserver.service.IUploadService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pojo.common.vo.Result;

/**
 * 文件上传 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class UploadController {

    private final IUploadService uploadService;

    private final IWorkflowService workflowService;

    /**
     * 文件上传任务接口
     *
     * @param file 待上传文件
     * @return 任务是否提交成功
     */
    @PostMapping("/files")
    public Result<?> uploadFile(@RequestParam("file") MultipartFile file) {
        uploadService.uploadSource(file);
        return Result.success();
    }

    /**
     * 视频切片上传任务接口
     *
     * @param video 待上传视频
     * @return 任务是否提交成功
     */
    @SneakyThrows
    @PostMapping("/video")
    public Result<?> uploadVideoForPost(@RequestParam("video") MultipartFile video) {
        return Result.success(workflowService.submitWorkflow(video));
    }
}