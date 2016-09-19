package com.lvtulife.base.service.impl;

import com.lvtulife.base.component.LabelValue;
import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.component.log.MethodEnum;
import com.lvtulife.base.component.log.annotation.LogService;
import com.lvtulife.base.component.message.Code;
import com.lvtulife.base.component.message.CustomException;
import com.lvtulife.base.dao.BaseDaoI;
import com.lvtulife.base.service.BaseServiceI;
import com.lvtulife.base.utils.HqlFilter;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 基础业务逻辑
 * 注意: 此层不建议加入切面日志，如需要加入切面日志建议在业务层加入
 * @param <T>
 * @author valuegroup
 */
@Service(value = "BaseService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class BaseServiceImpl<T> implements BaseServiceI<T> {
    private static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Resource(name = "BaseDao")
    private BaseDaoI<T> baseDao;

    private Class<T> getTClass() {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        return (Class<T>) trueType;
        // return GenericsUtils.getSuperClassGenricType(xx, 0);
    }

    private String getTClassName() {
        return getTClass().getName();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Serializable save(T o) {
        if (o == null) {
            throw new CustomException(Code.C402);
        }
        writeDefaultField(o, false);
        return baseDao.save(o);
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(T o) {
        baseDao.delete(o);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        T t = getById(id);
        if (t == null) {
            throw new CustomException(Code.C400, "需删除的对象[" + id + "]不存在!");
        }

        baseDao.delete(t);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deletes(List<Integer> ids) {
        for (Integer id : ids) {
            delete(id);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void update(T o) {
        if (o == null) {
            throw new CustomException(Code.C402);
        }

        writeDefaultField(o, true);
        baseDao.update(o);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveOrUpdate(T o) {
        baseDao.saveOrUpdate(o);
    }

    public T getById(Serializable id) {
        return baseDao.getById(getTClass(), id);
    }

    public T getByHql(String hql) {
        return baseDao.getByHql(hql);
    }

    public T getByHql(String hql, Map<String, Object> params) {
        return baseDao.getByHql(hql, params);
    }

    public T getByFilter(HqlFilter hqlFilter) {
        String hql = "select distinct t from " + getTClassName() + " t";
        return getByHql(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
    }

    public List<T> find() {
        return findByFilter(new HqlFilter());
    }

    public List<T> find(String hql) {
        return baseDao.find(hql);
    }

    public List<LabelValue> findToLabelValue(String className, String columnName) {
        StringBuffer hql = new StringBuffer();
        hql.append("SELECT id AS value,").append(columnName).append(" AS label FROM ").append(className).append(" WHERE del = " + SysConstants.SYS_STATUS);
        return baseDao.findToLabelValue(hql.toString());
    }

    public List<T> find(String hql, Map<String, Object> params) {
        return baseDao.find(hql, params);
    }

    public List<T> find(String hql, int page, int rows) {
        return baseDao.find(hql, page, rows);
    }

    public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
        return baseDao.find(hql, params, page, rows);
    }

    public List<T> find(int page, int rows) {
        return findByFilter(new HqlFilter(), page, rows);
    }

    public List<T> findByFilter(HqlFilter hqlFilter) {
        String hql = "select distinct t from " + getTClassName() + " t";
        return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
    }

    public List<T> findByFilter(HqlFilter hqlFilter, int page, int rows) {
        String hql = "select distinct t from " + getTClassName() + " t";
        return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams(), page, rows);
    }

    public Long count(String hql) {
        return baseDao.count(hql);
    }

    public Long count(String hql, Map<String, Object> params) {
        return baseDao.count(hql, params);
    }

    public int minOrMax(String hql, Map<String, Object> params) {
        return baseDao.minOrMax(hql, params);
    }

    public Long countByFilter(HqlFilter hqlFilter) {
        String hql = "select count(distinct t) from " + getTClassName() + " t";
        return count(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
    }

    public Long count() {
        return countByFilter(new HqlFilter());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int executeHql(String hql) {
        return baseDao.executeHql(hql);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int executeHql(String hql, Map<String, Object> params) {
        return baseDao.executeHql(hql, params);
    }

    public List<Object> findClumnBySql(String sql) {
        return baseDao.findClumnBySql(sql);
    }

    public List findBySql(String sql) {
        return baseDao.findBySql(sql);
    }

    public List findBySql(String sql, int page, int rows) {
        return baseDao.findBySql(sql, page, rows);
    }

    public List findBySql(String sql, Map<String, Object> params) {
        return baseDao.findBySql(sql, params);
    }

    public List findBySql(String sql, Map<String, Object> params, int page, int rows) {
        return baseDao.findBySql(sql, params, page, rows);
    }

    public List findBySql(String sql, Class target) {
        return baseDao.findBySql(sql, target);
    }

    public List findBySql(String sql, int page, int rows, Class target) {
        return baseDao.findBySql(sql, page, rows, target);
    }

    public List findBySql(String sql, Map<String, Object> params, Class target) {
        return baseDao.findBySql(sql, params, target);
    }

    public List findBySql(String sql, Map<String, Object> params, int page, int rows, Class target) {
        return baseDao.findBySql(sql, params, page, rows, target);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int executeSql(String sql) {
        return baseDao.executeSql(sql);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int executeSql(String sql, Map<String, Object> params) {
        return baseDao.executeSql(sql, params);
    }

    public BigInteger countBySql(String sql) {
        return baseDao.countBySql(sql);
    }

    public BigInteger countBySql(String sql, Map<String, Object> params) {
        return baseDao.countBySql(sql, params);
    }


    /**
     * 设置常用字段的默认值
     *
     * @param o
     */
    private void writeDefaultField(T o, boolean isUpdate) {
        try {
            Short status = (Short) FieldUtils.readField(o, "del", true);
            if (status == null) {
                FieldUtils.writeDeclaredField(o, "del", SysConstants.SYS_STATUS, true);
            }
        } catch (IllegalAccessException e) {
            //logger.info("读取权限不够！");
        } catch (IllegalArgumentException e) {
            //logger.info("不存在该字段！");
        } catch (Exception e) {
        }

        if (!isUpdate) {
            try {
                Date createdDt = (Date) FieldUtils.readField(o, "createdDt", true);
                if (createdDt == null) {
                    FieldUtils.writeDeclaredField(o, "createdDt", new Date(), true);
                }
            } catch (IllegalAccessException e) {
                //logger.info("读取权限不够！");
            } catch (IllegalArgumentException e) {
                //logger.info("不存在该字段！");
            } catch (Exception e) {
            }
        }

        try {
            Date updatedDt = (Date) FieldUtils.readField(o, "updatedDt", true);
            if (updatedDt == null) {
                FieldUtils.writeDeclaredField(o, "updatedDt", new Date(), true);
            }
        } catch (IllegalAccessException e) {
            //logger.info("读取权限不够！");
        } catch (IllegalArgumentException e) {
            //logger.info("不存在该字段！");
        } catch (Exception e) {
        }
    }
}
