package com.kou.auth.authority.controller.common;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kou.auth.authority.biz.service.common.OptLogService;
import com.kou.auth.entity.common.OptLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端控制器
 * 系统操作日志
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/optLog")
@Api(value = "OptLog", tags = "系统操作日志")
public class OptLogController  {

}