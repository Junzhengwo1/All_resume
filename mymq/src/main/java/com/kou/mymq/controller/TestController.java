package com.kou.mymq.controller;

import com.kou.mymq.fegin.TestFeign;
import com.kou.service.MQProducer;
import com.kou.util.MyDateUtil;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("self/starter")
public class TestController {

    @Autowired
    private MyDateUtil myDateUtil;

    @Autowired
    private MQProducer mqProducer;

    @Autowired
    TestFeign testFeign;
    /**
     * 用自己的坐标玩一下
     * @return 时间
     */
    @GetMapping("/get")
    public String getMyUtilDate(){
        return myDateUtil.getLocalTime();
    }


    @GetMapping("/test")
    public String test(){
        return testFeign.test(0);
    }


}

