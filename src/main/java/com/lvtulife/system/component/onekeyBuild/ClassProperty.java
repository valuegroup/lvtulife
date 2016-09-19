package com.lvtulife.system.component.onekeyBuild;

import com.lvtulife.base.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ClassProperty {
    private static final Logger logger = LoggerFactory.getLogger(ClassProperty.class);

    public static List<FieldInfo> getClassPropertyList(String classPackage) {
        try {
            return getClassPropertyList(Class.forName(classPackage));
        } catch (Exception ex) {
            logger.error("", ex);
        }
        return new ArrayList<FieldInfo>();
    }

    public static List<FieldInfo> getClassPropertyList(Class clazz) {
        ArrayList<FieldInfo> list = new ArrayList<FieldInfo>();
        try {
            HashMap<String, FieldInfo> fieldMap = getFieldMapForDB(clazz);

            Field[] field = clazz.getDeclaredFields();
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                String type = field[j].getGenericType().toString(); // 获取属性的类型
                String typeShort = getTypeShort(type);// 获取属性类型的缩写

                FieldInfo data = fieldMap.get(StringUtil.replaceFirstUpperToUnderline(name));
                if (data == null) {
                    data = new FieldInfo();
                }
                data.setFieldName(name);
                data.setFieldType(type);
                data.setTypeShort(typeShort);

                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getTableName(String classPackage) {
        try {
            return getTableName(Class.forName(classPackage));
        } catch (Exception e) {
            logger.error("获取实体类表名称异常:" + classPackage, e);
        }
        return "";
    }

    public static String getTableName(Class clazz) {
        String tableName = "";

        // Hibernate注解中获取 @Table(name = "sys_user")
        /*try {
            Annotation[] anno = clazz.getAnnotations();
            for (int i = 0; i < anno.length; i++) {
                if (anno[i] instanceof Table) {
                    Table table = (Table) anno[i];
                    tableName = table.name();
                }
                if (anno[i] instanceof Entity) {
                    Entity table = (Entity) anno[i];
                    tableName = table.name();
                }
            }
        } catch (Exception e) {
            logger.error("获取Hibernate注解失败！", e);
        }*/

        // 命名方式转换 SysUser to sys_user
        if (StringUtils.isBlank(tableName)) {
            tableName = StringUtil.replaceFirstUpperToUnderline(clazz.getSimpleName());
        }

        return tableName;
    }

    public static HashMap<String, String> getTableMapForDB() {
        String tableSchema = PropertyResourceBundle.getBundle("db").getString("jdbc.table_schema");
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT table_name, table_comment ");
        sql.append("FROM information_schema.tables ");
        sql.append("WHERE 1=1 ");
        sql.append("AND table_schema ='").append(tableSchema).append("' ");// eztpf数据库名 table_schema ='lvtulife-paas'

        Info info = new Info();
        info.setPage(0);
        info.setSql(sql.toString());
        info.query();

        // 将结果方式map，并返回
        HashMap<String, String> tableMap = new HashMap<String, String>();
        while (info.moveNext()) {
            tableMap.put(info.getVal("table_name"), info.getVal("table_comment"));// 表注释
        }
        return tableMap;
    }

    /**
     * 从数据库中获取字段信息
     *
     * @param clazz
     * @return
     */
    public static HashMap<String, FieldInfo> getFieldMapForDB(Class clazz) {
        // 从数据库中获取表结构信息
        String tableName = getTableName(clazz);
        String tableSchema = PropertyResourceBundle.getBundle("db").getString("jdbc.table_schema");
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT column_name, column_comment, column_key, character_maximum_length, numeric_precision, numeric_scale, data_type, is_nullable  ");
        sql.append("FROM information_schema.columns ");
        sql.append("WHERE 1=1 ");
        sql.append("AND table_schema ='").append(tableSchema).append("' ");// eztpf数据库名 table_schema ='lvtulife-paas'
        sql.append("AND table_name = '").append(tableName).append("' ");

        Info info = new Info();
        info.setPage(0);
        info.setSql(sql.toString());
        info.query();

        // 将结果方式map，并返回
        HashMap<String, FieldInfo> fieldMap = new HashMap<String, FieldInfo>();
        while (info.moveNext()) {

            FieldInfo data = new FieldInfo();
            data.setColumnName(info.getVal("column_name"));// 列名

            String comments = info.getVal("column_comment");
            data.setColumnComments(StringUtils.isBlank(comments) ? "" : comments.trim().replace("：", ":").split(":")[0]);// 注释

            if (info.getVal("character_maximum_length") != null)
                data.setColumnLength(Integer.parseInt(info.getVal("character_maximum_length")));//长度精度小数位数

            if (info.getVal("numeric_precision") != null)
                data.setColumnPrecision(Integer.parseInt(info.getVal("numeric_precision")));//小数位数

            if (info.getVal("numeric_scale") != null)
                data.setColumnScale(Integer.parseInt(info.getVal("numeric_scale")));//小数精度

            if (info.getVal("is_nullable").equals("YES")) {
                data.setNullable(true);
            } else {
                data.setNullable(false);
            }

            data.setColumnType(info.getVal("data_type"));//数据类型

            data.setPrimaryKey(false);//主键
            if ("PRI".equals(info.getVal("column_key"))) {
                data.setPrimaryKey(true);
            }

            fieldMap.put(data.getColumnName(), data);
        }

        return fieldMap;
    }

    public static String getTypeShort(String type) {
        String typeShort = "Other";
        if (type.equals("class java.lang.String")) {
            typeShort = "S";
        } else if (type.equals("class java.lang.Long") || type.equals("long")) {
            typeShort = "L";
        } else if (type.equals("class java.lang.Integer")) {
            typeShort = "I";
        } else if (type.equals("class java.util.Date")) {
            typeShort = "D";
        } else if (type.equals("class java.lang.Short") || type.equals("short")) {
            typeShort = "ST";
        } else if (type.equals("class java.math.BigDecimal")) {
            typeShort = "BD";
        } else if (type.equals("class java.lang.Float")) {
            typeShort = "FT";
        } else if (type.equals("class java.lang.Double") || type.equals("double")) {
            typeShort = "DB";
        } else if (type.equals("class java.lang.Byte")) {
            typeShort = "BT";
        } else if (type.equals("java.lang.Boolean") || type.equals("boolean")) {
            typeShort = "BL";
        }
        return typeShort;
    }


    /**
     * 读取Hibernate的Table注解，获得数据库中的表名称
     *
     * @param classtype
     * @return
     */
    public static String getTableNameAnnotation(Class classtype) {
        Annotation[] anno = classtype.getAnnotations();
        String tableName = "";
        for (int i = 0; i < anno.length; i++) {
            if (anno[i] instanceof Table) {
                Table table = (Table) anno[i];
                tableName = table.name();
            }
        }
        return tableName;
    }

    /**
     * 读取Hibernate的Column注解，获得数据库中的字段名称
     *
     * @param classtype
     * @return
     */
    public static Map<String, String> getColumnsAnnotationMap(Class classtype) {
        Field[] fields = classtype.getDeclaredFields();
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < fields.length; i++) {
            Method[] methods = classtype.getMethods();
            for (int j = 0; j < methods.length; j++) {
                if (methods[j].getName().equals("get" + StringUtil.capitalToUpper(fields[i].getName()))) {
                    Annotation[] annotations = methods[j].getAnnotations();
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Column) {
                            Column column = (Column) annotation;
                            map.put(fields[i].getName(), column.name());
                        }
                    }
                }
            }
        }
        return map;
    }


    public static void main(String[] args) {
        /*ClassProperty pro = new ClassProperty();
        List<FieldColumn> cols = pro.getClassPropertyList(SysRole.class);
        for (FieldColumn col : cols) {
            System.out.println(col.getPojo() + "\t" + col.getField() + "\t" + col.getDataType() + "\t" + col.getDataTypeShort());
        }*/

        /*Map<String, String> map = getColumnsAnnotationMap(SysUser.class);
        for (String key : map.keySet()) {
            System.out.println("key= " + key + " \t value= " + map.get(key));
        }*/

        /*System.out.println(StringUtil.replaceFirstUpperToUnderline("userNameHHaa"));
        System.out.println(StringUtil.replaceUnderlineAndFirstToUpper("user_name_h_haa"));*/

        /*List<FieldInfo> list = getClassPropertyList(SysUser.class);
        for (FieldInfo field : list) {
            System.out.println(field.toString());
        }*/
    }


    /*TABLE_CATALOG 	nvarchar(128) 	表值函数的目录或数据库名称。
    TABLE_SCHEMA 	nvarchar(128) 	表值函数的所有者。
	TABLE_NAME 	nvarchar(128) 	表值函数的名称。
	COLUMN_NAME 	nvarchar(128) 	列名。
	ORDINAL_POSITION 	smallint 	列标识号。
	COLUMN_DEFAULT 	nvarchar(4000) 	列的默认值。
	IS_NULLABLE 	varchar(3) 	如果该列允许 NULL，那么返回 YES。否则，返回 NO。
	DATA_TYPE 	nvarchar(128) 	系统提供的数据类型。
	CHARACTER_MAXIMUM_LENGTH 	smallint 	以字符为单位的最大长度，适于二进制数据、字符数据，或者文本和图像数据。否则，返回 NULL。有关更多信息，请参见数据类型。
	CHARACTER_OCTET_LENGTH 	smallint 	以字节为单位的最大长度，适于二进制数据、字符数据，或者文本和图像数据。否则，返回 NULL。
	NUMERIC_PRECISION 	tinyint 	近似数字数据、精确数字数据、整型数据或货币数据的精度。否则，返回 NULL。
	NUMERIC_PRECISION_RADIX 	smallint 	近似数字数据、精确数字数据、整型数据或货币数据的精度基数。否则，返回 NULL。
	NUMERIC_SCALE 	tinyint 	近似数字数据、精确数字数据、整数数据或货币数据的小数位数。否则，返回 NULL。
	DATETIME_PRECISION 	smallint 	datetime 及 SQL-92 integer 数据类型的子类型代码。对于其它数据类型，返回 NULL。
	CHARACTER_SET_CATALOG 	varchar(6) 	如果列是字符数据或 text 数据类型，那么返回 master，指明字符集所在的数据库。否则，返回 NULL。
	CHARACTER_SET_SCHEMA 	varchar(3) 	如果列是字符数据或 text 数据类型，那么返回 DBO，指明字符集的所有者名称。否则，返回 NULL。
	CHARACTER_SET_NAME 	nvarchar(128) 	如果该列是字符数据或 text 数据类型，那么为字符集返回唯一的名称。否则，返回 NULL。
	COLLATION_CATALOG 	varchar(6) 	如果列是字符数据或 text 数据类型，那么返回 master，指明在其中定义排序次序的数据库。否则，返回 NULL。
	COLLATION_SCHEMA 	varchar(3) 	返回 DBO，为字符数据或 text 数据类型指明排序次序的所有者。否则，返回 NULL。
	COLLATION_NAME 	nvarchar(128) 	如果列是字符数据或 text 数据类型，那么为排序次序返回唯一的名称。否则，返回 NULL。
	DOMAIN_CATALOG 	nvarchar(128) 	如果列是一种用户定义数据类型，那么该列是某个数据库名称，在该数据库名中创建了这种用户定义数据类型。否则，返回 NULL。
	DOMAIN_SCHEMA 	nvarchar(128) 	如果列是一种用户定义数据类型，那么该列是这种用户定义数据类型的创建者。否则，返回 NULL。
	DOMAIN_NAME 	nvarchar(128) 	如果列是一种用户定义数据类型，那么该列是这种用户定义数据类型的名称。否则，返回 NULL。*/


    //方法1：<T extends Annotation> T getAnnotation(Class<T> annotationClass): 返回改程序元素上存在的、指定类型的注解，如果该类型注解不存在，则返回null。
    //方法2：Annotation[] getAnnotations():返回该程序元素上存在的所有注解。
    //方法3：boolean is AnnotationPresent(Class<?extends Annotation> annotationClass):判断该程序元素上是否包含指定类型的注解，存在则返回true，否则返回false.
    //方法4：Annotation[] getDeclaredAnnotations()：返回直接存在于此元素上的所有注释。与此接口中的其他方法不同，该方法将忽略继承的注释。（如果没有注释直接存在于此元素上，则返回长度为零的一个数组。）该方法的调用者可以随意修改返回的数组；这不会对其他调用者返回的数组产生任何影响。
}