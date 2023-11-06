package cn.net.anonymous.gateway.controller.post;

import api.post.ITagService;
import dao.post.entity.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pojo.common.vo.Page;
import pojo.common.vo.Result;
import pojo.post.dto.TagDto;
import pojo.vaild.ValidGroup;

/**
 * 帖子标签 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@io.swagger.v3.oas.annotations.tags.Tag(name = "帖子标签信息 API 接口")
@RestController
@RequestMapping("/tags")
public class TagController {

    @DubboReference
    private ITagService tagService;

    /**
     * 获取 or 搜索热门标签接口 +
     *
     * @param no 页码
     * @param size 页码大小
     * @param keyword 关键词
     * @return 热门标签分页数据
     */
    @Operation(summary = "获取 or 搜索热门标签接口")
    @Parameters(
            value = {
                    @Parameter(name = "no", required = true,
                            description = "分页页码", in = ParameterIn.PATH,
                            example = "1"),
                    @Parameter(name = "size", required = true,
                            description = "分页大小", in = ParameterIn.PATH,
                            example = "10"),
                    @Parameter(name = "keyword", required = true,
                            description = "搜索关键词", in = ParameterIn.PATH,
                            example = "体育")
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
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
    @Operation(summary = "添加标签接口", description = "仅测试", deprecated = true)
    @PostMapping("/add")
    public Result<?> addTag(@Validated({ValidGroup.Insert.class})
                                @RequestBody TagDto tagDto) {
        return Result.success(tagService.addTag(tagDto.getTags()));
    }
}