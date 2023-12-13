package com.kou.sentinel.controller;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kou.sentinel.model.MyResult;
import com.kou.sentinel.model.Result;
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
    public MyResult testC() {
        // 直接调用 golang的服务
        String body = HttpUtil.get("http://localhost:8089/go/go?id=1");
        JSONObject jsonObject = JSON.parseObject(body);
        //A obj = toObj(body, A.class);


        MyResult javaObject = JSON.toJavaObject(JSON.parseObject(body), MyResult.class);
        MyResult javaObject2 = JSON.toJavaObject(jsonObject, MyResult.class);
        Object parse = JSON.parse(body);
        MyResult bean = JSONUtil.toBean(body,new TypeReference<MyResult<Result>>() {
       } ,false);
//        A<B> result = JSONUtil.toBean(body, new TypeReference<A<B>>() {
//        }, false);
        return javaObject;

    }
    public static <T> T toObj(String str, Class<T> clz) {
        final ObjectMapper jsonMapper = new ObjectMapper();

        try {
            return jsonMapper.readValue(str, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
