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
@Table(name = "gtd_ideal_adjust")
public class GtdIdealAdjust {
    private Integer id;
    private Integer lid;
    private String ideal;
    private String detail;
    private String cause;
    private String giCode;
    private Date hopeDt;
    private Date sumDt;
    private String sumContent;
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
    @Column(name = "lid", nullable = false)
    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
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
    @Column(name = "sum_dt", nullable = false)
    public Date getSumDt() {
        return sumDt;
    }

    public void setSumDt(Date sumDt) {
        this.sumDt = sumDt;
    }

    @Basic
    @Column(name = "sum_content", nullable = false, length = 255)
    public String getSumContent() {
        return sumContent;
    }

    public void setSumContent(String sumContent) {
        this.sumContent = sumContent;
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

        GtdIdealAdjust that = (GtdIdealAdjust) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lid != null ? !lid.equals(that.lid) : that.lid != null) return false;
        if (ideal != null ? !ideal.equals(that.ideal) : that.ideal != null) return false;
        if (detail != null ? !detail.equals(that.detail) : that.detail != null) return false;
        if (cause != null ? !cause.equals(that.cause) : that.cause != null) return false;
        if (giCode != null ? !giCode.equals(that.giCode) : that.giCode != null) return false;
        if (hopeDt != null ? !hopeDt.equals(that.hopeDt) : that.hopeDt != null) return false;
        if (sumDt != null ? !sumDt.equals(that.sumDt) : that.sumDt != null) return false;
        if (sumContent != null ? !sumContent.equals(that.sumContent) : that.sumContent != null) return false;
        if (del != null ? !del.equals(that.del) : that.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lid != null ? lid.hashCode() : 0);
        result = 31 * result + (ideal != null ? ideal.hashCode() : 0);
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        result = 31 * result + (cause != null ? cause.hashCode() : 0);
        result = 31 * result + (giCode != null ? giCode.hashCode() : 0);
        result = 31 * result + (hopeDt != null ? hopeDt.hashCode() : 0);
        result = 31 * result + (sumDt != null ? sumDt.hashCode() : 0);
        result = 31 * result + (sumContent != null ? sumContent.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
