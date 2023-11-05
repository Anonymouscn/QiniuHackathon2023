package cn.net.anonymous.gateway.controller.post;

import api.post.ITagService;
import dao.post.entity.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pojo.common.vo.Page;
import pojo.common.vo.Result;
import pojo.post.dto.TagDto;
import pojo.vaild.ValidGroup;

/**
 * 内容标签 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    @DubboReference
    private ITagService tagService;

    /**
     * 获取/搜索热门标签 +
     *
     * @param no 页码
     * @param size 页码大小
     * @param keyword 关键词
     * @return 热门标签分页数据
     */
    @GetMapping({"/{no}/{size}/{keyword}", "/{no}/{size}"})
    public Result<Page<Tag>> getHotTag(@PathVariable("no") Integer no,
                                       @PathVariable("size") Integer size,
                                       @PathVariable(value = "keyword", required = false)
                                   String keyword) {
        return Result.success(tagService.queryHotTag(no, size, keyword));
    }

    /**
     * 添加标签 - 测试 +
     *
     * @param tagDto 标签 dto
     * @return 添加的标签数据
     */
    @PostMapping("/add")
    public Result<?> addTag(@Validated({ValidGroup.Insert.class})
                                @RequestBody TagDto tagDto) {
        return Result.success(tagService.addTag(tagDto.getTags()));
    }
}