package com.lvtulife.system.component.security;

import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.Message;
import com.lvtulife.base.utils.JsonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring security 自定义登录点
 */
public class CustomLoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String targetUrl = null;
        String url = request.getRequestURI();

        if (url.indexOf("/system") != -1) {
            // 未登录而访问后台受控资源时，跳转到后台登录页面
            targetUrl = "/system/baseUser/login";
            targetUrl = request.getContextPath() + targetUrl;
            response.sendRedirect(targetUrl);
        } else if (url.startsWith("/api")) {
            Message<Object> obj = new Message<Object>(Code.C501);
            obj.setDetail("用户名或密码错误！");
            JsonUtils.writeJson(obj, response);
        } else {
            // 未登录而访问前台受控资源时，跳转到前台登录页面
            targetUrl = "/system/baseUser/login";
            targetUrl = request.getContextPath() + targetUrl;
            response.sendRedirect(targetUrl);
        }
    }
}
