package com.lvtulife.base.component.excel;

/**
 * Created with IntelliJ IDEA.
 * User: Taven
 * Date: 13-7-6
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
public class ColumnFiled {

    private String filedName;
    private int CIndex = 0;
    private int width = 15;
    private boolean notNull = false;
    private String beanFiledName;

    public ColumnFiled(String filedName, int cIndex, int width, boolean notNull) {
        this.filedName = filedName;
        this.CIndex = cIndex;
        this.width = width;
        this.notNull = notNull;

    }

    public ColumnFiled(String filedName, int cIndex, int width) {
        this.filedName = filedName;
        this.CIndex = cIndex;
        this.width = width;

    }

    public ColumnFiled(String filedName, int cIndex) {
        this.filedName = filedName;
        this.CIndex = cIndex;

    }

    public ColumnFiled(String filedName, String beanFiledName, int cIndex, int width) {
        this.filedName = filedName;
        this.CIndex = cIndex;
        this.width = width;
        this.beanFiledName = beanFiledName;

    }

    public ColumnFiled(String filedName, String beanFiledName, int cIndex) {
        this.filedName = filedName;
        this.CIndex = cIndex;
        this.beanFiledName = beanFiledName;

    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public int getCIndex() {
        return CIndex;
    }

    public void setCIndex(int cIndex) {
        CIndex = cIndex;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getBeanFiledName() {
        return beanFiledName;
    }

    public void setBeanFiledName(String beanFiledName) {
        this.beanFiledName = beanFiledName;
    }

}
