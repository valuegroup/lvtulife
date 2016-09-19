package com.lvtulife.system.component.security;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.system.manager.SysUserGroupManagerI;
import com.lvtulife.system.model.SysRole;
import com.lvtulife.system.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;

/**
 * 获取用户信息
 * 源于：AbstractUserDetailsAuthenticationProvider
 * 该类实现 UserDetails 接口，该类在验证成功后会被保存在当前回话的principal对象中
 * <p/>
 * 获得对象的方式：
 * UserPrincipal webUserDetails = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * <p/>
 * 或在JSP中：
 * <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 * <sec:authentication property="principal.username"/>
 * <p/>
 * 如果需要包括用户的其他属性，可以在该类中增加相应属性即可
 *
 * @author Lvtulife
 */
@Component(value = "UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    /**
     * 注入用户组综合业务逻辑
     */
    @Resource(name = "SysUserGroupManager")
    private SysUserGroupManagerI sysUserGroupManageBean;

    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        // 通过用户登录名称查找用户信息
        SysUser loginUser = sysUserGroupManageBean.getUserByLoginName(loginName);
        if (loginUser == null) {
            throw new UsernameNotFoundException(new MessageFormat("用户 {0} 不存在").format(new Object[]{loginName}));
        }

        // 校验用户信息
        if (SysConstants.SYS_USTATUS_BAN == loginUser.getUstatus().byteValue()) {
            throw new UsernameNotFoundException(new MessageFormat("用户 {0} 已被锁定").format(new Object[]{loginName}));
        }

        // 组装用户信息
        return initUserDetails(loginUser);
    }

    /**
     * 取得用户的权限
     *
     * @param loginUser
     * @return
     */
    private UserPrincipal initUserDetails(SysUser loginUser) {

        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();// 用户角色信息
        StringBuffer tempIds = new StringBuffer();
        List<Integer> roleIds = new ArrayList<Integer>();
        List<String> roleNames = new ArrayList<String>();
        List<String> resTypeIds = new ArrayList<String>(); // 具备权限的资源类型
        String split = ",";
        boolean isSuper = loginUser.getIsSuper().byteValue() == SysConstants.SYS_YES ? true : false;

        // 取出用户的角色信息，默认包含普通用户角色，若用户为管理员则具备管理员角色
        List<SysRole> roles = sysUserGroupManageBean.findUserAllRole(loginUser);
        for (SysRole role : roles) {
            authSet.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleNameEn()));
            roleIds.add(role.getId());
            roleNames.add(role.getRoleName());
            if (!StringUtils.isBlank(role.getResTypeIds()))
                tempIds.append(role.getResTypeIds()).append(split);
        }

        // 取得当前用户具备访问权限的资源类型集合，该数据用于管理首页加载时使用
        String[] typeids = tempIds.toString().split(split);
        for (int i = 0; i < typeids.length; i++) {
            if (!StringUtils.isBlank(typeids[i]) && !resTypeIds.contains(typeids[i])) { // 去重
                resTypeIds.add(typeids[i]);
            }
        }

        boolean enables = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        // 拼装用户信息
        return new UserPrincipal(loginUser.getLoginName(),
                loginUser.getUserName(),
                loginUser.getId(),
                "",
                loginUser.getPwd(),
                enables,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authSet,
                resTypeIds,
                roleIds,
                isSuper,
                roleNames);
    }
}
