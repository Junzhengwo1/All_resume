package com.kou.mythread.controller;


import com.kou.mythread.pojo.StockPojo;
import com.kou.mythread.service.StockService;
import com.kou.mythread.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test")
public class StockController {

    @Autowired(required = true)
    private StockService stockService;


    @GetMapping("/a")
    public Res test(){
        List<StockPojo> list = stockService.list();
        return Res.ok().setData(list);

    }


}
