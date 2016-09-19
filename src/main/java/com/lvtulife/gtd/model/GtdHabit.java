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
@Table(name = "gtd_habit")
public class GtdHabit {
    private Integer id;
    private Integer uid;
    private String title;
    private String site;
    private Byte repeatType;
    private Byte repeatRestrict;
    private Byte expiryTimes;
    private Byte expiryDtType;
    private Date expiryDt;
    private Integer aid;
    private String habitNo;
    private Byte star;
    private Byte top;
    private String remarks;
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
    @Column(name = "site", nullable = true, length = 20)
    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Basic
    @Column(name = "repeat_type", nullable = false)
    public Byte getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(Byte repeatType) {
        this.repeatType = repeatType;
    }

    @Basic
    @Column(name = "repeat_restrict", nullable = false)
    public Byte getRepeatRestrict() {
        return repeatRestrict;
    }

    public void setRepeatRestrict(Byte repeatRestrict) {
        this.repeatRestrict = repeatRestrict;
    }

    @Basic
    @Column(name = "expiry_times", nullable = true)
    public Byte getExpiryTimes() {
        return expiryTimes;
    }

    public void setExpiryTimes(Byte expiryTimes) {
        this.expiryTimes = expiryTimes;
    }

    @Basic
    @Column(name = "expiry_dt_type", nullable = true)
    public Byte getExpiryDtType() {
        return expiryDtType;
    }

    public void setExpiryDtType(Byte expiryDtType) {
        this.expiryDtType = expiryDtType;
    }

    @Basic
    @Column(name = "expiry_dt", nullable = true)
    public Date getExpiryDt() {
        return expiryDt;
    }

    public void setExpiryDt(Date expiryDt) {
        this.expiryDt = expiryDt;
    }

    @Basic
    @Column(name = "aid", nullable = true)
    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    @Basic
    @Column(name = "habit_no", nullable = false, length = 20)
    public String getHabitNo() {
        return habitNo;
    }

    public void setHabitNo(String habitNo) {
        this.habitNo = habitNo;
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

        GtdHabit gtdHabit = (GtdHabit) o;

        if (id != null ? !id.equals(gtdHabit.id) : gtdHabit.id != null) return false;
        if (uid != null ? !uid.equals(gtdHabit.uid) : gtdHabit.uid != null) return false;
        if (title != null ? !title.equals(gtdHabit.title) : gtdHabit.title != null) return false;
        if (site != null ? !site.equals(gtdHabit.site) : gtdHabit.site != null) return false;
        if (repeatType != null ? !repeatType.equals(gtdHabit.repeatType) : gtdHabit.repeatType != null) return false;
        if (repeatRestrict != null ? !repeatRestrict.equals(gtdHabit.repeatRestrict) : gtdHabit.repeatRestrict != null)
            return false;
        if (expiryTimes != null ? !expiryTimes.equals(gtdHabit.expiryTimes) : gtdHabit.expiryTimes != null)
            return false;
        if (expiryDtType != null ? !expiryDtType.equals(gtdHabit.expiryDtType) : gtdHabit.expiryDtType != null)
            return false;
        if (expiryDt != null ? !expiryDt.equals(gtdHabit.expiryDt) : gtdHabit.expiryDt != null) return false;
        if (aid != null ? !aid.equals(gtdHabit.aid) : gtdHabit.aid != null) return false;
        if (habitNo != null ? !habitNo.equals(gtdHabit.habitNo) : gtdHabit.habitNo != null) return false;
        if (star != null ? !star.equals(gtdHabit.star) : gtdHabit.star != null) return false;
        if (top != null ? !top.equals(gtdHabit.top) : gtdHabit.top != null) return false;
        if (remarks != null ? !remarks.equals(gtdHabit.remarks) : gtdHabit.remarks != null) return false;
        if (createdDt != null ? !createdDt.equals(gtdHabit.createdDt) : gtdHabit.createdDt != null) return false;
        if (del != null ? !del.equals(gtdHabit.del) : gtdHabit.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (site != null ? site.hashCode() : 0);
        result = 31 * result + (repeatType != null ? repeatType.hashCode() : 0);
        result = 31 * result + (repeatRestrict != null ? repeatRestrict.hashCode() : 0);
        result = 31 * result + (expiryTimes != null ? expiryTimes.hashCode() : 0);
        result = 31 * result + (expiryDtType != null ? expiryDtType.hashCode() : 0);
        result = 31 * result + (expiryDt != null ? expiryDt.hashCode() : 0);
        result = 31 * result + (aid != null ? aid.hashCode() : 0);
        result = 31 * result + (habitNo != null ? habitNo.hashCode() : 0);
        result = 31 * result + (star != null ? star.hashCode() : 0);
        result = 31 * result + (top != null ? top.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (createdDt != null ? createdDt.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
