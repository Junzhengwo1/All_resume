package com.kou.sentinel.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "流式控制测试接口")
@RestController
public class FlowLimitController {

    @ApiOperation("测试A")
    @GetMapping("/testA")
    public String testA(){
        return "_________AAA";
    }

    @ApiOperation("测试B")
    @GetMapping("/testB")
    public String testB(){
        return "_________bbb";
    }
}
