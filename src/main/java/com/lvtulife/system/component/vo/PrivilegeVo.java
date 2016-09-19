package com.lvtulife.system.component.vo;

/**
 * 系统资源权限点业务对象
 *
 * @author valuegroup
 */
public class PrivilegeVo implements java.io.Serializable {
    private String priId;
    private String priName;
    private String priMark;
    private String status;
    private Object attributes;

    public String getPriId() {
        return priId;
    }

    public void setPriId(String priId) {
        this.priId = priId;
    }

    public String getPriName() {
        return priName;
    }

    public void setPriName(String priName) {
        this.priName = priName;
    }

    public String getPriMark() {
        return priMark;
    }

    public void setPriMark(String priMark) {
        this.priMark = priMark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

}
