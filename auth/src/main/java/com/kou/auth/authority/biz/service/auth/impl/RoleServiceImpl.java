package com.kou.auth.authority.biz.service.auth.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kou.auth.authority.biz.dao.auth.RoleMapper;
import com.kou.auth.authority.biz.service.auth.RoleService;
import com.kou.auth.entity.auth.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



/**
 * 业务实现类
 * 角色
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
