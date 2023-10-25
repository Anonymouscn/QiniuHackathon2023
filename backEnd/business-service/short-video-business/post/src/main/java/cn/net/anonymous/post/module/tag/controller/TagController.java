package cn.net.anonymous.post.module.tag.controller;

import cn.net.anonymous.post.module.tag.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 内容标签 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/content/tag")
public class TagController {

    private final ITagService tagService;
}