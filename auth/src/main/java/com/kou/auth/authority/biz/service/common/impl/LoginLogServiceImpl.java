package com.kou.auth.authority.biz.service.common.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kou.auth.authority.biz.dao.common.LoginLogMapper;
import com.kou.auth.authority.biz.service.common.LoginLogService;
import com.kou.auth.entity.common.LoginLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 业务实现类
 * 登录日志
 */
@Slf4j
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

}
