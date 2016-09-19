package com.lvtulife.system.component.security;

import com.lvtulife.base.utils.SpringContextUtil;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 验证码服务
     */
    @Resource(name = "imageCaptchaService")
    private ImageCaptchaService imageCaptchaService;

    private boolean postOnly = true;
    /**
     * 是否需要验证码
     */
    private boolean isCheckValidateCode = true;
    /**
     * 验证码对应的表单参数名称
     */
    private String validateCodeParameter = "verifyCode";

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            //是否需要校验验证码
            /*if (isCheckValidateCode()) {
                checkValidateCode(request);
            }*/

            String username = this.obtainUsername(request);
            String password = this.obtainPassword(request);
            if (StringUtils.isBlank(username)) {
                username = "";
            }
            if (StringUtils.isBlank(password)) {
                password = "";
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void checkValidateCode(HttpServletRequest request) throws AuthenticationServiceException {
        String captchaId = request.getSession().getId();
        String code = request.getParameter(getValidateCodeParameter());
        //可使用SpringContextUtil.getBean的方式获取对象
        //ImageCaptchaService imageCaptchaService = (ImageCaptchaService) SpringContextUtil.getBean("imageCaptchaService");
        Boolean isCorrect = imageCaptchaService.validateResponseForID(captchaId, code);
        if (!isCorrect) {
            throw new AuthenticationServiceException("验证码验证不通过！");
        }
    }
    public boolean isCheckValidateCode() {
        return isCheckValidateCode;
    }

    public String getValidateCodeParameter() {
        return validateCodeParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}
