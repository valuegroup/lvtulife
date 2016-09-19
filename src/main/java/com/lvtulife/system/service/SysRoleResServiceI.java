package com.lvtulife.system.service;

import java.util.List;

import com.lvtulife.base.service.BaseServiceI;
import com.lvtulife.system.model.SysResource;
import com.lvtulife.system.model.SysRoleRes;

public interface SysRoleResServiceI extends BaseServiceI<SysRoleRes> {

    /**
     * 查找所有角色资源信息
     *
     * @param rid 角色主键
     * @return
     */
    public List<SysRoleRes> findRoleRes(int rid);

    /**
     * 移除指定角色下的所有资源
     * @param id
     */
    public void removeRoleAllRes(int id);

    /**
     * 查找角色下的固定资源信息
     * 当前查询的资源是指在sys_resource表中存在的数据，是用户自定义的资源，他的特点是主键是小于10000，t.res_id<10000
     * @param rid
     * @return
     */
    public List<SysResource> findRoleResourceFixedToRes(int rid);

    /**
     * 查找角色下的动态资源信息
     * 当前查询的资源是指在sys_resource表中不存在的数据，是系统内置好的资源，该资源组成方式是由父节点的主键+（10000：查询,20000：增加,30000：修改,40000：删除）组成，因此会大于10000
     *
     * @param rid
     * @return
     */
    public List<SysRoleRes> findRoleResourceDynamic(int rid);
}
