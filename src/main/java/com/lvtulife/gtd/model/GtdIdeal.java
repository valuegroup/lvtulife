package com.lvtulife.gtd.model;

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
@Table(name = "gtd_ideal")
public class GtdIdeal {
    private Integer id;
    private Integer uid;
    private Integer pid;
    private String ideal;
    private String detail;
    private String cause;
    private String giCode;
    private Date hopeDt;
    private Byte giStatus;
    private Byte top;
    private Byte sort;
    private Date createdDt;
    private Date updatedDt;
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
    @Column(name = "uid", nullable = false)
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "pid", nullable = true)
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "ideal", nullable = false, length = 255)
    public String getIdeal() {
        return ideal;
    }

    public void setIdeal(String ideal) {
        this.ideal = ideal;
    }

    @Basic
    @Column(name = "detail", nullable = false, length = 255)
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Basic
    @Column(name = "cause", nullable = false, length = 255)
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Basic
    @Column(name = "gi_code", nullable = false, length = 20)
    public String getGiCode() {
        return giCode;
    }

    public void setGiCode(String giCode) {
        this.giCode = giCode;
    }

    @Basic
    @Column(name = "hope_dt", nullable = false)
    public Date getHopeDt() {
        return hopeDt;
    }

    public void setHopeDt(Date hopeDt) {
        this.hopeDt = hopeDt;
    }

    @Basic
    @Column(name = "gi_status", nullable = false)
    public Byte getGiStatus() {
        return giStatus;
    }

    public void setGiStatus(Byte giStatus) {
        this.giStatus = giStatus;
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

        GtdIdeal gtdIdeal = (GtdIdeal) o;

        if (id != null ? !id.equals(gtdIdeal.id) : gtdIdeal.id != null) return false;
        if (uid != null ? !uid.equals(gtdIdeal.uid) : gtdIdeal.uid != null) return false;
        if (pid != null ? !pid.equals(gtdIdeal.pid) : gtdIdeal.pid != null) return false;
        if (ideal != null ? !ideal.equals(gtdIdeal.ideal) : gtdIdeal.ideal != null) return false;
        if (detail != null ? !detail.equals(gtdIdeal.detail) : gtdIdeal.detail != null) return false;
        if (cause != null ? !cause.equals(gtdIdeal.cause) : gtdIdeal.cause != null) return false;
        if (giCode != null ? !giCode.equals(gtdIdeal.giCode) : gtdIdeal.giCode != null) return false;
        if (hopeDt != null ? !hopeDt.equals(gtdIdeal.hopeDt) : gtdIdeal.hopeDt != null) return false;
        if (giStatus != null ? !giStatus.equals(gtdIdeal.giStatus) : gtdIdeal.giStatus != null) return false;
        if (top != null ? !top.equals(gtdIdeal.top) : gtdIdeal.top != null) return false;
        if (sort != null ? !sort.equals(gtdIdeal.sort) : gtdIdeal.sort != null) return false;
        if (createdDt != null ? !createdDt.equals(gtdIdeal.createdDt) : gtdIdeal.createdDt != null) return false;
        if (updatedDt != null ? !updatedDt.equals(gtdIdeal.updatedDt) : gtdIdeal.updatedDt != null) return false;
        if (del != null ? !del.equals(gtdIdeal.del) : gtdIdeal.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (ideal != null ? ideal.hashCode() : 0);
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        result = 31 * result + (cause != null ? cause.hashCode() : 0);
        result = 31 * result + (giCode != null ? giCode.hashCode() : 0);
        result = 31 * result + (hopeDt != null ? hopeDt.hashCode() : 0);
        result = 31 * result + (giStatus != null ? giStatus.hashCode() : 0);
        result = 31 * result + (top != null ? top.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (createdDt != null ? createdDt.hashCode() : 0);
        result = 31 * result + (updatedDt != null ? updatedDt.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
