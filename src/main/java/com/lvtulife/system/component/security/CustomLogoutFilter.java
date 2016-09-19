package com.lvtulife.system.component.security;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 从LogoutFilter继承一个类，如果还想在退出后加点自己的逻辑（比如注销后，清空额外的Cookie之类\记录退出时间、地点之类），
 * 可重写doFilter方法，但不建议这样，有更好的做法，自行定义logoutSuccessHandler，然后在运行时，通过构造函数注入即可。
 */
public class CustomLogoutFilter extends LogoutFilter {
    public CustomLogoutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
        super(logoutSuccessUrl, handlers);
    }

    public CustomLogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
                              LogoutHandler[] handlers) {
        super(logoutSuccessHandler, handlers);
    }
}
