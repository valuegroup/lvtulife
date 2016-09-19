package com.lvtulife.system.component.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * 自己实现的过滤用户请求类，也可以直接使用 FilterSecurityInterceptor
 * <p/>
 * AbstractSecurityInterceptor有三个派生类：
 * FilterSecurityInterceptor，负责处理FilterInvocation，实现对URL资源的拦截。
 * MethodSecurityInterceptor，负责处理MethodInvocation，实现对方法调用的拦截。
 * AspectJSecurityInterceptor，负责处理JoinPoint，主要是用于对切面方法(AOP)调用的拦截。
 * <p/>
 * 还可以直接使用注解对Action方法进行拦截，例如在方法上加：
 *
 * @author Lvtulife
 * @PreAuthorize("hasRole('ROLE_SUPER')")
 */
public class CustomSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    private static Logger logger = LoggerFactory.getLogger(CustomSecurityInterceptor.class);


    @Resource(name = "SecurityMetadataSourceManager")
    private SecurityMetadataSourceManagerImpl securityMetadataSourceManager;

    @Override
    public Class<?> getSecureObjectClass() {
        //下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityMetadataSourceManager;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        // object为FilterInvocation对象
        // super.beforeInvocation(fi);//源码
        // 1.获取请求资源的权限
        //执行 Collection attributes =
        //securityMetadataSource.getAttributes(fi);
        // 2.是否拥有权限
        // this.accessDecisionManager.decide(authenticated, fi, attributes);
        // this.accessDecisionManager.decide(authenticated, fi, attributes);


        //在执行doFilter之前，进行权限的检查
        InterceptorStatusToken token = super.beforeInvocation(fi);
        logger.debug("requestURL:{} \t token:{}", fi.getRequestUrl(), token);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {

    }
}
