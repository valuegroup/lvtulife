package com.lvtulife.system.manager.impl;

import javax.annotation.Resource;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.easyui.Tree;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.system.component.security.SecurityMetadataSourceManagerImpl;
import com.lvtulife.system.component.security.UserPrincipal;
import com.lvtulife.system.component.vo.MainMenuVo;
import com.lvtulife.system.model.*;
import org.apache.batik.dom.util.HashTable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lvtulife.system.manager.SysUserGroupManagerI;
import com.lvtulife.system.service.SysResourceServiceI;
import com.lvtulife.system.service.SysResourceTypeServiceI;
import com.lvtulife.system.service.SysRoleResServiceI;
import com.lvtulife.system.service.SysRoleServiceI;
import com.lvtulife.system.service.SysUserRoleServiceI;
import com.lvtulife.system.service.SysUserServiceI;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(value = "SysUserGroupManager")
public class SysUserGroupManagerImpl implements SysUserGroupManagerI {
    private static Logger logger = LoggerFactory.getLogger(SysUserGroupManagerImpl.class);

    @Resource(name = "SysUserService")
    private SysUserServiceI sysUserBean;

    @Resource(name = "SysUserRoleService")
    private SysUserRoleServiceI sysUserRoleBean;

    @Resource(name = "SysRoleService")
    private SysRoleServiceI sysRoleBean;

    @Resource(name = "SysRoleResService")
    private SysRoleResServiceI sysRoleResBean;

    @Resource(name = "SysResourceService")
    private SysResourceServiceI sysResourceBean;

    @Resource(name = "SysResourceTypeService")
    private SysResourceTypeServiceI sysResourceTypeBean;

    @Resource(name = "SecurityMetadataSourceManager")
    private SecurityMetadataSourceManagerImpl securityMetadataSourceManager;

    public SysUser getUserByLoginName(String loginName) {
        return sysUserBean.getUserByLoginName(loginName);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void grantUserRole(int uid, List<Integer> roleIds) {
        SysUser user = sysUserBean.getById(uid);
        if (user == null) {
            throw new CustomException(Code.C401, "不存在的用户信息！");
        }

        if (SysConstants.SYS_YES == user.getIsSuper().byteValue()) {
            throw new CustomException(Code.C401, "超级管理员无需授予其他角色！");
        }

        List<SysUserRole> list = new ArrayList<SysUserRole>();
        for (Integer roleId : roleIds) {
            /*SysRole role = sysRoleBean.getById(roleId);
            if (role == null) {
                throw new CustomException(Code.C401, "不存在的角色信息！");
            }*/
            SysUserRole t = new SysUserRole();
            t.setUid(user.getId());
            t.setRid(roleId);
            t.setDel(SysConstants.SYS_STATUS);
            list.add(t);
        }

        // 先移除
        sysUserRoleBean.removeUserAllRole(uid);
        for (SysUserRole t : list) {
            // 再添加
            sysUserRoleBean.save(t);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void clearUserRole(int uid) {
        SysUser user = sysUserBean.getById(uid);
        if (user == null) {
            throw new CustomException(Code.C401, "不存在的用户信息！");
        }

        if (SysConstants.SYS_YES == user.getIsSuper().byteValue()) {
            throw new CustomException(Code.C401, "超级管理员无需授予其他角色！");
        }

        // 先移除
        sysUserRoleBean.removeUserAllRole(uid);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void grantRoleRes(int rid, List<Integer> resIds, String typeIds) {
        SysRole role = sysRoleBean.getById(rid);
        if (role == null) {
            throw new CustomException(Code.C401, "不存在的角色信息！");
        }

        if ("ROOT".equals(role.getRoleNameEn())) {
            throw new CustomException(Code.C401, "超级管理员角色,无需授权！");
        }

        List<SysRoleRes> list = new ArrayList<SysRoleRes>();
        for (Integer resId : resIds) {
            /*SysResource res = sysResourceBean.getById(resId);
            if (res == null) {
                throw new CustomException(Code.C401, "不存在的资源信息！");
            }*/
            SysRoleRes t = new SysRoleRes();
            t.setRid(role.getId());
            t.setResId(resId);
            t.setDel(SysConstants.SYS_STATUS);
            list.add(t);
        }

        // 先移除
        sysRoleResBean.removeRoleAllRes(role.getId());
        for (SysRoleRes t : list) {
            // 再添加
            sysRoleResBean.save(t);
        }

        // 更新资源类型集合
        role.setResTypeIds(typeIds);
        sysRoleBean.update(role);

        // 重新加载Security权限
        securityMetadataSourceManager.reloadResource();
    }

    public List<SysUserRole> findUserRoles(int uid) {
        return sysUserRoleBean.findUserRoles(uid);
    }

    public SysRole getRole(int id) {
        return sysRoleBean.getById(id);
    }

    public List<SysRole> findAvailableRoles() {
        return sysRoleBean.findAvailableRoles();
    }

    public List<SysResource> findRoleResourceFixedToRes(int rid) {
        return sysRoleResBean.findRoleResourceFixedToRes(rid);
    }

    public List<SysResource> findAllResource() {
        return sysResourceBean.find();
    }

    public List<SysRoleRes> findRoleRes(int rid) {
        return sysRoleResBean.findRoleRes(rid);
    }

    public HashMap<Integer, SysResource> findResourcesToMap() {
        return sysResourceBean.findResourcesToMap();
    }

    public HashMap<Integer, SysResource> findResourcesToMap(Integer typeId) {
        return sysResourceBean.findResourcesToMap(typeId);
    }

    public List<SysRole> findUserAllRole(SysUser t) {
        // 所有的用户默认拥有ROLE_USER权限,用户和超级管理员角色默认不在数据库中关联
        List<SysRole> result = new ArrayList<SysRole>();

        List<Object> userRoles = sysUserRoleBean.findUserRoleIds(t.getId());

        // 查询所有可用的角色信息
        List<SysRole> roles = findAvailableRoles();
        for (SysRole role : roles) {
            // 如果参数isSuper为1.则拥有ROLE_ROOT权限
            if (SysConstants.ROLE_ROOT.equals("ROLE_" + role.getRoleNameEn()) && SysConstants.SYS_YES == t.getIsSuper().byteValue()) {
                result.add(role);
            }

            // 所有用户默认含用户角色，该角色不在数据库中关联
            if (SysConstants.ROLE_USER.equals("ROLE_" + role.getRoleNameEn())) {
                result.add(role);
            }

            // 匹配已授予的角色信息
            if (userRoles.contains(role.getId())) {
                result.add(role);
            }
        }

        return result;
    }

    public List<SysRole> findUserRelatedRole(int uid) {
        return sysUserRoleBean.findUserRelatedRole(uid);
    }

    public List<Long> statiUserCreateDtChart() {
        return sysUserBean.statiUserCreateDtChart();
    }

    public List<Map<String, Object>> statiUserRoleChart() {
        List<SysRole> roles = sysRoleBean.findAvailableRoles();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SysRole role : roles) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("name", role.getRoleName());
            m.put("y", sysUserRoleBean.countUserByRoleId(role.getId()));
            m.put("sliced", false);
            m.put("selected", false);
            list.add(m);
        }
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("name", "无");
        m.put("y", sysUserRoleBean.countUserByNotRoleId());
        m.put("sliced", true);
        m.put("selected", true);
        list.add(m);
        return list;
    }

    public List<SysResourceType> findResourcetype() {
        return sysResourceTypeBean.findResourcetype();
    }

    public HashMap<Integer, SysResource> findMenuToMap(Integer typeId) {
        return sysResourceBean.findMenuToMap(typeId);
    }

    public HashMap<Integer, SysResource> findMenuToMap() {
        return sysResourceBean.findMenuToMap();
    }

    public List<MainMenuVo> getMenu(UserPrincipal userDetails) {
        List<MainMenuVo> result = new ArrayList<MainMenuVo>();

        // 查出所有资源类型
        List<SysResourceType> resourceTypes = findResourcetype(); // 查询所有

        if (userDetails.getSuper()) {
            // 系统超级管理员

            for (SysResourceType sysResourceType : resourceTypes) {
                List<Tree> tree = new ArrayList<Tree>();

                // 通过资源主键查询资源
                List<SysResource> resources = findMenu(sysResourceType.getId());// typeId
                sysResourceBean.listConvertTree(tree, resources);

                MainMenuVo menus = new MainMenuVo(sysResourceType, tree);
                result.add(menus);
            }
        } else {
            // 取出角色资源权限关联信息
            List<SysRoleRes> roleRes = new ArrayList<SysRoleRes>();
            for (Integer rid : userDetails.getRoleIds()) {
                roleRes.addAll(findRoleRes(rid));
            }

            // 取出资源主键并去重
            HashSet<Integer> resourceIds = new HashSet<Integer>();
            for (SysRoleRes sysRoleRes : roleRes) {
                if (!resourceIds.contains(sysRoleRes.getResId())) {
                    resourceIds.add(sysRoleRes.getResId());
                }
            }

            for (SysResourceType sysResourceType : resourceTypes) {
                if (userDetails.getResTypeIds().contains(sysResourceType.getId().toString())) {

                    List<Tree> tree = new ArrayList<Tree>();

                    // 通过资源主键查询资源
                    List<SysResource> resources = new ArrayList<SysResource>();
                    HashMap<Integer, SysResource> resourcesMap = findMenuToMap(sysResourceType.getId());// typeId
                    for (Integer rid : resourceIds) {
                        if (resourcesMap.get(rid) != null) {
                            resources.add(resourcesMap.get(rid));
                        }
                    }
                    sysResourceBean.listConvertTree(tree, resources);

                    MainMenuVo menus = new MainMenuVo(sysResourceType, tree);
                    result.add(menus);
                }
            }
        }

        return result;
    }

    public List<Tree> getMenuNoType(UserPrincipal userDetails) {
        List<Tree> result = new ArrayList<Tree>();

        // 查出所有资源类型
        List<SysResourceType> resourceTypes = findResourcetype(); // 查询所有

        if (userDetails.getSuper()) {
            // 系统超级管理员

            // 查询资源
            List<SysResource> resources = findMenu();
            sysResourceBean.listConvertTree(result, resources);
        } else {
            // 取出角色资源权限关联信息
            List<SysRoleRes> roleRes = new ArrayList<SysRoleRes>();
            for (Integer rid : userDetails.getRoleIds()) {
                roleRes.addAll(findRoleRes(rid));
            }

            // 取出资源主键并去重
            HashSet<Integer> resourceIds = new HashSet<Integer>();
            for (SysRoleRes sysRoleRes : roleRes) {
                if (!resourceIds.contains(sysRoleRes.getResId())) {
                    resourceIds.add(sysRoleRes.getResId());
                }
            }

            // 通过资源主键查询资源
            List<SysResource> resources = new ArrayList<SysResource>();
            HashMap<Integer, SysResource> resourcesMap = findMenuToMap();
            for (Integer rid : resourceIds) {
                if (resourcesMap.get(rid) != null) {
                    resources.add(resourcesMap.get(rid));
                }
            }
            sysResourceBean.listConvertTree(result, resources);
        }

        return result;
    }

    public SysUser getUser(int id) {
        return sysUserBean.getById(id);
    }

    public List<SysResource> findMenu(Integer typeId) {
        return sysResourceBean.findMenu(typeId);
    }

    public List<SysResource> findMenu() {
        return sysResourceBean.findMenu();
    }

    public List<Tree> findCustomAvailableRoles() {
        return sysRoleBean.findCustomAvailableRoles();
    }

    public List<SysRoleRes> findRoleResourceDynamic(int rid) {
        return sysRoleResBean.findRoleResourceDynamic(rid);
    }

}
