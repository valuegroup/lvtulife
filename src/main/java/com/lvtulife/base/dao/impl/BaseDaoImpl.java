package com.lvtulife.base.dao.impl;

import com.lvtulife.base.dao.BaseDaoI;
import com.lvtulife.base.component.LabelValue;
import com.lvtulife.base.mapper.impl.BaseMapperImpl;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("BaseDao")
public class BaseDaoImpl<T> implements BaseDaoI<T> {

    @Autowired
    private SessionFactory sessionFactory;

    @Resource(name = "mybatisSessionTemplate")
    public SqlSessionTemplate mybatisSessionTemplate;

    /**
     * 获得当前事物的session
     *
     * @return org.hibernate.Session
     */
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


    public SqlSessionTemplate getMybatisSessionTemplate() {
        return mybatisSessionTemplate;
    }

    public Serializable save(T o) {
        if (o != null) {
            return getCurrentSession().save(o);
        }
        return null;
    }

    public void delete(T o) {
        if (o != null) {
            getCurrentSession().delete(o);
        }
    }

    public void update(T o) {
        if (o != null) {
            getCurrentSession().update(o);
        }
    }

    public void saveOrUpdate(T o) {
        if (o != null) {
            getCurrentSession().saveOrUpdate(o);
        }
    }

    public T getById(Class<T> c, Serializable id) {
        return (T) getCurrentSession().get(c, id);
    }

    public T getByHql(String hql) {
        Query q = getCurrentSession().createQuery(hql);
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    public T getByHql(String hql, Map<String, Object> params) {
        Query q = getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    public List<T> find(String hql) {
        Query q = getCurrentSession().createQuery(hql);
        return q.list();
    }

    public List<LabelValue> findToLabelValue(String hql) {
        Query q = getCurrentSession().createQuery(hql);
        List<Object> temp = q.list();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (int i = 0; i < temp.size(); i++) {
            Object[] objs = (Object[]) temp.get(i);
            LabelValue lv = new LabelValue(objs[1].toString(), objs[0].toString());
            list.add(lv);
        }
        return list;
    }


    public List<T> find(String hql, Map<String, Object> params) {
        Query q = getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.list();
    }

    public List<T> find(String hql, int page, int rows) {
        Query q = getCurrentSession().createQuery(hql);
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
        Query q = getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    public Long count(String hql) {
        Query q = getCurrentSession().createQuery(hql);
        return (Long) q.uniqueResult();
    }


    public Long count(String hql, Map<String, Object> params) {
        Query q = getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return (Long) q.uniqueResult();
    }

    public int minOrMax(String hql, Map<String, Object> params) {
        Query q = getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        Object result = q.uniqueResult();
        if (result != null) {
            return (Integer) result;
        }
        return 0;
    }

    public int executeHql(String hql) {
        Query q = getCurrentSession().createQuery(hql);
        return q.executeUpdate();
    }

    public int executeHql(String hql, Map<String, Object> params) {
        Query q = getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.executeUpdate();
    }

    public List<Object> findClumnBySql(String sql) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        return q.list();
    }

    public List<Map> findBySql(String sql) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    public List<Map> findBySql(String sql, int page, int rows) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    public List<Map> findBySql(String sql, Map<String, Object> params) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    public List<Map> findBySql(String sql, Map<String, Object> params, int page, int rows) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    public List<Object> findBySql(String sql, Class target) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        return q.addEntity(target).list();
    }

    public List<Object> findBySql(String sql, int page, int rows, Class target) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        return q.addEntity(target).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    public List<Object> findBySql(String sql, Map<String, Object> params, Class target) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.addEntity(target).list();
    }

    public List<Object> findBySql(String sql, Map<String, Object> params, int page, int rows, Class target) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.addEntity(target).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    public int executeSql(String sql) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        return q.executeUpdate();
    }

    public int executeSql(String sql, Map<String, Object> params) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.executeUpdate();
    }

    public BigInteger countBySql(String sql) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        return (BigInteger) q.uniqueResult();
    }

    public BigInteger countBySql(String sql, Map<String, Object> params) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return (BigInteger) q.uniqueResult();
    }

}
