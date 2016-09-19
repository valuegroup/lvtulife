package com.lvtulife.system.component.onekeyBuild;

/**
 * pojo:实体类名称 field:实体类字段  dataType:数据类型 dataTypeShort:数据类型简写  table:表名 column:数据库列 columnComments:数据库列中文注释
 */
public class FieldColumn {
    private String pojo = null;//实体类名称
    private String pojoAsName = null;//实体类别名
    private String pojoPackage = null;
    private String field = null;//实体类字段
    private String table = null;//表名
    private String tableAsName = null;//表别名
    private String ynMain = null;//是否主表
    private String dataType = null;//数据类型
    private String dbdataType = null;//从数据库查出来的数据类型
    private String dataTypeShort = null;//数据类型简写
    private String column = null;//数据库列
    private String columnComments = null;//数据库列中文注释
    private Integer length = 0;//长度
    private Integer numericPrecision = 12;//精度
    private Integer numericScale = 2;//小数位数
    private Boolean isPrimaryKey = false; //是否为主键

    public FieldColumn(String pojo, String field, String dataType, String dataTypeShort) {
        this.pojo = pojo;
        this.field = field;
        this.dataType = dataType;
        this.dataTypeShort = dataTypeShort;
    }

    public FieldColumn(String pojo, String pojoAsName, String pojoPackage, String table, String tableAsName, String ynMain) {
        this.pojo = pojo;
        this.pojoAsName = pojoAsName;
        this.pojoPackage = pojoPackage;
        this.table = table;
        this.tableAsName = tableAsName;
        this.ynMain = ynMain;
    }

    public FieldColumn(String pojo, String pojoAsName, String pojoPackage, String field, String table, String tableAsName, String ynMain, String dataType, String dataTypeShort, String column, String columnComments) {
        this.pojo = pojo;
        this.pojoAsName = pojoAsName;
        this.pojoPackage = pojoPackage;
        this.field = field;
        this.table = table;
        this.tableAsName = tableAsName;
        this.ynMain = ynMain;
        this.dataType = dataType;
        this.dataTypeShort = dataTypeShort;
        this.column = column;
        this.columnComments = columnComments;
    }

    public String getPojo() {
        return pojo;
    }

    public void setPojo(String pojo) {
        this.pojo = pojo;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDbdataType() {
        return dbdataType;
    }

    public void setDbdataType(String dbdataType) {
        this.dbdataType = dbdataType;
    }

    public String getDataTypeShort() {
        return dataTypeShort;
    }

    public void setDataTypeShort(String dataTypeShort) {
        this.dataTypeShort = dataTypeShort;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getColumnComments() {
        return columnComments;
    }

    public void setColumnComments(String columnComments) {
        this.columnComments = columnComments;
    }

    public String getPojoAsName() {
        return pojoAsName;
    }

    public void setPojoAsName(String pojoAsName) {
        this.pojoAsName = pojoAsName;
    }

    public String getTableAsName() {
        return tableAsName;
    }

    public void setTableAsName(String tableAsName) {
        this.tableAsName = tableAsName;
    }

    public String getYnMain() {
        return ynMain;
    }

    public void setYnMain(String ynMain) {
        this.ynMain = ynMain;
    }

    public String getPojoPackage() {
        return pojoPackage;
    }

    public void setPojoPackage(String pojoPackage) {
        this.pojoPackage = pojoPackage;
    }

    public Boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getNumericPrecision() {
        return numericPrecision;
    }

    public void setNumericPrecision(Integer numericPrecision) {
        this.numericPrecision = numericPrecision;
    }

    public Integer getNumericScale() {
        return numericScale;
    }

    public void setNumericScale(Integer numericScale) {
        this.numericScale = numericScale;
    }

    /**
     * Compare LabelValueBeans based on the label, because that's the human
     * viewable part of the object.
     *
     * @see Comparable
     */
    public int compareTo(Object o) {
        // Implicitly tests for the correct type, throwing
        // ClassCastException as required by interface
        String otherField = ((FieldColumn) o).getField();

        return this.getField().compareTo(otherField);
    }

    /**
     * Return a string representation of this object.
     * json
     * pojo:实体类名称 field:实体类字段  dataType:数据类型 dataTypeShort:数据类型简写  table:表名 column:数据库列 columnComments:数据库列中文注释
     */
    public String toJson() {
        StringBuffer sb = new StringBuffer("FieldColumn[");
        sb.append(this.pojo);
        sb.append(", ");
        sb.append(this.pojoAsName);
        sb.append(", ");
        sb.append(this.pojoPackage);
        sb.append(", ");
        sb.append(this.field);
        sb.append(", ");
        sb.append(this.dataType);
        sb.append(", ");
        sb.append(this.dataTypeShort);
        sb.append(", ");
        sb.append(this.table);
        sb.append(", ");
        sb.append(this.tableAsName);
        sb.append(", ");
        sb.append(this.ynMain);
        sb.append(", ");
        sb.append(this.column);
        sb.append(", ");
        sb.append(this.columnComments);
        sb.append(", ");
        sb.append(this.isPrimaryKey);
        sb.append(", ");
        sb.append(this.length);
        sb.append(", ");
        sb.append(this.numericPrecision);
        sb.append(", ");
        sb.append(this.numericScale);
        sb.append("]");
        return (sb.toString());
    }

    /**
     * Return a string representation of this object.
     * json
     * pojo:实体类名称 field:实体类字段  dataType:数据类型 dataTypeShort:数据类型简写  table:表名 column:数据库列 columnComments:数据库列中文注释
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        sb.append(this.pojo);
        sb.append(", ");
        sb.append(this.pojoAsName);
        sb.append(", ");
        sb.append(this.pojoPackage);
        sb.append(", ");
        sb.append(this.field);
        sb.append(", ");
        sb.append(this.dataType);
        sb.append(", ");
        sb.append(this.dataTypeShort);
        sb.append(", ");
        sb.append(this.table);
        sb.append(", ");
        sb.append(this.tableAsName);
        sb.append(", ");
        sb.append(this.ynMain);
        sb.append(", ");
        sb.append(this.column);
        sb.append(", ");
        sb.append(this.columnComments);
        sb.append(", ");
        sb.append(this.isPrimaryKey);
        sb.append(", ");
        sb.append(this.numericPrecision);
        sb.append(", ");
        sb.append(this.numericScale);
        return (sb.toString());
    }

    /**
     * Return a string representation of this object.
     * json
     * pojo:实体类名称 field:实体类字段  dataType:数据类型 dataTypeShort:数据类型简写  table:表名 column:数据库列 columnComments:数据库列中文注释
     */
    public String toPxString() {
        StringBuffer sb = new StringBuffer("");
        sb.append("\"");
        sb.append(this.pojo);
        sb.append("\",\"");
        sb.append(this.pojoAsName);
        sb.append("\",\"");
        sb.append(this.pojoPackage);
        sb.append("\",\"");
        sb.append(this.field);
        sb.append("\",\"");
        sb.append(this.dataType);
        sb.append("\",\"");
        sb.append(this.dataTypeShort);
        sb.append("\",\"");
        sb.append(this.table);
        sb.append("\",\"");
        sb.append(this.tableAsName);
        sb.append("\",\"");
        sb.append(this.ynMain);
        sb.append("\",\"");
        sb.append(this.column);
        sb.append("\",\"");
        sb.append(this.columnComments);
        sb.append("\",\"");
        sb.append(this.length);
        sb.append("\",\"");
        sb.append(this.numericPrecision);
        sb.append("\",\"");
        sb.append(this.numericScale);
        sb.append("\",\"");
        sb.append(this.isPrimaryKey);
        sb.append("\"");
        return (sb.toString());
    }

    /**
     * LabelValueBeans are equal if their values are both null or equal.
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof FieldColumn)) {
            return false;
        }

        FieldColumn bean = (FieldColumn) obj;
        int nil = (this.getField() == null) ? 1 : 0;
        nil += (bean.getField() == null) ? 1 : 0;

        if (nil == 2) {
            return true;
        } else if (nil == 1) {
            return false;
        } else {
            return this.getField().equals(bean.getField());
        }

    }

    /**
     * The hash code is based on the object's value.
     *
     * @see Object#hashCode()
     */
    public int hashCode() {
        return (this.getField() == null) ? 17 : this.getField().hashCode();
    }
}
