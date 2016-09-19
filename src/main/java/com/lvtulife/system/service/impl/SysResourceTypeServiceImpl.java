package com.lvtulife.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lvtulife.base.service.impl.BaseServiceImpl;
import com.lvtulife.base.utils.HqlFilter;
import com.lvtulife.system.model.SysResourceType;
import com.lvtulife.system.service.SysResourceTypeServiceI;

/**
 * 系统资源类型业务逻辑
 *
 * @author valuegroup
 */
@Service(value = "SysResourceTypeService")
public class SysResourceTypeServiceImpl extends BaseServiceImpl<SysResourceType> implements SysResourceTypeServiceI {
    private static Logger logger = LoggerFactory.getLogger(SysResourceTypeServiceImpl.class);

    /**
     * 为列表添加了缓存，查询一次过后，只要不重启服务，缓存一直存在，不需要再查询数据库了，节省了一些资源
     * <p/>
     * 在ehcache.xml里面需要有对应的value
     * <p/>
     * <cache name="SysResourceTypeServiceCache"
     * <p/>
     * key是自己设定的一个ID，用来标识缓存
     */

    @Cacheable(value = "SysResourceTypeServiceCache", key = "'SysResourceTypeList'")
    public List<SysResourceType> findResourcetype() {
        return find();
    }


    public Long countResourceTypeByFilter(HqlFilter hqlFilter) {
        String hql = "select count(t) from SysResourceType t";
        return count(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
    }


    public List<SysResourceType> findResourceTypeByFilter(HqlFilter hqlFilter, int page, int rows) {
        String hql = "select t from SysResourceType t ";
        return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams(), page, rows);
    }


    public int getResTypeSeq() {
        String Hql = "select MAX(t.sort) from SysResourceType t";
        return minOrMax(Hql, null) + 1;
    }
}
