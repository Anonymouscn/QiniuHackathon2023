package cn.net.anonymous.gateway.controller.log;

import api.log.ILogService;
import dao.log.entity.Log;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.common.vo.Page;
import pojo.common.vo.Result;

/**
 * 日志信息 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Tag(name = "日志信息接口")
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class LogController {

    @DubboReference
    private ILogService logService;

    /**
     * 最新日志查询接口
     *
     * @return 最新日志分页信息
     */
    @Operation(summary = "最新日志查询接口")
    @Parameters(
            value = {
                    @Parameter(name = "no", required = true,
                            description = "分页页码", in = ParameterIn.PATH,
                            example = "1"),
                    @Parameter(name = "size", required = true,
                            description = "分页大小", in = ParameterIn.PATH,
                            example = "10"),
                    @Parameter(name = "keyword", description = "搜索关键词",
                            in = ParameterIn.PATH, example = "anon")
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "响应成功", content = {
                            @Content(schema = @Schema(implementation = Result.class))
                    })
            }
    )
    @GetMapping("/latest/{no}/{size}/{keyword}")
    public Result<Page<Log>> queryLog(@PathVariable("no") Integer no,
                                      @PathVariable("size") Integer size,
                                      @PathVariable("keyword") String keyword) {
        return Result.success();
    }
}