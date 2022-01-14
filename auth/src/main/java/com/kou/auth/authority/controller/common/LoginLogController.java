package com.kou.auth.authority.controller.common;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端控制器
 * 登录日志
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/loginLog")
@Api(value = "LoginLog", tags = "登录日志")
public class LoginLogController {

}