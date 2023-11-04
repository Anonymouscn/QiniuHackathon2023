package cn.net.anonymous.gateway.controller.log;

import api.log.ILogService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}