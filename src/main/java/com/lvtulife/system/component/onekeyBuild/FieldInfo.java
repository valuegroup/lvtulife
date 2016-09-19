package com.lvtulife.system.component.onekeyBuild;

public class FieldInfo {

    private String fieldName;// 属性名称
    private String fieldType;// 属性类型
    private String typeShort;// 类型简称

    private Boolean isPrimaryKey;// 是否为主键
    private Boolean isNullable; // 是否不能为空
    private String columnName;// 数据库列名称
    private String columnType;// 数据库列类型
    private String columnComments;// 数据库列注释
    private Integer columnLength; // 数据库列长度
    private Integer columnPrecision = 12;//数据库列精度
    private Integer columnScale = 2;//数据库列小数位数

    public Boolean getNullable() {
        return isNullable;
    }

    public void setNullable(Boolean nullable) {
        isNullable = nullable;
    }

    public Integer getColumnScale() {
        return columnScale;
    }

    public void setColumnScale(Integer columnScale) {
        this.columnScale = columnScale;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getTypeShort() {
        return typeShort;
    }

    public void setTypeShort(String typeShort) {
        this.typeShort = typeShort;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public Integer getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(Integer columnLength) {
        this.columnLength = columnLength;
    }

    public String getColumnComments() {
        return columnComments;
    }

    public void setColumnComments(String columnComments) {
        this.columnComments = columnComments;
    }

    public Boolean getPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public Integer getColumnPrecision() {
        return columnPrecision;
    }

    public void setColumnPrecision(Integer columnPrecision) {
        this.columnPrecision = columnPrecision;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("属性名称:").append(fieldName).append("\t");
        str.append("属性类型:").append(fieldType).append("\t");
        str.append("类型简称:").append(typeShort).append("\t");
        str.append("是否为主键:").append(isPrimaryKey).append("\t");
        str.append("是否不能为空:").append(isNullable).append("\t");
        str.append("数据库列名称:").append(columnName).append("\t");
        str.append("数据库列类型:").append(columnType).append("\t");
        str.append("数据库列注释:").append(columnComments).append("\t");
        str.append("数据库列长度:").append(columnLength).append("\t");
        str.append("数据库列精度:").append(columnPrecision).append("\t");
        str.append("数据库列小数位数:").append(columnScale).append("\t");
        return str.toString();
    }
}
