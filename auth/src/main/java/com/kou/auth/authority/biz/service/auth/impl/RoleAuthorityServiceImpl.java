package com.kou.auth.authority.biz.service.auth.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kou.auth.authority.biz.dao.auth.RoleAuthorityMapper;
import com.kou.auth.authority.biz.service.auth.RoleAuthorityService;
import com.kou.auth.entity.auth.RoleAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务实现类
 * 角色的资源
 */
@Slf4j
@Service
public class RoleAuthorityServiceImpl extends ServiceImpl<RoleAuthorityMapper, RoleAuthority> implements RoleAuthorityService {

}