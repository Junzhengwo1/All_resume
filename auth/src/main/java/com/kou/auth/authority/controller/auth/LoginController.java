package com.kou.auth.authority.controller.auth;
/**
 * @author huajieli
 * @create 2021-10-05 22:11
 * <p>
 * 登录认证控制器
 */


import com.kou.auth.authority.biz.service.auth.ValidateCodeService;
import com.kou.auth.authority.biz.service.auth.impl.AuthManager;
import com.kou.auth.base.entity.R;
import com.kou.auth.dto.auth.LoginDTO;
import com.kou.auth.dto.auth.LoginParamDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录
 */
@RestController
@RequestMapping("/anno")
@Api(value = "UserAuthController", tags = "登录")
@Slf4j
@SuppressWarnings("all")
public class LoginController {

    @ApiOperation("验证码")
    @GetMapping("/captcha/{key}")
    public R captcha(@PathVariable String key){


        return R.success();
    }

}
