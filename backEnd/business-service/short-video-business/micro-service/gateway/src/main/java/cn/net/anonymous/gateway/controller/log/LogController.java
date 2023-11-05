package cn.net.anonymous.gateway.controller.log;

import api.log.ILogService;
import dao.log.entity.Log;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.common.vo.Page;
import pojo.common.vo.Result;

/**
 * 日志 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
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
    @GetMapping("/latest/{no}/{size}/{keyword}")
    public Result<Page<Log>> queryLog(@PathVariable("no") Integer no,
                                      @PathVariable("size") Integer size,
                                      @PathVariable("keyword") String keyword) {
        return Result.success();
    }
}