package com.lvtulife.system.component.vo;

/**
 * sessionInfo模型，只要登录成功，就需要设置到session里面，便于系统使用
 *
 * @author valuegroup
 */
public class SessionInfo implements java.io.Serializable {
    private SysUserVo user;

    public SysUserVo getUser() {
        return user;
    }

    public void setUser(SysUserVo user) {
        this.user = user;
    }

    public String toString() {
        return user.getLoginName();
    }
}
