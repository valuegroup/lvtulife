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
@Table(name = "gtd_affairs_his")
public class GtdAffairsHis {
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
    private Date finishDt;
    private Byte ass1;
    private Byte ass2;
    private Byte ass3;
    private Byte ass4;
    private Byte ass5;

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

    @Basic
    @Column(name = "finish_dt", nullable = false)
    public Date getFinishDt() {
        return finishDt;
    }

    public void setFinishDt(Date finishDt) {
        this.finishDt = finishDt;
    }

    @Basic
    @Column(name = "ass1", nullable = false)
    public Byte getAss1() {
        return ass1;
    }

    public void setAss1(Byte ass1) {
        this.ass1 = ass1;
    }

    @Basic
    @Column(name = "ass2", nullable = false)
    public Byte getAss2() {
        return ass2;
    }

    public void setAss2(Byte ass2) {
        this.ass2 = ass2;
    }

    @Basic
    @Column(name = "ass3", nullable = false)
    public Byte getAss3() {
        return ass3;
    }

    public void setAss3(Byte ass3) {
        this.ass3 = ass3;
    }

    @Basic
    @Column(name = "ass4", nullable = false)
    public Byte getAss4() {
        return ass4;
    }

    public void setAss4(Byte ass4) {
        this.ass4 = ass4;
    }

    @Basic
    @Column(name = "ass5", nullable = false)
    public Byte getAss5() {
        return ass5;
    }

    public void setAss5(Byte ass5) {
        this.ass5 = ass5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GtdAffairsHis that = (GtdAffairsHis) o;

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
        if (finishDt != null ? !finishDt.equals(that.finishDt) : that.finishDt != null) return false;
        if (ass1 != null ? !ass1.equals(that.ass1) : that.ass1 != null) return false;
        if (ass2 != null ? !ass2.equals(that.ass2) : that.ass2 != null) return false;
        if (ass3 != null ? !ass3.equals(that.ass3) : that.ass3 != null) return false;
        if (ass4 != null ? !ass4.equals(that.ass4) : that.ass4 != null) return false;
        if (ass5 != null ? !ass5.equals(that.ass5) : that.ass5 != null) return false;

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
        result = 31 * result + (finishDt != null ? finishDt.hashCode() : 0);
        result = 31 * result + (ass1 != null ? ass1.hashCode() : 0);
        result = 31 * result + (ass2 != null ? ass2.hashCode() : 0);
        result = 31 * result + (ass3 != null ? ass3.hashCode() : 0);
        result = 31 * result + (ass4 != null ? ass4.hashCode() : 0);
        result = 31 * result + (ass5 != null ? ass5.hashCode() : 0);
        return result;
    }
}
