package com.lvtulife.base.mapper.impl;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.base.mapper.BaseMapperI;
import com.lvtulife.base.service.impl.BaseServiceImpl;
import com.lvtulife.base.utils.GenericsUtils;
import com.lvtulife.base.utils.ReflectionUtils;
import com.lvtulife.base.utils.SQLGenerator;
import com.lvtulife.base.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public abstract class BaseMapperImpl<T> extends BaseServiceImpl<T> implements BaseMapperI<T> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource(name = "mybatisSessionTemplate")
    public SqlSessionTemplate mybatisSessionTemplate;


    private Class<T> entityClass;
    private String tableName;
    /**
     * 作cache 结构{T类的镜像,{数据库列名,实体字段名}}
     */
    private static final Map<Class<?>, Map<String, String>> classFieldMap = new HashMap<Class<?>, Map<String, String>>();
    private Map<String, String> currentColumnFieldNames;

    private SQLGenerator<T> sqlGenerator;

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public BaseMapperImpl() {
        super();


        this.entityClass = (Class<T>) GenericsUtils.getSuperClassGenricType(this.getClass());

        currentColumnFieldNames = classFieldMap.get(entityClass);
        if (null == currentColumnFieldNames) {
            currentColumnFieldNames = new LinkedHashMap<String, String>();
            classFieldMap.put(entityClass, currentColumnFieldNames);
        }


        // 获取字段的get方法上的注解 例如
        // @Column(name = "id", unique = true, nullable = false)
        // public Integer getId() {return id;}
        Method[] methods = this.entityClass.getMethods();
        Field[] fields = this.entityClass.getDeclaredFields();

        Map<String, Annotation[]> methodAnnoMap = new HashMap<String, Annotation[]>();
        for (int j = 0; j < methods.length; j++) {
            Annotation[] annotations = methods[j].getAnnotations();
            methodAnnoMap.put(methods[j].getName(), annotations);
        }


        String fieldName, columnName = null;
        for (Field field : fields) {
            //Hibernate 中表示为临时 @Transient
            /*if (field.isAnnotationPresent(Ignore.class)) {
                continue;
            }*/

            // 获取字段上的注解 例如
            // @Column(name="id")
            // private Integer id;
            /*Column tableColumn = field.getAnnotation(Column.class);
            if (null != tableColumn) {
                columnName = tableColumn.name();
            } else {
                columnName = null;
            }*/

            fieldName = field.getName();

            Annotation[] annotations = methodAnnoMap.get("get" + StringUtil.capitalToUpper(fieldName));
            if (annotations != null && annotations.length > 0) {
                boolean ignore = false;
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Id || annotation instanceof Transient) {
                        ignore = true;
                    }

                    if (annotation instanceof Column) {
                        Column column = (Column) annotation;
                        columnName = column.name();
                    }
                }
                // 忽略此字段
                if (ignore) {
                    continue;
                }

                // 如果未标识特殊的列名，则转换成下划线形式，如 userName = user_name
                columnName = (StringUtils.isEmpty(columnName) ? StringUtil.replaceFirstUpperToUnderline(fieldName) : columnName);
                currentColumnFieldNames.put(columnName, fieldName);
            }
        }

        Entity table = this.entityClass.getAnnotation(Entity.class);
        if (null == table) {
            throw new RuntimeException("类-" + this.entityClass + ",未用@Table注解标识!!");
        }
        tableName = table.name();
        sqlGenerator = new SQLGenerator<T>(currentColumnFieldNames.keySet(), tableName);
    }

    /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void createToMyBatis(T t) {
        mybatisSessionTemplate.insert("create", sqlGenerator.sql_create(t, currentColumnFieldNames));
    }*/

    /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void createOfBatchToMyBatis(List<T> tList) {
        if (null == tList || tList.isEmpty()) {
            return;
        }
        int len = tList.size(), i = 0;
        List<T> temp = new ArrayList<T>();
        for (; i < len; i++) {
            T t = tList.get(i);

            temp.add(t);
            if (i > 0 && i % SysConstants.FLUSH_CRITICAL_VAL == 0) {
                String sql = sqlGenerator.sql_createOfBatch(temp, currentColumnFieldNames);
                mybatisSessionTemplate.insert("createOfBatch", sql);
                mybatisSessionTemplate.flushStatements();
                temp = new ArrayList<T>();
            }
        }
        if (!temp.isEmpty()) {
            mybatisSessionTemplate.insert("createOfBatch", sqlGenerator.sql_createOfBatch(temp, currentColumnFieldNames));
        }
    }
*/
    /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void createOfBatchNonFlushToMyBatis(List<T> tList) {
        if (null == tList || tList.isEmpty()) {
            return;
        }
        int len = tList.size(), i = 0;
        List<T> temp = new ArrayList<T>();
        //获取列表的第一个对象的pk的value
        for (; i < len; i++) {
            T t = tList.get(i);

            temp.add(t);
            if (i > 0 && i % SysConstants.FLUSH_BIG_CRITICAL_VAL == 0) {
                String sql = sqlGenerator.sql_createOfBatch(temp, currentColumnFieldNames);
                mybatisSessionTemplate.insert("createOfBatch", sql);
                temp = new ArrayList<T>();
            }
        }
        if (!temp.isEmpty()) {
            mybatisSessionTemplate.insert("createOfBatch", sqlGenerator.sql_createOfBatch(temp, currentColumnFieldNames));
        }
    }*/

    /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Integer saveToMyBatis(T t) {
        this.createToMyBatis(t);
        return (Integer) ReflectionUtils.invokeGetterMethod(t, "id");
    }*/

    /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<Integer> saveOfBatchToMyBatis(List<T> tList) {
        if (null == tList || tList.isEmpty()) {
            return null;
        }
        List<Integer> pkList = new ArrayList<>();
        for (T t : tList) {
            Integer id = saveToMyBatis(t);
            pkList.add(id);
        }
        return pkList;
    }*/

    /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removeByIdToMyBatis(Integer id) {
        mybatisSessionTemplate.delete("removeById", sqlGenerator.sql_removeById(id));
    }
*/
    /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removeOfBatchToMyBatis(List<Integer> ids) {
        if (null == ids || ids.isEmpty()) {
            return;
        }
        int len = ids.size(), i = 0;
        List<Integer> temp = new ArrayList<Integer>();
        for (; i < len; i++) {
            temp.add(ids.get(i));
            if (i > 0 && i % SysConstants.FLUSH_CRITICAL_VAL == 0) {
                mybatisSessionTemplate.delete("removeOfBatch", sqlGenerator.sql_removeOfBatch(temp));
                mybatisSessionTemplate.flushStatements();
                temp = new ArrayList<Integer>();
            }
        }
        if (!temp.isEmpty()) {
            mybatisSessionTemplate.delete("removeOfBatch", sqlGenerator.sql_removeOfBatch(temp));
        }
    }*/

   /* @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removeObjOfBatchByColumnToMyBatis(List<T> list, String fieldName) {
        if (null == list || list.isEmpty()) {
            return;
        }
        int len = list.size(), i = 0;
        List<T> temp = new ArrayList<T>();
        for (; i < len; i++) {
            T obj = list.get(i);
            temp.add(obj);
            if (i > 0 && i % SysConstants.FLUSH_CRITICAL_VAL == 0) {
                mybatisSessionTemplate.delete("removeOfBatch", sqlGenerator.sql_removeOfBatchByColumn(temp, currentColumnFieldNames, fieldName));
                mybatisSessionTemplate.flushStatements();
                temp = new ArrayList<T>();
            }
        }
        if (!temp.isEmpty()) {
            mybatisSessionTemplate.delete("removeOfBatch", sqlGenerator.sql_removeOfBatchByColumn(temp, currentColumnFieldNames, fieldName));
        }
    }*/

    /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removeByColumnNameToMyBatis(String columnName, Object val,
                                            String... symbol) {
        if (null == val || StringUtils.isEmpty(columnName)) {
            return;
        }
        mybatisSessionTemplate.delete("removeByColumnName", sqlGenerator.sql_removeByColumnName(columnName, val, symbol));
    }*/

    /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removeOfBatchByColumnNameToMyBatis(String columnName,
                                                   Object... vals) {
        if (null == vals || vals.length <= 0) {
            return;
        }
        int len = vals.length, i = 0;
        List<Object> temp = new ArrayList<Object>();
        for (; i < len; i++) {
            temp.add(vals[i]);
            if (i > 0 && i % SysConstants.FLUSH_CRITICAL_VAL == 0) {
                mybatisSessionTemplate.delete("removeOfBatch", sqlGenerator.sql_removeOfBatchByColumnName(columnName, temp));
                mybatisSessionTemplate.flushStatements();
                temp = new ArrayList<Object>();
            }
        }
        if (!temp.isEmpty()) {
            mybatisSessionTemplate.delete("removeOfBatch", sqlGenerator.sql_removeOfBatchByColumnName(columnName, temp));
        }
    }*/

    /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void removeAllToMyBatis() {
        mybatisSessionTemplate.delete("removeAll", sqlGenerator.sql_removeAll());
    }
*/
    /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void modifyToMyBatis(T t, Boolean... ignoreNull) {
        mybatisSessionTemplate.update("modify", sqlGenerator.sql_modify(t, currentColumnFieldNames, ignoreNull));
    }
*/
    /*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void modifyOfBatchToMyBatis(List<T> tList, Boolean... ignoreNull) {
        if (null == tList || tList.isEmpty()) {
            return;
        }
        int len = tList.size(), i = 0;
        for (; i < len; i++) {
            this.modifyToMyBatis(tList.get(i), ignoreNull);
            if (i > 0 && i % SysConstants.FLUSH_CRITICAL_VAL == 0) {
                mybatisSessionTemplate.flushStatements();
            }
        }
    }*/


    public T findOneByIdToMyBatis(Integer id) {
        Map<String, Object> resultMap = mybatisSessionTemplate.selectOne("findOneById", sqlGenerator.sql_findOneById(id));
        return handleResultToMyBatis(resultMap, this.entityClass);
    }


    public T findOneByColumnNameToMyBatis(String columnName, Object val,
                                          String... symbol) {
        Map<String, Object> resultMap = mybatisSessionTemplate.selectOne("findOneByColumnName", sqlGenerator.findOneByColumnName(columnName, val, symbol));
        T t = handleResultToMyBatis(resultMap, this.entityClass);
        return t;
    }


    public List<T> findListByColumnNameToMyBatis(String columnName, Object val, String... symbol) {
        List<Map<String, Object>> resultMapList = mybatisSessionTemplate
                .selectList("findListByColumnName", sqlGenerator.sql_findListByColumnName(columnName, val, symbol));
        List<T> tList = new ArrayList<T>(resultMapList.size());
        for (Map<String, Object> resultMap : resultMapList) {
            T t = handleResultToMyBatis(resultMap, this.entityClass);
            tList.add(t);
        }
        return tList;
    }


    public List<T> findAllToMyBatis() {
        List<Map<String, Object>> resultMapList = mybatisSessionTemplate
                .selectList("findAll", sqlGenerator.sql_findAll());
        List<T> tList = new ArrayList<T>(resultMapList.size());
        for (Map<String, Object> resultMap : resultMapList) {
            T t = handleResultToMyBatis(resultMap, this.entityClass);
            tList.add(t);
        }
        return tList;
    }


    public Long findAllCountToMyBatis() {
        Long count = mybatisSessionTemplate
                .selectOne("findAllCount", sqlGenerator.sql_findAllCount());
        return count;
    }


    @SuppressWarnings("unchecked")
    public Boolean isExistsToMyBatis(Integer id) {
        Boolean result = mybatisSessionTemplate.selectOne("isExists", sqlGenerator.sql_isExists(id));
        return result;
    }

    public Boolean isExistsToMyBatis(String columnName, Object val, String... symbol) {
        Boolean result = mybatisSessionTemplate.selectOne("isExistsByColumn", sqlGenerator.sql_isExists(columnName, val, symbol));
        return result;
    }

    private T handleResultToMyBatis(Map<String, Object> resultMap, Class<T> tClazz) {
        T t = null;
        if (null == resultMap) {
            return t;
        }
        try {
            t = tClazz.newInstance();
        } catch (InstantiationException e) {
            logger.error("/********************************");
            logger.error("封装查询结果时，实例化对象(" + this.entityClass + ")时，出现异常!" + e.getMessage());
            logger.error("/********************************");
        } catch (IllegalAccessException e) {
            logger.error("/********************************");
            logger.error("封装查询结果时，实例化对象(" + this.entityClass + ")时，出现异常!" + e.getMessage());
            logger.error("/********************************");
        }
        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            String dbKey = entry.getKey();
            String entityKey = currentColumnFieldNames.get(dbKey);
            if (StringUtils.isNotEmpty(entityKey)) {
                Object val = entry.getValue();
                ReflectionUtils.invokeSetterMethod(t, entityKey, val);
            } else {
                if (!StringUtils.equalsIgnoreCase("rownum_", dbKey)) {//rownum_ 为mybatis 分页拦截器产生
                    logger.error(tClazz.getName() + "中,不存在映射到" + dbKey + "的字段!");
                }
            }
        }
        return t;
    }

}
