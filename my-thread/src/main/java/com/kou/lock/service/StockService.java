package com.kou.lock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kou.lock.mapper.StockMapper;
import com.kou.lock.pojo.StockPojo;
import org.springframework.stereotype.Service;

@Service
public class StockService extends ServiceImpl<StockMapper,StockPojo> implements IService<StockPojo> {



}
