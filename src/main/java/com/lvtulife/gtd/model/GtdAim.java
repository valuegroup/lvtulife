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
@Table(name = "gtd_aim")
public class GtdAim {
    private Integer id;
    private Integer tid;
    private Integer uid;
    private Integer giid;
    private Byte aimUnit;
    private Byte aimStatus;
    private Byte star;
    private Byte top;
    private String content;
    private String demand;
    private String aimNo;
    private String remarks;
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
    @Column(name = "tid", nullable = false)
    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
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
    @Column(name = "giid", nullable = false)
    public Integer getGiid() {
        return giid;
    }

    public void setGiid(Integer giid) {
        this.giid = giid;
    }

    @Basic
    @Column(name = "aim_unit", nullable = false)
    public Byte getAimUnit() {
        return aimUnit;
    }

    public void setAimUnit(Byte aimUnit) {
        this.aimUnit = aimUnit;
    }

    @Basic
    @Column(name = "aim_status", nullable = false)
    public Byte getAimStatus() {
        return aimStatus;
    }

    public void setAimStatus(Byte aimStatus) {
        this.aimStatus = aimStatus;
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
    @Column(name = "content", nullable = false, length = 255)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "demand", nullable = true, length = 255)
    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    @Basic
    @Column(name = "aim_no", nullable = false, length = 20)
    public String getAimNo() {
        return aimNo;
    }

    public void setAimNo(String aimNo) {
        this.aimNo = aimNo;
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

        GtdAim gtdAim = (GtdAim) o;

        if (id != null ? !id.equals(gtdAim.id) : gtdAim.id != null) return false;
        if (tid != null ? !tid.equals(gtdAim.tid) : gtdAim.tid != null) return false;
        if (uid != null ? !uid.equals(gtdAim.uid) : gtdAim.uid != null) return false;
        if (giid != null ? !giid.equals(gtdAim.giid) : gtdAim.giid != null) return false;
        if (aimUnit != null ? !aimUnit.equals(gtdAim.aimUnit) : gtdAim.aimUnit != null) return false;
        if (aimStatus != null ? !aimStatus.equals(gtdAim.aimStatus) : gtdAim.aimStatus != null) return false;
        if (star != null ? !star.equals(gtdAim.star) : gtdAim.star != null) return false;
        if (top != null ? !top.equals(gtdAim.top) : gtdAim.top != null) return false;
        if (content != null ? !content.equals(gtdAim.content) : gtdAim.content != null) return false;
        if (demand != null ? !demand.equals(gtdAim.demand) : gtdAim.demand != null) return false;
        if (aimNo != null ? !aimNo.equals(gtdAim.aimNo) : gtdAim.aimNo != null) return false;
        if (remarks != null ? !remarks.equals(gtdAim.remarks) : gtdAim.remarks != null) return false;
        if (createdDt != null ? !createdDt.equals(gtdAim.createdDt) : gtdAim.createdDt != null) return false;
        if (updatedDt != null ? !updatedDt.equals(gtdAim.updatedDt) : gtdAim.updatedDt != null) return false;
        if (del != null ? !del.equals(gtdAim.del) : gtdAim.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tid != null ? tid.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (giid != null ? giid.hashCode() : 0);
        result = 31 * result + (aimUnit != null ? aimUnit.hashCode() : 0);
        result = 31 * result + (aimStatus != null ? aimStatus.hashCode() : 0);
        result = 31 * result + (star != null ? star.hashCode() : 0);
        result = 31 * result + (top != null ? top.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (demand != null ? demand.hashCode() : 0);
        result = 31 * result + (aimNo != null ? aimNo.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (createdDt != null ? createdDt.hashCode() : 0);
        result = 31 * result + (updatedDt != null ? updatedDt.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
