package com.lvtulife.system.component.vo;

/**
 * 系统资源权限关联业务类
 *
 * @author valuegroup
 */
public class RespriVo implements java.io.Serializable {
    private String respriId;
    private String resId;
    private String priId;
    private String priName;
    private Object attributes;
    private Integer seq;

    public String getRespriId() {
        return respriId;
    }

    public void setRespriId(String respriId) {
        this.respriId = respriId;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

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

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

}
