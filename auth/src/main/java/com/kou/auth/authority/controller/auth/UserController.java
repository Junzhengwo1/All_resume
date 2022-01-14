package com.kou.auth.authority.controller.auth;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kou.auth.authority.biz.service.auth.RoleService;
import com.kou.auth.authority.biz.service.auth.UserService;
import com.kou.auth.base.entity.SuperEntity;
import com.kou.auth.dto.auth.*;
import com.kou.auth.entity.auth.User;
import com.kou.auth.entity.core.Org;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 前端控制器
 * 用户
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
@Api(value = "User", tags = "用户")
public class UserController  {

}