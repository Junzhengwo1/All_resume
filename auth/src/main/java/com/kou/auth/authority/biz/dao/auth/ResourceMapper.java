package com.kou.auth.authority.biz.dao.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kou.auth.dto.auth.ResourceQueryDTO;
import com.kou.auth.entity.auth.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 资源
 * </p>
 *
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {
}
