package com.kou.lock.controller;


import com.kou.lock.pojo.StockPojo;
import com.kou.lock.service.StockService;
import com.kou.lock.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("stock")
public class StockController {

    @Autowired(required = true)
    private StockService stockService;


    @GetMapping("dec")
    public Res test(){
        List<StockPojo> list = stockService.list();
        return Res.ok().setData(list);

    }


}
