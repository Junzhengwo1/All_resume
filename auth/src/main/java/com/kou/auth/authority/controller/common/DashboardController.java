package com.kou.auth.authority.controller.common;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端控制器
 * 首页
 */
@Slf4j
@Validated
@RestController
@Api(value = "dashboard", tags = "首页")
public class DashboardController {

}