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
@Table(name = "gtd_life_plan_sum")
public class GtdLifePlanSum {
    private Integer id;
    private Integer pid;
    private Byte stage;
    private String oldContent;
    private String sumContent;
    private Integer sumYear;
    private Date sumDt;
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
    @Column(name = "pid", nullable = false)
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "stage", nullable = false)
    public Byte getStage() {
        return stage;
    }

    public void setStage(Byte stage) {
        this.stage = stage;
    }

    @Basic
    @Column(name = "old_content", nullable = true, length = -1)
    public String getOldContent() {
        return oldContent;
    }

    public void setOldContent(String oldContent) {
        this.oldContent = oldContent;
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
    @Column(name = "sum_year", nullable = false)
    public Integer getSumYear() {
        return sumYear;
    }

    public void setSumYear(Integer sumYear) {
        this.sumYear = sumYear;
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

        GtdLifePlanSum that = (GtdLifePlanSum) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
        if (stage != null ? !stage.equals(that.stage) : that.stage != null) return false;
        if (oldContent != null ? !oldContent.equals(that.oldContent) : that.oldContent != null) return false;
        if (sumContent != null ? !sumContent.equals(that.sumContent) : that.sumContent != null) return false;
        if (sumYear != null ? !sumYear.equals(that.sumYear) : that.sumYear != null) return false;
        if (sumDt != null ? !sumDt.equals(that.sumDt) : that.sumDt != null) return false;
        if (del != null ? !del.equals(that.del) : that.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (stage != null ? stage.hashCode() : 0);
        result = 31 * result + (oldContent != null ? oldContent.hashCode() : 0);
        result = 31 * result + (sumContent != null ? sumContent.hashCode() : 0);
        result = 31 * result + (sumYear != null ? sumYear.hashCode() : 0);
        result = 31 * result + (sumDt != null ? sumDt.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
