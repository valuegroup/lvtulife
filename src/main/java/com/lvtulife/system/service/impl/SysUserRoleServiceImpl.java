package com.lvtulife.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.system.model.SysRole;
import com.lvtulife.system.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lvtulife.base.dao.BaseDaoI;
import com.lvtulife.base.service.impl.BaseServiceImpl;
import com.lvtulife.system.model.SysUserRole;
import com.lvtulife.system.service.SysUserRoleServiceI;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户角色业务处理
 *
 * @author lvtulife
 */
@Service(value = "SysUserRoleService")
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole> implements SysUserRoleServiceI {
    private static Logger logger = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    @Resource(name = "BaseDao")
    private BaseDaoI<SysUserRole> sysUserRoleDao;

    public List<SysUserRole> findUserRoles(int uid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        params.put("del", SysConstants.SYS_STATUS);
        return sysUserRoleDao.find("from SysUserRole where uid=:uid and del=:del", params);
    }

    public List<Object> findUserRoleIds(int uid) {
        return sysUserRoleDao.findClumnBySql("select rid from sys_user_role  where uid=" + uid + " and del=" + SysConstants.SYS_STATUS);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removeUserAllRole(int id) {
        String hql = "delete from SysUserRole where uid = " + id;
        sysUserRoleDao.executeHql(hql);
    }

    public List<SysRole> findUserRelatedRole(int uid) {
        // 系统超级管理员角色和用户角色默认不会记录在数据库中，ps:所有的用户都具备用具角色，用户为管理员用户时具备超级管理员角色
        String sql = "select r.* from sys_user_role t LEFT OUTER JOIN sys_role r ON t.rid = r.id where t.uid=:uid and r.source=:source and r.rstatus=:rstatus and t.del=:tdel and r.del=:rdel";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uid", uid);
        params.put("source", SysConstants.SYS_INUSER); // 非内置
        params.put("rstatus", SysConstants.SYS_RSTATUS);// 可用状态
        params.put("tdel", SysConstants.SYS_STATUS);
        params.put("rdel", SysConstants.SYS_STATUS);
        return findBySql(sql, params, SysRole.class);
    }

    public Long countUserByRoleId(int roleId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleId", roleId);
        params.put("del", SysConstants.SYS_STATUS);
        String hql = "select count(0) from SysUserRole t where t.rid = :roleId and t.del=:del";
        return count(hql, params);
    }

    public Long countUserByNotRoleId() {
        String hql = "select count(0) from SysUserRole t where t.rid is null and t.del=" + SysConstants.SYS_STATUS;
        return count(hql);
    }

}
