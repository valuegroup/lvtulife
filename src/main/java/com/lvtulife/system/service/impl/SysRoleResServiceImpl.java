package com.lvtulife.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.system.model.SysResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lvtulife.base.service.impl.BaseServiceImpl;
import com.lvtulife.base.utils.HqlFilter;
import com.lvtulife.system.model.SysRoleRes;
import com.lvtulife.system.service.SysRoleResServiceI;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色资源业务处理
 *
 * @author lvtulife
 */
@Service(value = "SysRoleResService")
public class SysRoleResServiceImpl extends BaseServiceImpl<SysRoleRes> implements SysRoleResServiceI {
    private static Logger logger = LoggerFactory.getLogger(SysRoleResServiceImpl.class);

    public List<SysRoleRes> findRoleRes(int rid) {
        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#rid_I_EQ", rid + "");
        return findByFilter(hqlFilter);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removeRoleAllRes(int id) {
        String hql = "delete from SysRoleRes where rid = " + id;
        executeHql(hql);
    }

    public List<SysResource> findRoleResourceFixedToRes(int rid) {
        // ps: 当前查询的资源是指在sys_resource表中存在的数据，是用户自定义的资源，他的特点是主键是小于SysConstants.AUTH_POINT_FIND_VALUE，t.res_id<SysConstants.AUTH_POINT_FIND_VALUE
        String sql = "select r.* from sys_role_res t LEFT OUTER JOIN sys_resource r ON t.res_id = r.id where t.res_id<" + SysConstants.AUTH_POINT_FIND_VALUE + " and t.rid=:rid and t.del=:tdel and r.del=:rdel";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("rid", rid);
        params.put("tdel", SysConstants.SYS_STATUS);
        params.put("rdel", SysConstants.SYS_STATUS);
        return findBySql(sql, params, SysResource.class);
    }

    public List<SysRoleRes> findRoleResourceDynamic(int rid) {
        // ps: 当前查询的资源是指在sys_resource表中不存在的数据，是系统内置好的资源，该资源组成方式是由父节点的主键+（SysConstants.AUTH_POINT_FIND_VALUE：查询,SysConstants.AUTH_POINT_SAVE_VALUE：增加,30000：SysConstants.AUTH_POINT_EDIT_VALUE,SysConstants.AUTH_POINT_REMOVE_VALUE：删除）组成，因此会大于SysConstants.AUTH_POINT_FIND_VALUE
        HqlFilter hqlFilter = new HqlFilter();
        hqlFilter.addFilter("QUERY_t#rid_I_EQ", Integer.toString(rid));
        hqlFilter.addFilter("QUERY_t#resId_I_GT", SysConstants.AUTH_POINT_FIND_VALUE);
        hqlFilter.addFilter("QUERY_t#del_BT_EQ", SysConstants.SYS_STATUS);
        return findByFilter(hqlFilter);
    }
}
