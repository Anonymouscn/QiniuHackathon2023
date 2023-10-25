package cn.net.anonymous.user.module.base.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户基本信息 API 接口
 *
 * @author anonymous
 * @version 1.0
 */
@Tag(name = "用户基本信息接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/base-info")
public class BaseInfoController {

}