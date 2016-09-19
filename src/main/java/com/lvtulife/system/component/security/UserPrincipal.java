package com.lvtulife.system.component.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class UserPrincipal extends User {
    private static final long serialVersionUID = 1L;

    private String loginName;
    private String userName;
    private Integer userId;
    private String language;
    private String loginTime;
    private Boolean isSuper;
    private List<String> resTypeIds;
    private List<Integer> roleIds;
    private List<String> roleNames;

    public UserPrincipal(String loginName, String userName, Integer userId, String language, String password, boolean enabled,
                         boolean accountNonExpired, boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities, List<String> resTypeIds, List<Integer> roleIds, boolean isSuper, List<String> roleNames) {

        super(loginName, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);

        this.loginName = loginName;
        this.userName = userName;
        this.userId = userId;
        this.language = language;
        this.loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.resTypeIds = resTypeIds;
        this.roleIds = roleIds;
        this.isSuper = isSuper;
        this.roleNames = roleNames;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getResTypeIds() {
        return resTypeIds;
    }

    public void setResTypeIds(List<String> resTypeIds) {
        this.resTypeIds = resTypeIds;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public Boolean getSuper() {
        return isSuper;
    }

    public void setSuper(Boolean aSuper) {
        isSuper = aSuper;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }
}
