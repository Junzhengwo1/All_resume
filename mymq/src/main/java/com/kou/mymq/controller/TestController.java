package com.kou.mymq.controller;

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

    /**
     * 用自己的坐标玩一下
     * @return 时间
     */
    @GetMapping("/get")
    public String getMyUtilDate(){
        LocalDate localDate = new LocalDate();
        return myDateUtil.getLocalTime();
    }
}

