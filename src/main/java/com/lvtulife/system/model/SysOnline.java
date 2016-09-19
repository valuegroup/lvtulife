package com.lvtulife.system.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by lvtulife on 2016-03-24 .
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "sys_online")
public class SysOnline {
    private Integer id;
    private String ip;
    private String loginName;
    private Byte olType;
    private Date createdDt;
    private Byte del;

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
    @Column(name = "ip", nullable = true, length = 100)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "login_name", nullable = true, length = 100)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Basic
    @Column(name = "ol_type", nullable = false)
    public Byte getOlType() {
        return olType;
    }

    public void setOlType(Byte olType) {
        this.olType = olType;
    }

    @Basic
    @Column(name = "created_dt", nullable = false)
    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    @Basic
    @Column(name = "del", nullable = false)
    public Byte getDel() {
        return del;
    }

    public void setDel(Byte del) {
        this.del = del;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysOnline sysOnline = (SysOnline) o;

        if (id != null ? !id.equals(sysOnline.id) : sysOnline.id != null) return false;
        if (ip != null ? !ip.equals(sysOnline.ip) : sysOnline.ip != null) return false;
        if (loginName != null ? !loginName.equals(sysOnline.loginName) : sysOnline.loginName != null) return false;
        if (olType != null ? !olType.equals(sysOnline.olType) : sysOnline.olType != null) return false;
        if (createdDt != null ? !createdDt.equals(sysOnline.createdDt) : sysOnline.createdDt != null) return false;
        if (del != null ? !del.equals(sysOnline.del) : sysOnline.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (olType != null ? olType.hashCode() : 0);
        result = 31 * result + (createdDt != null ? createdDt.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
