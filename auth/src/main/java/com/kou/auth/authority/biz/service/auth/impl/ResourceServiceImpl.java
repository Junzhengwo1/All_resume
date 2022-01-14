package com.kou.auth.authority.biz.service.auth.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kou.auth.authority.biz.dao.auth.ResourceMapper;
import com.kou.auth.authority.biz.service.auth.ResourceService;
import com.kou.auth.entity.auth.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 业务实现类
 * 资源
 */
@Slf4j
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {


}