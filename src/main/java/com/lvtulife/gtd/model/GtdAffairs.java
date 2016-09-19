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
@Table(name = "gtd_affairs")
public class GtdAffairs {
    private Integer id;
    private Integer uid;
    private Integer planWeek;
    private Date doDt;
    private String content;
    private String site;
    private Byte timeInterval;
    private String affNo;
    private Byte affStatus;
    private Byte sourceType;
    private Integer sourceId;
    private Byte importance;
    private Byte exeTimes;
    private Byte delayTimes;
    private Byte sort;
    private String remarks;
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
    @Column(name = "uid", nullable = false)
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "plan_week", nullable = false)
    public Integer getPlanWeek() {
        return planWeek;
    }

    public void setPlanWeek(Integer planWeek) {
        this.planWeek = planWeek;
    }

    @Basic
    @Column(name = "do_dt", nullable = false)
    public Date getDoDt() {
        return doDt;
    }

    public void setDoDt(Date doDt) {
        this.doDt = doDt;
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
    @Column(name = "site", nullable = false, length = 20)
    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Basic
    @Column(name = "time_interval", nullable = false)
    public Byte getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Byte timeInterval) {
        this.timeInterval = timeInterval;
    }

    @Basic
    @Column(name = "aff_no", nullable = false, length = 20)
    public String getAffNo() {
        return affNo;
    }

    public void setAffNo(String affNo) {
        this.affNo = affNo;
    }

    @Basic
    @Column(name = "aff_status", nullable = false)
    public Byte getAffStatus() {
        return affStatus;
    }

    public void setAffStatus(Byte affStatus) {
        this.affStatus = affStatus;
    }

    @Basic
    @Column(name = "source_type", nullable = false)
    public Byte getSourceType() {
        return sourceType;
    }

    public void setSourceType(Byte sourceType) {
        this.sourceType = sourceType;
    }

    @Basic
    @Column(name = "source_id", nullable = false)
    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    @Basic
    @Column(name = "importance", nullable = false)
    public Byte getImportance() {
        return importance;
    }

    public void setImportance(Byte importance) {
        this.importance = importance;
    }

    @Basic
    @Column(name = "exe_times", nullable = false)
    public Byte getExeTimes() {
        return exeTimes;
    }

    public void setExeTimes(Byte exeTimes) {
        this.exeTimes = exeTimes;
    }

    @Basic
    @Column(name = "delay_times", nullable = false)
    public Byte getDelayTimes() {
        return delayTimes;
    }

    public void setDelayTimes(Byte delayTimes) {
        this.delayTimes = delayTimes;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GtdAffairs that = (GtdAffairs) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (planWeek != null ? !planWeek.equals(that.planWeek) : that.planWeek != null) return false;
        if (doDt != null ? !doDt.equals(that.doDt) : that.doDt != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (site != null ? !site.equals(that.site) : that.site != null) return false;
        if (timeInterval != null ? !timeInterval.equals(that.timeInterval) : that.timeInterval != null) return false;
        if (affNo != null ? !affNo.equals(that.affNo) : that.affNo != null) return false;
        if (affStatus != null ? !affStatus.equals(that.affStatus) : that.affStatus != null) return false;
        if (sourceType != null ? !sourceType.equals(that.sourceType) : that.sourceType != null) return false;
        if (sourceId != null ? !sourceId.equals(that.sourceId) : that.sourceId != null) return false;
        if (importance != null ? !importance.equals(that.importance) : that.importance != null) return false;
        if (exeTimes != null ? !exeTimes.equals(that.exeTimes) : that.exeTimes != null) return false;
        if (delayTimes != null ? !delayTimes.equals(that.delayTimes) : that.delayTimes != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (createdDt != null ? !createdDt.equals(that.createdDt) : that.createdDt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (planWeek != null ? planWeek.hashCode() : 0);
        result = 31 * result + (doDt != null ? doDt.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (site != null ? site.hashCode() : 0);
        result = 31 * result + (timeInterval != null ? timeInterval.hashCode() : 0);
        result = 31 * result + (affNo != null ? affNo.hashCode() : 0);
        result = 31 * result + (affStatus != null ? affStatus.hashCode() : 0);
        result = 31 * result + (sourceType != null ? sourceType.hashCode() : 0);
        result = 31 * result + (sourceId != null ? sourceId.hashCode() : 0);
        result = 31 * result + (importance != null ? importance.hashCode() : 0);
        result = 31 * result + (exeTimes != null ? exeTimes.hashCode() : 0);
        result = 31 * result + (delayTimes != null ? delayTimes.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (createdDt != null ? createdDt.hashCode() : 0);
        return result;
    }
}
