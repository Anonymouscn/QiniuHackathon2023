package cn.net.anonymous.fileworkflowserver.controller;

import api.workflow.IWorkflowService;
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
@RequestMapping("/workflow/upload")
public class UploadController {

    private final IUploadService uploadService;

    private final IWorkflowService workflowService;

    /**
     * 文件上传任务接口 (同步)
     *
     * @param file 待上传z文件
     * @return 任务是否提交成功
     */
    @PostMapping("/files")
    public Result<?> uploadFile(@RequestParam("file") MultipartFile file,
                                @RequestParam("auth") String auth) {
        uploadService.uploadSource(file);
        return Result.success();
    }

    /**
     * 短视频上传接口 (异步处理)
     *
     * @param video 待上传视频
     * @return 任务是否提交成功
     */
    @SneakyThrows
    @PostMapping("/video")
    public Result<?> uploadVideoForPost(
            @RequestParam("video") MultipartFile video,
            @RequestParam("auth") String auth,
            @RequestParam("post_id") String postId) {
        return Result.success(workflowService.submitWorkflow(video, auth, postId));
    }
}