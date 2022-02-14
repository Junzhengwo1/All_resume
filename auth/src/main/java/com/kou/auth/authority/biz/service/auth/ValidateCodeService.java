package com.kou.auth.authority.biz.service.auth;

import com.kou.auth.base.entity.R;
import com.kou.auth.dto.auth.LoginDTO;
import com.kou.auth.dto.auth.LoginParamDTO;
import org.apache.http.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码
 */
public interface ValidateCodeService {

    /**
     * 验证码
     * @param key
     * @param response
     * @throws IOException
     */
    void createCode(String key, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 登录验证
     * @param dto
     * @return
     */
    R<LoginDTO> loginCheck(LoginParamDTO dto);
}
