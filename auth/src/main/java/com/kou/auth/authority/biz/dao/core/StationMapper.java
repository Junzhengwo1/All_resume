package com.kou.auth.authority.biz.dao.core;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kou.auth.entity.core.Station;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

//import com.kou.auth.database.mybatis.auth.DataScope;

/**
 * <p>
 * Mapper 接口
 * 岗位
 * </p>
 *
 */
@Repository
public interface StationMapper extends BaseMapper<Station> {
    /**
     * 分页查询岗位信息（含角色）
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<Station> findStationPage(Page page, @Param(Constants.WRAPPER) Wrapper<Station> queryWrapper);
}
