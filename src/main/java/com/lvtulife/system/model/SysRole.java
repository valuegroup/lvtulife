package com.lvtulife.system.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by lvtulife on 2016-03-24 .
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "sys_role")
public class SysRole implements Serializable {
    private Integer id;
    private String roleName;
    private String roleNameEn;
    private Byte rstatus;
    private String iconCls;
    private String bewrite;
    private String resTypeIds;
    private Integer sort;
    private Byte source;
    private Date createdDt;
    private Date updatedDt;
    private Byte del;

    public SysRole() {
    }

    public SysRole(Integer id, String roleName, String roleNameEn, Byte rstatus, String iconCls, String bewrite, String resTypeIds, Integer sort, Byte source, Date createdDt, Date updatedDt, Byte del) {
        this.id = id;
        this.roleName = roleName;
        this.roleNameEn = roleNameEn;
        this.rstatus = rstatus;
        this.iconCls = iconCls;
        this.bewrite = bewrite;
        this.resTypeIds = resTypeIds;
        this.sort = sort;
        this.source = source;
        this.createdDt = createdDt;
        this.updatedDt = updatedDt;
        this.del = del;
    }

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
    @Column(name = "role_name", nullable = false, length = 20)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "role_name_en", nullable = false, length = 20)
    public String getRoleNameEn() {
        return roleNameEn;
    }

    public void setRoleNameEn(String roleNameEn) {
        this.roleNameEn = roleNameEn;
    }

    @Basic
    @Column(name = "rstatus", nullable = false)
    public Byte getRstatus() {
        return rstatus;
    }

    public void setRstatus(Byte rstatus) {
        this.rstatus = rstatus;
    }

    @Basic
    @Column(name = "icon_cls", nullable = true, length = 100)
    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    @Basic
    @Column(name = "bewrite", nullable = true, length = 200)
    public String getBewrite() {
        return bewrite;
    }

    public void setBewrite(String bewrite) {
        this.bewrite = bewrite;
    }

    @Basic
    @Column(name = "res_type_ids", nullable = true, length = 100)
    public String getResTypeIds() {
        return resTypeIds;
    }

    public void setResTypeIds(String resTypeIds) {
        this.resTypeIds = resTypeIds;
    }

    @Basic
    @Column(name = "sort", nullable = false)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "source", nullable = false)
    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
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
    @Column(name = "updated_dt", nullable = false)
    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
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

        SysRole sysRole = (SysRole) o;

        if (id != null ? !id.equals(sysRole.id) : sysRole.id != null) return false;
        if (roleName != null ? !roleName.equals(sysRole.roleName) : sysRole.roleName != null) return false;
        if (roleNameEn != null ? !roleNameEn.equals(sysRole.roleNameEn) : sysRole.roleNameEn != null) return false;
        if (rstatus != null ? !rstatus.equals(sysRole.rstatus) : sysRole.rstatus != null) return false;
        if (iconCls != null ? !iconCls.equals(sysRole.iconCls) : sysRole.iconCls != null) return false;
        if (bewrite != null ? !bewrite.equals(sysRole.bewrite) : sysRole.bewrite != null) return false;
        if (resTypeIds != null ? !resTypeIds.equals(sysRole.resTypeIds) : sysRole.resTypeIds != null) return false;
        if (sort != null ? !sort.equals(sysRole.sort) : sysRole.sort != null) return false;
        if (createdDt != null ? !createdDt.equals(sysRole.createdDt) : sysRole.createdDt != null) return false;
        if (updatedDt != null ? !updatedDt.equals(sysRole.updatedDt) : sysRole.updatedDt != null) return false;
        if (del != null ? !del.equals(sysRole.del) : sysRole.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (roleNameEn != null ? roleNameEn.hashCode() : 0);
        result = 31 * result + (rstatus != null ? rstatus.hashCode() : 0);
        result = 31 * result + (iconCls != null ? iconCls.hashCode() : 0);
        result = 31 * result + (bewrite != null ? bewrite.hashCode() : 0);
        result = 31 * result + (resTypeIds != null ? resTypeIds.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (createdDt != null ? createdDt.hashCode() : 0);
        result = 31 * result + (updatedDt != null ? updatedDt.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
