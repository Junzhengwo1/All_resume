package com.kou.auth;

import com.wf.captcha.ChineseCaptcha;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void captcha() {
        ChineseCaptcha chineseCaptcha = new ChineseCaptcha();
        String text = chineseCaptcha.text();
        System.out.println(text);

    }


}
