package com.kou.auth.authority.biz.service.auth.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kou.auth.authority.biz.dao.auth.ResourceMapper;
import com.kou.auth.authority.biz.service.auth.UserService;
import com.kou.auth.base.entity.R;
import com.kou.auth.base.entity.Token;
import com.kou.auth.dto.auth.LoginDTO;
import com.kou.auth.dto.auth.LoginParamDTO;
import com.kou.auth.dto.auth.UserDTO;
import com.kou.auth.entity.auth.User;
import com.kou.auth.exception.code.ExceptionCode;
import com.kou.auth.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huajieli
 * @create 2021-10-06 18:16
 * <p>
 * 认证管理器类
 */
@Slf4j
@Service
public class AuthManager {

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceMapper resourceMapper;

    //登录认证
    public R<LoginDTO> login(LoginParamDTO dto) {
        R<User> userR = this.validUserAndPassword(dto);
        if(userR.getIsError()){
            return R.fail("认证失败");
        }
        //令牌生成
        Token token = this.genUserToken(userR);
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(userR.getData(),userDTO);
        // todo 权限列表
        //resourceMapper.findVisibleResource();
        LoginDTO loginDTO = LoginDTO.builder().user(userDTO).token(token).build();


        return R.success(loginDTO);

    }

    private Token genUserToken(R<User> userR) {
        Map<String, Object> map = new HashMap<>();
        User user = userR.getData();
        map.put("account",user.getAccount());
        map.put("name",user.getName());
        map.put("org_id",user.getOrgId());
        map.put("id",user.getId());
        map.put("station_id",user.getStationId());
        String tokenStr = JWTUtils.generateToken(map);
        return new Token(tokenStr, null);
    }

    private R<User> validUserAndPassword(LoginParamDTO dto) {
        User one = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getAccount, dto.getAccount()));
        String md5Hex = DigestUtil.md5Hex(dto.getPassword());
        if(ObjectUtil.isNull(one)||!one.getPassword().equals(md5Hex)){
            return R.fail(ExceptionCode.JWT_USER_INVALID);
        }
        return R.success(one);
    }
}
