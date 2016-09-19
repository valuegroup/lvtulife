package com.lvtulife.system.manager;

import com.lvtulife.base.component.easyui.Tree;
import com.lvtulife.system.component.security.UserPrincipal;
import com.lvtulife.system.component.vo.MainMenuVo;
import com.lvtulife.system.model.*;
import com.lvtulife.system.service.SysResourceServiceI;
import com.lvtulife.system.service.SysResourceTypeServiceI;
import com.lvtulife.system.service.SysRoleResServiceI;
import com.lvtulife.system.service.SysRoleServiceI;
import com.lvtulife.system.service.SysUserRoleServiceI;
import com.lvtulife.system.service.SysUserServiceI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysUserGroupManagerI {

    /**
     * 通过用户名查找用户信息
     * @param loginName
     * @return
     */
    public SysUser getUserByLoginName(String loginName);

    /**
     * 用户角色授予
     * @param uid
     * @param roleIds
     */
    public void grantUserRole(int uid, List<Integer> roleIds);

    /**
     * 移除用户的所有角色
     *
     * @param uid
     */
    public void clearUserRole(int uid);

    /**
     * 角色授权
     *
     * @param rid
     * @param resIds
     * @param typeIds
     */
    public void grantRoleRes(int rid, List<Integer> resIds, String typeIds);

    /**
     * 获取指定用户的角色信息
     *
     * @param uid
     * @return
     */
    public List<SysUserRole> findUserRoles(int uid);

    /**
     * 获取角色信息
     *
     * @param id
     * @return
     */
    public SysRole getRole(int id);

    /**
     * 获取可用的角色列表
     *
     * @return
     */
    public List<SysRole> findAvailableRoles();

    /**
     * 查找角色下的资源信息
     *
     * @param rid
     * @return
     */
    public List<SysResource> findRoleResourceFixedToRes(int rid);

    /**
     * 获取所有资源信息
     *
     * @return
     */
    public List<SysResource> findAllResource();

    /**
     * 查找角色资源信息
     *
     * @param rid 角色主键
     * @return
     */
    public List<SysRoleRes> findRoleRes(int rid);

    /**
     * 获取所有资源map
     *
     * @return
     */
    public HashMap<Integer, SysResource> findResourcesToMap();

    /**
     * 通过资源类型获取类型下所有资源map
     *
     * @param typeId
     * @return
     */
    public HashMap<Integer, SysResource> findResourcesToMap(Integer typeId);

    /**
     * 通过资源类型获取所有属性为菜单的资源map
     * @param typeId
     * @return
     */
    public HashMap<Integer, SysResource> findMenuToMap(Integer typeId);

    /**
     * 获取所有属性为菜单的资源map
     * @return
     */
    public HashMap<Integer, SysResource> findMenuToMap();

    /**
     * 通过资源类型获取所有属性为菜单的资源
     * @param typeId
     * @return
     */
    public List<SysResource> findMenu(Integer typeId);

    /**
     * 获取所有属性为菜单的资源
     *
     * @return
     */
    public List<SysResource> findMenu();

    /**
     * 查询用户所有的角色信息，含默认的用户角色和超级管理员角色
     *
     * @param t
     * @return
     */
    public List<SysRole> findUserAllRole(SysUser t);

    /**
     * 查询用户已关联的角色信息，不含默认的用户角色和超级管理员角色
     * @param uid
     * @return
     */
    public List<SysRole> findUserRelatedRole(int uid);


    /**
     * 统计用户注册时间图表
     *
     * @return
     */
    public List<Long> statiUserCreateDtChart();

    /**
     * 用户角色分布报表
     *
     * @return
     */
    public List<Map<String, Object>> statiUserRoleChart();

    /**
     * 获取所有资源类型
     */
    public List<SysResourceType> findResourcetype();

    /**
     * 获取用户菜单,分类获取
     *
     * @param userDetails
     * @return
     */
    public List<MainMenuVo> getMenu(UserPrincipal userDetails);

    /**
     * 获取用户菜单，不分类
     *
     * @param userDetails
     * @return
     */
    public List<Tree> getMenuNoType(UserPrincipal userDetails);


    /**
     * 查询用户信息
     *
     * @param id
     * @return
     */
    public SysUser getUser(int id);

    /**
     * 获取可用的角色树列表(不含系统内置的角色)
     *
     * @return
     */
    public List<Tree> findCustomAvailableRoles();

    /**
     * 查找角色下的动态资源信息
     * 当前查询的资源是指在sys_resource表中不存在的数据，是系统内置好的资源，该资源组成方式是由父节点的主键+（10000：查询,20000：增加,30000：修改,40000：删除）组成，因此会大于10000
     *
     * @param rid
     * @return
     */
    public List<SysRoleRes> findRoleResourceDynamic(int rid);
}
