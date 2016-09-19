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
@Table(name = "base_dictionary")
public class BaseDictionary {
    private Integer id;
    private Byte dtype;
    private String tnameCn;
    private String tnameEn;
    private String tvalueCn;
    private String tvalueEn;
    private Date createdDt;
    private Date updatedDt;
    private Integer sort;
    private String remark;
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
    @Column(name = "dtype", nullable = false)
    public Byte getDtype() {
        return dtype;
    }

    public void setDtype(Byte dtype) {
        this.dtype = dtype;
    }

    @Basic
    @Column(name = "tname_cn", nullable = false, length = 100)
    public String getTnameCn() {
        return tnameCn;
    }

    public void setTnameCn(String tnameCn) {
        this.tnameCn = tnameCn;
    }

    @Basic
    @Column(name = "tname_en", nullable = false, length = 100)
    public String getTnameEn() {
        return tnameEn;
    }

    public void setTnameEn(String tnameEn) {
        this.tnameEn = tnameEn;
    }

    @Basic
    @Column(name = "tvalue_cn", nullable = false, length = 100)
    public String getTvalueCn() {
        return tvalueCn;
    }

    public void setTvalueCn(String tvalueCn) {
        this.tvalueCn = tvalueCn;
    }

    @Basic
    @Column(name = "tvalue_en", nullable = false, length = 100)
    public String getTvalueEn() {
        return tvalueEn;
    }

    public void setTvalueEn(String tvalueEn) {
        this.tvalueEn = tvalueEn;
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
    @Column(name = "sort", nullable = true)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

        BaseDictionary that = (BaseDictionary) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dtype != null ? !dtype.equals(that.dtype) : that.dtype != null) return false;
        if (tnameCn != null ? !tnameCn.equals(that.tnameCn) : that.tnameCn != null) return false;
        if (tnameEn != null ? !tnameEn.equals(that.tnameEn) : that.tnameEn != null) return false;
        if (tvalueCn != null ? !tvalueCn.equals(that.tvalueCn) : that.tvalueCn != null) return false;
        if (tvalueEn != null ? !tvalueEn.equals(that.tvalueEn) : that.tvalueEn != null) return false;
        if (createdDt != null ? !createdDt.equals(that.createdDt) : that.createdDt != null) return false;
        if (updatedDt != null ? !updatedDt.equals(that.updatedDt) : that.updatedDt != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (del != null ? !del.equals(that.del) : that.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dtype != null ? dtype.hashCode() : 0);
        result = 31 * result + (tnameCn != null ? tnameCn.hashCode() : 0);
        result = 31 * result + (tnameEn != null ? tnameEn.hashCode() : 0);
        result = 31 * result + (tvalueCn != null ? tvalueCn.hashCode() : 0);
        result = 31 * result + (tvalueEn != null ? tvalueEn.hashCode() : 0);
        result = 31 * result + (createdDt != null ? createdDt.hashCode() : 0);
        result = 31 * result + (updatedDt != null ? updatedDt.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
