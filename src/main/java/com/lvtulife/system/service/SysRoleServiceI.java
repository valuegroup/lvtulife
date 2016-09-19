package com.lvtulife.system.service;

import java.util.List;
import java.util.Map;

import com.lvtulife.base.component.easyui.Tree;
import com.lvtulife.base.service.BaseServiceI;
import com.lvtulife.base.utils.HqlFilter;
import com.lvtulife.system.model.SysRole;

/**
 * 系统角色业务
 *
 * @author valuegroup
 */
public interface SysRoleServiceI extends BaseServiceI<SysRole> {

    /**
     * 查找角色
     *
     * @param hqlFilter
     * @param page
     * @param rows
     * @return
     */
    public List<SysRole> findRoleByFilter(HqlFilter hqlFilter, int page, int rows);

    /**
     * 查找角色
     */
    public List<SysRole> findRoleByFilter(HqlFilter hqlFilter);


    /**
     * 统计角色
     *
     * @param hqlFilter
     * @return
     */
    public Long countRoleByFilter(HqlFilter hqlFilter);

    /**
     * 添加一个角色
     * @param role
     */
    public void saveData(SysRole role);

    /**
     * 更新角色数据
     * @param role
     */
    public void updateData(SysRole role);

    /**
     * 删除角色信息
     * @param id
     */
    public void deleteData(int id);

    /**
     * 批量删除角色信息
     *
     * @param ids
     */
    public void deleteDatas(List<Integer> ids);

    /**
     * 获取可用的角色列表(含系统内置的角色)
     *
     * @return
     */
    public List<SysRole> findAvailableRoles();

    /**
     * 获取可用的角色树列表(不含系统内置的角色)
     *
     * @return
     */
    public List<Tree> findCustomAvailableRoles();

}
