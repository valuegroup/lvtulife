package com.lvtulife.system.component.security;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.utils.*;
import com.lvtulife.system.component.vo.SessionInfo;
import com.lvtulife.system.component.vo.SysUserVo;
import com.lvtulife.system.manager.SysUserGroupManagerI;
import com.lvtulife.system.model.SysRole;
import com.lvtulife.system.model.SysUser;
import com.lvtulife.system.model.SysUserRole;
import com.lvtulife.system.service.SysUserServiceI;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lvtulife on 2016-04-04 .
 */
public class CustomSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Resource(name = "SysUserService")
    private SysUserServiceI sysUserServiceBean;
    @Resource(name = "SysUserGroupManager")
    private SysUserGroupManagerI sysUserGroupManageBean;

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        // 获取真实IP地址
        String ip = IpUtil.getIpAddr(request);

        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#loginName_S_EQ", userDetails.getUsername());
        String resTypeIds = "";// 该字段用于存储当前用户可以访问的资源类型，值从已绑定的角色信息中获取，多个值通过逗号拼接而成，最终值在其他地方处理（如去重）
        String roleIds = "";
        try {
            SysUser user = getSysUserServiceBean().getByFilter(hqlFilter);
            if (user != null) {

                List<SysUserRole> list = getSysUserGroupManageBean().findUserRoles(user.getId());
                for (SysUserRole userRole : list) {
                    SysRole role = getSysUserGroupManageBean().getRole(userRole.getRid());
                    roleIds += role.getId() + ",";

                    if (!org.apache.commons.lang3.StringUtils.isBlank(role.getResTypeIds()))
                        resTypeIds += role.getResTypeIds() + ",";
                }

                SysUserVo userVo = new SysUserVo();
                BeanUtils.copyNotNullProperties(user, userVo);
                userVo.setResTypeIds(resTypeIds);
                userVo.setRoleIds(roleIds);
                userVo.setIp(ip);

                SessionInfo sessionInfo = new SessionInfo();
                sessionInfo.setUser(userVo);
                request.getSession().setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
            } else {
                return;
            }
        } catch (CustomException e) {
            return;
        } catch (Exception e) {
            return;
        }


        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    public SysUserServiceI getSysUserServiceBean() {
        return sysUserServiceBean;
    }

    public SysUserGroupManagerI getSysUserGroupManageBean() {
        return sysUserGroupManageBean;
    }
}
