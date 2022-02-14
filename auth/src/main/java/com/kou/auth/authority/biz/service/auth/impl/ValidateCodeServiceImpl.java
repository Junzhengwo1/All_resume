package com.kou.auth.authority.biz.service.auth.impl;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.kou.auth.authority.biz.dao.auth.UserMapper;
import com.kou.auth.authority.biz.service.auth.ValidateCodeService;
import com.kou.auth.base.entity.R;
import com.kou.auth.dto.auth.LoginDTO;
import com.kou.auth.dto.auth.LoginParamDTO;
import com.kou.auth.utils.SteamUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 验证码服务
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthManager authManager;

    /**
     * 生成验证码 并且缓存
     * @param key
     * @param response
     */
    @Override
    public void createCode(String key, HttpServletRequest request,HttpServletResponse response) throws IOException {
        //这是google的Captcha中默认的生成的验证码文本内容
        String text = defaultKaptcha.createText();
        LocalDate now = LocalDate.now();
        log.info(now.toString()+"验证码 \t"+text);
        //将验证码文本内容放到session中，到时候去获取来比较
        request.getSession().setAttribute("captcha",text);
        BufferedImage image = defaultKaptcha.createImage(text);
        ImageIO.write(image,"jpg",response.getOutputStream());
    }

    @Override
    public R<LoginDTO> loginCheck(LoginParamDTO dto) {
        R<LoginDTO> login = authManager.login(dto);


        return login;
    }
}
