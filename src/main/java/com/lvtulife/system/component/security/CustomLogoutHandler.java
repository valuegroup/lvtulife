package com.lvtulife.system.component.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/4/18 0018.
 */
public class CustomLogoutHandler implements LogoutHandler {
    public CustomLogoutHandler() {
    }

    public void logout(HttpServletRequest request,
                       HttpServletResponse response, Authentication authentication) {
        System.out.println("CustomLogoutSuccessHandler.logout() is called!");
    }

}
