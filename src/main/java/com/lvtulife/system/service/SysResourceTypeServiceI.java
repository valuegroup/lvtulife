package com.lvtulife.system.service;

import java.util.List;

import com.lvtulife.base.service.BaseServiceI;
import com.lvtulife.base.utils.HqlFilter;
import com.lvtulife.system.model.SysResourceType;

/**
 * 资源类型业务
 *
 * @author valuegroup
 */
public interface SysResourceTypeServiceI extends BaseServiceI<SysResourceType> {
    /**
     * 获取所有资源类型
     */
    public List<SysResourceType> findResourcetype();

    /**
     * 统计资源类型
     *
     * @param hqlFilter
     * @return
     */
    public Long countResourceTypeByFilter(HqlFilter hqlFilter);

    /**
     * 查找资源类型
     *
     * @param hqlFilter
     * @param page
     * @param rows
     * @return
     */
    public List<SysResourceType> findResourceTypeByFilter(HqlFilter hqlFilter, int page, int rows);

    /**
     * 获取资源类型排序
     *
     * @return
     */
    public int getResTypeSeq();

}
