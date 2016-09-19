package com.lvtulife.base.utils;

import com.lvtulife.base.component.constants.SysConstants;
import com.lvtulife.system.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Description: 生成查询数量的SQL
 */
public class SQLGenerator<T> {

    /**
     * 批量删除时,多少个数量级,重新使用or连接 Oracle IN语句的最大表达式数为 1000
     */
    public static final Integer DELETE_CRITICAL_VAL = 999;


    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private Set<String> columns;
    private String tableName;
    private String columnsStr;

    public SQLGenerator(Set<String> columns, String tableName) {
        super();
        this.columns = columns;
        this.tableName = tableName;
        this.columnsStr = StringUtils.join(this.columns, ",");
    }

    /**
     * 生成新增的SQL
     *
     * @param t
     * @param currentColumnFieldNames
     * @return
     */
    public String sql_create(T t, Map<String, String> currentColumnFieldNames) {
        List<Object> values = obtainFieldValues(t, currentColumnFieldNames);

        StringBuilder sql_build = new StringBuilder();
        sql_build.append("INSERT INTO ").append(tableName).append("(")
                .append(columnsStr).append(")values(")
                .append(StringUtils.join(values, ",")).append(")");
        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;
    }

    /**
     * 生成批量新增的SQL
     *
     * @param tList
     * @param currentColumnFieldNames
     * @return
     */
    public String sql_createOfBatch(List<T> tList,
                                    Map<String, String> currentColumnFieldNames) {
        StringBuilder sql_build = new StringBuilder();
        int len = tList.size(), i = 0;


        sql_build.append("INSERT INTO ").append(tableName).append("(")
                .append(columnsStr).append(") VALUES ");
        for (; i < len; i++) {
            T t = tList.get(i);
            List<Object> values = obtainFieldValues(t, currentColumnFieldNames);
            sql_build.append("(").append(StringUtils.join(values, ",")).append(")");
            if (i != len - 1) {
                sql_build.append(",");
            }
        }

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;
    }

    /**
     * 提供给生成新增SQL 使用
     *
     * @param t
     * @param currentColumnFieldNames
     * @return
     */
    private List<Object> obtainFieldValues(T t, Map<String, String> currentColumnFieldNames) {
        List<Object> values = new LinkedList<Object>();
        for (String column : columns) {
            Object value = ReflectionUtils.obtainFieldValue(t, currentColumnFieldNames.get(column));
            values.add(handleValue(value));
        }
        return values;
    }

    /**
     * 处理value
     *
     * @param value
     * @return
     */
    private Object handleValue(Object value) {
        if (value instanceof String) {
            value = "\'" + value + "\'";
        } else if (value instanceof Date) {
            Date date = (Date) value;
            String dateStr = DateUtil.convertDateToString(date,
                    DateUtil.TIMEF_FORMAT);
            value = "DATE_FORMAT('" + dateStr
                    + "','%Y-%m-%d %T')";
        } else if (value instanceof Boolean) {
            Boolean v = (Boolean) value;
            value = v ? 1 : 0;
        } else if (null == value || StringUtils.isBlank(value.toString())) {
            value = "''";
        }
        return value;
    }

    /**
     * 生成根据ID删除的SQL
     *
     * @param id
     * @return
     */
    public <PK> String sql_removeById(PK id) {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("DELETE FROM ").append(this.tableName)
                .append(" WHERE ").append(" id ").append(" = ").append(handleValue(id));

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;
    }


    /**
     * @param columnName
     * @param val
     * @param symbol
     * @return
     */
    public String sql_removeByColumnName(String columnName, Object val,
                                         String... symbol) {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("DELETE FROM ").append(this.tableName)
                .append(" WHERE ").append(columnName).append(SQLSymbol.obtainFirstSymbol(symbol)).append(handleValue(val));

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;
    }

    /**
     * 生成根据IDs批量删除的SQL
     *
     * @param ids
     * @return
     */
    public <PK> String sql_removeOfBatch(List<PK> ids) {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("DELETE FROM ").append(this.tableName)
                .append(" WHERE ").append(" id ").append(" IN ( '0'");
        int len = ids.size(), i = 0;
        for (; i < len; i++) {
            PK id = ids.get(i);
            sql_build.append(",").append(handleValue(id));
            if (i > 0 && i % (DELETE_CRITICAL_VAL - 1) == 0) {
                sql_build.append(")").append(" OR ").append(" id ")
                        .append(" IN ( '0'");
            }
        }
        sql_build.append(")");

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;
    }

    /**
     * fieldName 用于当做条件的字段名称请用大写
     */
    public String sql_removeOfBatchByColumn(List<T> list, Map<String, String> currentColumnFieldNames, String fieldName) {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("DELETE FROM ").append(this.tableName)
                .append(" WHERE ").append(fieldName).append(" IN ( '0' ");
        int len = list.size(), i = 0;
        for (; i < len; i++) {
            T t = list.get(i);
            Object id = ReflectionUtils.obtainFieldValue(t,currentColumnFieldNames.get(fieldName));
            sql_build.append(",").append(handleValue(id));
            if (i > 0 && i % (DELETE_CRITICAL_VAL - 1) == 0) {
                sql_build.append(")").append(" OR ").append(fieldName)
                        .append(" IN ( '0'");
            }
        }
        sql_build.append(")");

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;
    }

    ;

    /**
     * fieldName 用于当做条件的字段名称请用大写
     */
    public String sql_removeOfBatchByColumnName(String columnName, List<Object> vals) {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("DELETE FROM ").append(this.tableName)
                .append(" WHERE ").append(columnName).append(" IN ( '0' ");
        int len = vals.size(), i = 0;
        for (; i < len; i++) {
            sql_build.append(",").append(handleValue(vals.get(i)));
            if (i > 0 && i % (DELETE_CRITICAL_VAL - 1) == 0) {
                sql_build.append(")").append(" OR ").append(columnName)
                        .append(" IN ( '0'");
            }
        }
        sql_build.append(")");

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;
    }

    ;

    public String sql_removeAll() {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("DELETE FROM ").append(this.tableName);
        String sql = sql_build.toString();
        logger.debug("生成的SQL为: " + sql);
        return sql;
    }

    /**
     * 生成更新的SQL
     *
     * @param t
     * @param currentColumnFieldNames
     * @param ignoreNull              忽略空值，默认为true
     * @return
     */
    public String sql_modify(T t, Map<String, String> currentColumnFieldNames, Boolean... ignoreNull) {
        List<String> values = obtainColumnVals(t, currentColumnFieldNames, ignoreNull);
        Object id = ReflectionUtils.obtainFieldValue(t, "id");
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("UPDATE ").append(tableName).append(" SET ")
                .append(StringUtils.join(values, ",")).append(" WHERE ")
                .append(" id ").append(" = ").append(handleValue(id));

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;
    }

    /**
     * 提供给生成更新SQL使用
     *
     * @param t
     * @param currentColumnFieldNames
     * @return
     */
    private List<String> obtainColumnVals(T t,
                                          Map<String, String> currentColumnFieldNames, Boolean... ignoreNull) {
        Boolean ignore = true;
        if (null != ignore && ignoreNull.length > 1) {
            ignore = ignoreNull[0];
        }

        List<String> colVals = new LinkedList<String>();
        for (String column : columns) {
            Object value = ReflectionUtils.obtainFieldValue(t,
                    currentColumnFieldNames.get(column));
            if (!StringUtils.equalsIgnoreCase(column, " id ")) {
                if (ignore && value != null) {
                    colVals.add(column + "=" + handleValue(value));
                } else if (!ignore && value == null) {
                    colVals.add(column + "= NULL ");
                }
            }
        }
        return colVals;
    }

    /**
     * 生成根据ID查询的SQL
     *
     * @param id
     * @return
     */
    public <PK> String sql_findOneById(PK id) {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("SELECT ").append("id,").append(columnsStr).append(" FROM ")
                .append(this.tableName)
                .append(" WHERE id = " + handleValue(id)).append(" limit 1");

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;

    }


    public String findOneByColumnName(String columnName, Object val,
                                      String... symbol) {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("SELECT ").append(columnsStr).append(" FROM ")
                .append(this.tableName)
                .append(" WHERE " + columnName + SQLSymbol.obtainFirstSymbol(symbol) + handleValue(val))
                .append(" AND ROWNUM = 1 ");

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;

    }

    /**
     * 生成根据指定字段查询的SQL
     */
    public <PK> String sql_findListByColumnName(String columnName, Object val, String... symbol) {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("SELECT ").append("id,").append(columnsStr).append(" FROM ")
                .append(this.tableName)
                .append(" WHERE " + columnName + SQLSymbol.obtainFirstSymbol(symbol) + handleValue(val))
                .append(" ORDER BY ").append(" id ");

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;

    }

    /**
     * 生成查询所有的SQL
     *
     * @return
     */
    public String sql_findAll() {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("SELECT ").append("id,").append(columnsStr).append(" FROM ").append(this.tableName).append(" ORDER BY ").append(" id ");

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;
    }

    /**
     * 生成查询数量的SQL
     *
     * @return
     */
    public String sql_findAllCount() {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("SELECT COUNT(1) ").append(" FROM ")
                .append(this.tableName);
        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;
    }


    public <PK> String sql_isExists(PK id) {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("SELECT 1 ").append(" FROM ")
                .append(this.tableName)
                .append(" WHERE id = " + handleValue(id)).append(" limit 1");

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;
    }

    public <PK> String sql_isExists(String columnName, Object val, String... symbol) {
        StringBuilder sql_build = new StringBuilder();
        sql_build.append("SELECT 1 ").append(" FROM ")
                .append(this.tableName)
                .append(" WHERE " + columnName + SQLSymbol.obtainFirstSymbol(symbol) + handleValue(val))
                .append(" ORDER BY ").append(" id ");

        String sql = sql_build.toString();

        logger.debug("生成的SQL为: " + sql);

        return sql;
    }


    public static void main(String[] args) {
        Class<SysUser> entityClass = SysUser.class;
        //this.pkClass = (Class<Integer>) GenericsUtils.getSuperClassGenricType(this.getClass(), 1);

        Map<Class<?>, Map<String, String>> classFieldMap = new HashMap<Class<?>, Map<String, String>>();
        Map<String, String> currentColumnFieldNames = classFieldMap.get(entityClass);
        if (null == currentColumnFieldNames) {
            currentColumnFieldNames = new LinkedHashMap<String, String>();
            classFieldMap.put(entityClass, currentColumnFieldNames);
        }


        // 获取字段的get方法上的注解 例如
        // @Column(name = "id", unique = true, nullable = false)
        // public Integer getId() {return id;}
        Method[] methods = entityClass.getMethods();
        Field[] fields = entityClass.getDeclaredFields();

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

        Entity table = entityClass.getAnnotation(Entity.class);
        if (null == table) {
            throw new RuntimeException("类-" + entityClass + ",未用@Table注解标识!!");
        }
        String tableName = table.name();
        SQLGenerator<SysUser> sqlGenerator = new SQLGenerator<SysUser>(currentColumnFieldNames.keySet(), tableName);


        SysUser user = new SysUser();
        user.setCreatedDt(new Date());
        user.setUserName("Mybatis");
        user.setLoginName("mybatis");
        user.setEmail("mybatis@mybatis.com");
        user.setIsSuper(SysConstants.SYS_NO);
        user.setDel(SysConstants.SYS_STATUS);
        user.setAge(new Byte("1"));
        user.setPhoto("haha");
        user.setPwd(MD5Util.md5("mybatis"));
        user.setSex(SysConstants.SYS_SEX_FEMALE);
        user.setUpdatedDt(new Date());
        user.setUstatus(SysConstants.SYS_USTATUS);

        List<SysUser> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            SysUser temp = new SysUser();
            BeanUtils.copyBeans(user, temp);
            temp.setLoginName(user.getLoginName() + i);
            temp.setUserName(user.getUserName() + i);
            temp.setEmail(i + user.getEmail());
            temp.setAge(new Byte("20"));
            temp.setCreatedDt(new Date());
            temp.setUpdatedDt(new Date());
            datas.add(temp);
        }


        SysUser modify = new SysUser();

        BeanUtils.copyNotNullProperties(user, modify, new String[]{
                "pwd",
                "photo",
                "sex",
                "ustatus",
                "age",
                "isSuper",
                "createdDt",
                "del"
        });
        modify.setId(21);
        modify.setLoginName("modify");
        modify.setUserName("modify");
        modify.setEmail("modify@mybatis.com");
        modify.setUpdatedDt(new Date());

        List<Object> ids = new ArrayList<>();
        ids.add("22");
        //ids.add("23");


        List<SysUser> delDatas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SysUser temp = new SysUser();
            BeanUtils.copyBeans(user, temp);
            temp.setLoginName(user.getLoginName() + i);
            temp.setUserName(user.getUserName() + i);
            temp.setEmail(i + user.getEmail());
            temp.setAge(Byte.parseByte((i * 10) + ""));
            temp.setCreatedDt(new Date());
            temp.setUpdatedDt(new Date());
            delDatas.add(temp);
        }


        List<Object> delColmun = new ArrayList<>();
        delColmun.add("f0d71b77ac2ea991f39f9dfdd5e985b3");
        delColmun.add("f0d71b77ac2ea991f39f9dfdd5e985b4");


        System.out.println("新增：" + sqlGenerator.sql_create(user, currentColumnFieldNames));

        System.out.println("批量新增：" + sqlGenerator.sql_createOfBatch(datas, currentColumnFieldNames));
        System.out.println("查询所有：" + sqlGenerator.sql_findAll());
        System.out.println("查询总量：" + sqlGenerator.sql_findAllCount());
        System.out.println("查询字段：" + sqlGenerator.sql_findListByColumnName("pwd", "f0d71b77ac2ea991f39f9dfdd5e985b5"));
        System.out.println("查询字段带操作符：" + sqlGenerator.sql_findListByColumnName("age", "20", SQLSymbol.GTE));
        System.out.println("查询一条数据：" + sqlGenerator.sql_findOneById(1));
        System.out.println("查询是否存在：" + sqlGenerator.sql_isExists(1));
        System.out.println("查询是否存在：" + sqlGenerator.sql_isExists("age", "20"));
        System.out.println("查询是否存在带操作符：" + sqlGenerator.sql_isExists("age", "20", SQLSymbol.GTE));
        System.out.println("修改：" + sqlGenerator.sql_modify(modify, currentColumnFieldNames, false));
        System.out.println("删除：" + sqlGenerator.sql_removeAll());
        System.out.println("删除：" + sqlGenerator.sql_removeByColumnName("user_name","modify"));
        System.out.println("删除：" + sqlGenerator.sql_removeById(26));
        System.out.println("删除：" + sqlGenerator.sql_removeOfBatch(ids));
        System.out.println("删除：" + sqlGenerator.sql_removeOfBatchByColumn(delDatas,currentColumnFieldNames,"login_name"));
        System.out.println("删除：" + sqlGenerator.sql_removeOfBatchByColumnName("pwd",delColmun));


        /*新增：
            INSERT INTO sys_user(user_name,login_name,pwd,email,photo,sex,ustatus,age,is_super,created_dt,updated_dt,del)
            values('Mybatis','mybatis','f0d71b77ac2ea991f39f9dfdd5e985b5','mybatis@mybatis.com','haha',0,0,1,0,DATE_FORMAT('2016-05-28 14:56:09','%Y-%m-%d %T'),DATE_FORMAT('2016-05-28 14:56:09','%Y-%m-%d %T'),0)
        批量新增：
            INSERT INTO sys_user(user_name,login_name,pwd,email,photo,sex,ustatus,age,is_super,created_dt,updated_dt,del)
            VALUES ('Mybatis0','mybatis0','f0d71b77ac2ea991f39f9dfdd5e985b5','0mybatis@mybatis.com','haha',0,0,0,0,DATE_FORMAT('2016-05-28 14:56:09','%Y-%m-%d %T'),DATE_FORMAT('2016-05-28 14:56:09','%Y-%m-%d %T'),0),('Mybatis1','mybatis1','f0d71b77ac2ea991f39f9dfdd5e985b5','1mybatis@mybatis.com','haha',0,0,10,0,DATE_FORMAT('2016-05-28 14:56:09','%Y-%m-%d %T'),DATE_FORMAT('2016-05-28 14:56:09','%Y-%m-%d %T'),0),('Mybatis2','mybatis2','f0d71b77ac2ea991f39f9dfdd5e985b5','2mybatis@mybatis.com','haha',0,0,20,0,DATE_FORMAT('2016-05-28 14:56:09','%Y-%m-%d %T'),DATE_FORMAT('2016-05-28 14:56:09','%Y-%m-%d %T'),0),('Mybatis3','mybatis3','f0d71b77ac2ea991f39f9dfdd5e985b5','3mybatis@mybatis.com','haha',0,0,30,0,DATE_FORMAT('2016-05-28 14:56:09','%Y-%m-%d %T'),DATE_FORMAT('2016-05-28 14:56:09','%Y-%m-%d %T'),0),('Mybatis4','mybatis4','f0d71b77ac2ea991f39f9dfdd5e985b5','4mybatis@mybatis.com','haha',0,0,40,0,DATE_FORMAT('2016-05-28 14:56:09','%Y-%m-%d %T'),DATE_FORMAT('2016-05-28 14:56:09','%Y-%m-%d %T'),0)
        查询所有：
            SELECT id,user_name,login_name,pwd,email,photo,sex,ustatus,age,is_super,created_dt,updated_dt,del FROM sys_user ORDER BY  id
        查询总量：
            SELECT COUNT(1)  FROM sys_user
        查询字段：
            SELECT id,user_name,login_name,pwd,email,photo,sex,ustatus,age,is_super,created_dt,updated_dt,del
            FROM sys_user
            WHERE pwd='f0d71b77ac2ea991f39f9dfdd5e985b5'
            ORDER BY  id
        查询字段带操作符：
            SELECT id,user_name,login_name,pwd,email,photo,sex,ustatus,age,is_super,created_dt,updated_dt,del
            FROM sys_user WHERE age>='20' ORDER BY  id
        查询一条数据：
            SELECT id,user_name,login_name,pwd,email,photo,sex,ustatus,age,is_super,created_dt,updated_dt,del
            FROM sys_user WHERE id = 1 limit 1
        查询是否存在：
            SELECT 1  FROM sys_user WHERE id = 1 limit 1
        查询是否存在：
            SELECT 1  FROM sys_user WHERE age='20' ORDER BY  id
        查询是否存在带操作符：
            SELECT 1  FROM sys_user WHERE age>='20' ORDER BY  id
        修改：
            UPDATE sys_user
            SET user_name='modify',login_name='modify',email='modify@mybatis.com',updated_dt=DATE_FORMAT('2016-05-28 14:56:10','%Y-%m-%d %T')
            WHERE  id  = 21
        删除：
            DELETE FROM sys_user
        删除：
            DELETE FROM sys_user WHERE user_name='modify'
        删除：
            DELETE FROM sys_user WHERE  id  = 26
        删除：
            DELETE FROM sys_user WHERE  id  IN ( '0','22')
        删除：
            DELETE FROM sys_user WHERE login_name IN ( '0' ,'mybatis0','mybatis1','mybatis2')
        删除：
            DELETE FROM sys_user WHERE pwd IN ( '0' ,'f0d71b77ac2ea991f39f9dfdd5e985b3','f0d71b77ac2ea991f39f9dfdd5e985b4')*/


        System.out.println("**********************");

        /*if (null == datas || datas.isEmpty()) {
            return;
        }
        int len = datas.size(), i = 0;
        List<SysUser> temp = new ArrayList<SysUser>();
        //获取列表的第一个对象的pk的value
        for (; i < len; i++) {
            SysUser t = datas.get(i);

            temp.add(t);
            System.out.println("i:"+i % 10);
            if (i > 0 && i % 10 == 0) {
                System.out.println(sqlGenerator.sql_createOfBatch(temp, currentColumnFieldNames));
                temp = new ArrayList<SysUser>();
            }
        }
        if (!temp.isEmpty()) {
            System.out.println("xxx");
            System.out.println(sqlGenerator.sql_createOfBatch(temp, currentColumnFieldNames));
        }*/


        System.out.println(ReflectionUtils.invokeGetterMethod(modify, "id"));
    }
}
