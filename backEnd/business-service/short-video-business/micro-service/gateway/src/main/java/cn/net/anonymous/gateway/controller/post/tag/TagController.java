package cn.net.anonymous.gateway.controller.post.tag;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 内容标签 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Tag(name = "帖子标签管理")
@RestController
@RequestMapping("/content/tag")
public class TagController {

}