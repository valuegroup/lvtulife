package com.lvtulife.system.service;

import java.util.List;
import java.util.Map;

import com.lvtulife.base.service.BaseServiceI;
import com.lvtulife.system.model.SysRole;
import com.lvtulife.system.model.SysUser;
import com.lvtulife.system.model.SysUserRole;

public interface SysUserRoleServiceI extends BaseServiceI<SysUserRole> {
    /**
     * 获取指定用户的角色信息
     *
     * @param uid
     * @return
     */
    public List<SysUserRole> findUserRoles(int uid);

    /**
     * 获取指定用户的角色ID集合
     *
     * @param uid
     * @return
     */
    public List<Object> findUserRoleIds(int uid);

    /**
     * 移除用户的所有角色信息
     * @param id
     */
    public void removeUserAllRole(int id);


    /**
     * 查询用户已关联的角色信息，不含默认的用户角色和超级管理员角色
     * @param uid
     * @return
     */
    public List<SysRole> findUserRelatedRole(int uid);

    /**
     * 统计?角色的用户
     *
     * @param roleId
     * @return
     */
    public Long countUserByRoleId(int roleId);

    /**
     * 统计没有角色的用户数量
     *
     * @return
     */
    public Long countUserByNotRoleId();

}
