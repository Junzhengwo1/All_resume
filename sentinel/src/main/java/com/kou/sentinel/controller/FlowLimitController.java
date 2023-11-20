package com.kou.sentinel.controller;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kou.sentinel.model.A;
import com.kou.sentinel.model.B;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FlowLimitController {


    @GetMapping("/testA")
    public String testA() {
        return "_________AAA";
    }


    @GetMapping("/testB")
    public String testB() {
        return "_________bbb";
    }


    @GetMapping("/testC")
    public A testC() {
        // 直接调用 golang的服务
        String body = HttpUtil.get("http://localhost:8089/go/go?id=1");
        JSONObject jsonObject = JSON.parseObject(body);

        A javaObject = JSON.toJavaObject(JSON.parseObject(body), A.class);
        A javaObject2 = JSON.toJavaObject(jsonObject, A.class);
        Object parse = JSON.parse(body);
        A bean = JSONUtil.toBean(body, A.class);
//        A<B> result = JSONUtil.toBean(body, new TypeReference<A<B>>() {
//        }, false);
        return javaObject;

    }
}
