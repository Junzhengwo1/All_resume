package com.kou.auth.authority.controller.auth;
/**
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
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 登录
 */
@Api(value = "UserAuthController", tags = "登录")
@RestController
@RequestMapping("anno")
@Slf4j
@SuppressWarnings("all")
public class LoginController {

    @Autowired
    private ValidateCodeService validateCodeService;




    @ApiOperation(value = "验证码",produces = "image/jpeg")
    @GetMapping("/captcha/{key}")
    public void captcha(@PathVariable("key") String key, HttpServletRequest request, HttpServletResponse response) throws IOException {
        validateCodeService.createCode(key,request,response);
    }

    @ApiOperation("登录")
    @PostMapping("login")
    public R login(@RequestBody @Valid LoginParamDTO dto, HttpServletRequest request, BindingResult result){
        //参数校验 JSR3.3
        if (result.hasErrors()) {
            //map key->对应属性 val->对应的校验结果提示
            Map<String, String> map = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return R.success(map);
        }
        @NotEmpty(message = "验证码不能为空") String code = dto.getCode();
        String captcha = (String)request.getSession().getAttribute("captcha");
        System.out.println("验证的验证码"+captcha);
        //开始判断验证码
        if(StringUtils.isEmpty(code)||!captcha.equalsIgnoreCase(code)){
            return R.fail("验证码错误，请重新输入");

        }
        return validateCodeService.loginCheck(dto);
    }
}
