package com.kou.auth.authority.biz.service.auth.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kou.auth.authority.biz.dao.auth.UserMapper;
import com.kou.auth.authority.biz.service.auth.UserService;
import com.kou.auth.entity.auth.User;
import com.kou.auth.entity.auth.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 业务实现类
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}