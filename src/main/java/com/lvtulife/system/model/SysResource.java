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
@Table(name = "sys_resource")
public class SysResource {
    private Integer id;
    private Integer typeId;
    private Integer pid;
    private Byte resAttr;
    private String resName;

    private String urlHead;
    private String urlModule;
    private String urlMethod;
    private String bewrite;
    private String iconCls;
    private String target;
    private Byte resLevel;
    private Byte resLeaf;
    private Byte isAuthPoint;
    private Integer sort;
    private Date createdDt;
    private Date updatedDt;
    private Byte del;

    public SysResource() {
    }

    public SysResource(String urlHead, String urlModule, String urlMethod) {
        this.urlHead = urlHead;
        this.urlModule = urlModule;
        this.urlMethod = urlMethod;
    }

    public SysResource(Integer id, Integer typeId, Integer pid, Byte resAttr, String resName, String urlHead, String urlModule, String urlMethod, String bewrite, String iconCls, String target, Byte resLevel, Byte resLeaf, Byte isAuthPoint, Integer sort, Date createdDt, Date updatedDt, Byte del) {
        this.id = id;
        this.typeId = typeId;
        this.pid = pid;
        this.resAttr = resAttr;
        this.resName = resName;
        this.urlHead = urlHead;
        this.urlModule = urlModule;
        this.urlMethod = urlMethod;
        this.bewrite = bewrite;
        this.iconCls = iconCls;
        this.target = target;
        this.resLevel = resLevel;
        this.resLeaf = resLeaf;
        this.isAuthPoint = isAuthPoint;
        this.sort = sort;
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
    @Column(name = "type_id", nullable = false)
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "pid", nullable = false)
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "res_attr", nullable = false)
    public Byte getResAttr() {
        return resAttr;
    }

    public void setResAttr(Byte resAttr) {
        this.resAttr = resAttr;
    }

    @Basic
    @Column(name = "res_name", nullable = false, length = 50)
    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    @Basic
    @Column(name = "url_head", nullable = true, length = 50)
    public String getUrlHead() {
        return urlHead;
    }

    public void setUrlHead(String urlHead) {
        this.urlHead = urlHead;
    }

    @Basic
    @Column(name = "url_module", nullable = true, length = 50)
    public String getUrlModule() {
        return urlModule;
    }

    public void setUrlModule(String urlModule) {
        this.urlModule = urlModule;
    }

    @Basic
    @Column(name = "url_method", nullable = true, length = 100)
    public String getUrlMethod() {
        return urlMethod;
    }

    public void setUrlMethod(String urlMethod) {
        this.urlMethod = urlMethod;
    }

/*
    @Basic
    @Column(name = "url_path", nullable = true, length = 100)
    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
*/


    @Basic
    @Column(name = "bewrite", nullable = true, length = 200)
    public String getBewrite() {
        return bewrite;
    }

    public void setBewrite(String bewrite) {
        this.bewrite = bewrite;
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
    @Column(name = "target", nullable = true, length = 50)
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Basic
    @Column(name = "res_level", nullable = false)
    public Byte getResLevel() {
        return resLevel;
    }

    public void setResLevel(Byte resLevel) {
        this.resLevel = resLevel;
    }

    @Basic
    @Column(name = "res_leaf", nullable = false)
    public Byte getResLeaf() {
        return resLeaf;
    }

    public void setResLeaf(Byte resLeaf) {
        this.resLeaf = resLeaf;
    }

    @Basic
    @Column(name = "is_auth_point", nullable = false)
    public Byte getIsAuthPoint() {
        return isAuthPoint;
    }

    public void setIsAuthPoint(Byte isAuthPoint) {
        this.isAuthPoint = isAuthPoint;
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

        SysResource that = (SysResource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
        if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
        if (resName != null ? !resName.equals(that.resName) : that.resName != null) return false;
        if (resAttr != null ? !resAttr.equals(that.resAttr) : that.resAttr != null) return false;
        if (urlHead != null ? !urlHead.equals(that.urlHead) : that.urlHead != null) return false;
        if (isAuthPoint != null ? !isAuthPoint.equals(that.isAuthPoint) : that.isAuthPoint != null) return false;
        if (urlMethod != null ? !urlMethod.equals(that.urlMethod) : that.urlMethod != null) return false;
        if (urlModule != null ? !urlModule.equals(that.urlModule) : that.urlModule != null) return false;
        if (bewrite != null ? !bewrite.equals(that.bewrite) : that.bewrite != null) return false;
        if (iconCls != null ? !iconCls.equals(that.iconCls) : that.iconCls != null) return false;
        if (target != null ? !target.equals(that.target) : that.target != null) return false;
        if (resLevel != null ? !resLevel.equals(that.resLevel) : that.resLevel != null) return false;
        if (resLeaf != null ? !resLeaf.equals(that.resLeaf) : that.resLeaf != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
        if (createdDt != null ? !createdDt.equals(that.createdDt) : that.createdDt != null) return false;
        if (updatedDt != null ? !updatedDt.equals(that.updatedDt) : that.updatedDt != null) return false;
        if (del != null ? !del.equals(that.del) : that.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (resName != null ? resName.hashCode() : 0);
        result = 31 * result + (resAttr != null ? resAttr.hashCode() : 0);
        result = 31 * result + (isAuthPoint != null ? isAuthPoint.hashCode() : 0);
        result = 31 * result + (urlHead != null ? urlHead.hashCode() : 0);
        result = 31 * result + (urlMethod != null ? urlMethod.hashCode() : 0);
        result = 31 * result + (urlModule != null ? urlModule.hashCode() : 0);
        result = 31 * result + (bewrite != null ? bewrite.hashCode() : 0);
        result = 31 * result + (iconCls != null ? iconCls.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (resLevel != null ? resLevel.hashCode() : 0);
        result = 31 * result + (resLeaf != null ? resLeaf.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (createdDt != null ? createdDt.hashCode() : 0);
        result = 31 * result + (updatedDt != null ? updatedDt.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
