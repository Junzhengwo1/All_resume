package com.kou.mythread.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kou.mythread.pojo.StockPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockMapper extends BaseMapper<StockPojo> {
}
