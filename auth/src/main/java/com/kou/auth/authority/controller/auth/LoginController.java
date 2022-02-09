package com.kou.auth.authority.controller.auth;
/**
 * 登录认证控制器
 */


import com.kou.auth.base.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 登录
 */
@Api(value = "UserAuthController", tags = "登录")
@RestController
@RequestMapping("anno")
@Slf4j
@SuppressWarnings("all")
public class LoginController {

    @ApiOperation("验证码")
    @GetMapping("/captcha/{key}")
    public R captcha(@PathVariable("key") String key){


        return R.success(key);
    }

}
