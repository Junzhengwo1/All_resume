package com.kou.auth.authority.biz.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kou.auth.authority.biz.dao.common.OptLogMapper;
import com.kou.auth.authority.biz.service.common.OptLogService;
import com.kou.auth.entity.common.OptLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 业务实现类
 * 操作日志
 */
@Slf4j
@Service
public class OptLogServiceImpl extends ServiceImpl<OptLogMapper, OptLog> implements OptLogService {

}