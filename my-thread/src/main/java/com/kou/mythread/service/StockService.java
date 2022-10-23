package com.kou.mythread.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kou.mythread.mapper.StockMapper;
import com.kou.mythread.pojo.StockPojo;
import org.springframework.stereotype.Service;

@Service
public class StockService extends ServiceImpl<StockMapper,StockPojo> implements IService<StockPojo> {



}
