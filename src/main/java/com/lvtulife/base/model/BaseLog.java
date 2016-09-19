package com.lvtulife.base.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2016/6/2 0002.
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "base_log")
public class BaseLog {
    private Integer id;
    private Byte logType;
    private Byte methodType;
    private String clazz;
    private String method;
    private String params;
    private String description;
    private String exCode;
    private String exDetail;
    private String requestIp;
    private Integer createdId;
    private Date createdDt;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "log_type")
    public Byte getLogType() {
        return logType;
    }

    public void setLogType(Byte logType) {
        this.logType = logType;
    }

    @Basic
    @Column(name = "method_type")
    public Byte getMethodType() {
        return methodType;
    }

    public void setMethodType(Byte methodType) {
        this.methodType = methodType;
    }

    @Basic
    @Column(name = "method")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Basic
    @Column(name = "clazz")
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Basic
    @Column(name = "params")
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "ex_code")
    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    @Basic
    @Column(name = "ex_detail")
    public String getExDetail() {
        return exDetail;
    }

    public void setExDetail(String exDetail) {
        this.exDetail = exDetail;
    }

    @Basic
    @Column(name = "request_ip")
    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    @Basic
    @Column(name = "created_id")
    public Integer getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Integer createdId) {
        this.createdId = createdId;
    }

    @Basic
    @Column(name = "created_dt")
    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseLog baseLog = (BaseLog) o;

        if (id != null ? !id.equals(baseLog.id) : baseLog.id != null) return false;
        if (logType != null ? !logType.equals(baseLog.logType) : baseLog.logType != null) return false;
        if (methodType != null ? !methodType.equals(baseLog.methodType) : baseLog.methodType != null) return false;
        if (method != null ? !method.equals(baseLog.method) : baseLog.method != null) return false;
        if (clazz != null ? !clazz.equals(baseLog.clazz) : baseLog.clazz != null) return false;
        if (params != null ? !params.equals(baseLog.params) : baseLog.params != null) return false;
        if (description != null ? !description.equals(baseLog.description) : baseLog.description != null) return false;
        if (exCode != null ? !exCode.equals(baseLog.exCode) : baseLog.exCode != null) return false;
        if (exDetail != null ? !exDetail.equals(baseLog.exDetail) : baseLog.exDetail != null) return false;
        if (requestIp != null ? !requestIp.equals(baseLog.requestIp) : baseLog.requestIp != null) return false;
        if (createdId != null ? !createdId.equals(baseLog.createdId) : baseLog.createdId != null) return false;
        if (createdDt != null ? !createdDt.equals(baseLog.createdDt) : baseLog.createdDt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (logType != null ? logType.hashCode() : 0);
        result = 31 * result + (methodType != null ? methodType.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        result = 31 * result + (params != null ? params.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (exCode != null ? exCode.hashCode() : 0);
        result = 31 * result + (exDetail != null ? exDetail.hashCode() : 0);
        result = 31 * result + (requestIp != null ? requestIp.hashCode() : 0);
        result = 31 * result + (createdId != null ? createdId.hashCode() : 0);
        result = 31 * result + (createdDt != null ? createdDt.hashCode() : 0);
        return result;
    }
}
