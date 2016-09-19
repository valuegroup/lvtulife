package com.lvtulife.base.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by lvtulife on 2016/3/24 0024.
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "base_demo")
public class BaseDemo {
    private Integer id;
    private Integer uid;
    private String title;
    private Byte star;
    private Byte top;
    private Byte sort;
    private String remarks;
    private Date createdDt;
    private Date updatedDt;
    private Byte del;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id",unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uid", nullable = false)
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "star", nullable = false)
    public Byte getStar() {
        return star;
    }

    public void setStar(Byte star) {
        this.star = star;
    }

    @Basic
    @Column(name = "top", nullable = false)
    public Byte getTop() {
        return top;
    }

    public void setTop(Byte top) {
        this.top = top;
    }

    @Basic
    @Column(name = "sort", nullable = false)
    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "remarks", nullable = false, length = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

        BaseDemo baseDemo = (BaseDemo) o;

        if (id != null ? !id.equals(baseDemo.id) : baseDemo.id != null) return false;
        if (uid != null ? !uid.equals(baseDemo.uid) : baseDemo.uid != null) return false;
        if (title != null ? !title.equals(baseDemo.title) : baseDemo.title != null) return false;
        if (star != null ? !star.equals(baseDemo.star) : baseDemo.star != null) return false;
        if (top != null ? !top.equals(baseDemo.top) : baseDemo.top != null) return false;
        if (sort != null ? !sort.equals(baseDemo.sort) : baseDemo.sort != null) return false;
        if (remarks != null ? !remarks.equals(baseDemo.remarks) : baseDemo.remarks != null) return false;
        if (createdDt != null ? !createdDt.equals(baseDemo.createdDt) : baseDemo.createdDt != null) return false;
        if (updatedDt != null ? !updatedDt.equals(baseDemo.updatedDt) : baseDemo.updatedDt != null) return false;
        if (del != null ? !del.equals(baseDemo.del) : baseDemo.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (star != null ? star.hashCode() : 0);
        result = 31 * result + (top != null ? top.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (createdDt != null ? createdDt.hashCode() : 0);
        result = 31 * result + (updatedDt != null ? updatedDt.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
