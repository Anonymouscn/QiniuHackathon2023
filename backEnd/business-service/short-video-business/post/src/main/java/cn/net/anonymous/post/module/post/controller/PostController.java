package cn.net.anonymous.post.module.post.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 视频内容 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Tag(name = "帖子管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/content")
public class PostController {

}