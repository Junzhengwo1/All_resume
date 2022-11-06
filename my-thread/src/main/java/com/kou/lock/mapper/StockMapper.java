package com.kou.lock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kou.lock.pojo.StockPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockMapper extends BaseMapper<StockPojo> {
}
