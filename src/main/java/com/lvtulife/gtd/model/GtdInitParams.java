package com.lvtulife.gtd.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by lvtulife on 2016-03-24 .
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "gtd_init_params")
public class GtdInitParams {
    private Integer id;
    private Integer initYear;
    private Byte nowStage;
    private Integer nowYear;
    private Byte nowQuarter;
    private Byte nowMonth;
    private Byte nowWeekMonth;
    private Byte nowWeekYear;
    private Integer nowDay;
    private String customStage;
    private String customTimeInterval;
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
    @Column(name = "init_year", nullable = false)
    public Integer getInitYear() {
        return initYear;
    }

    public void setInitYear(Integer initYear) {
        this.initYear = initYear;
    }

    @Basic
    @Column(name = "now_stage", nullable = false)
    public Byte getNowStage() {
        return nowStage;
    }

    public void setNowStage(Byte nowStage) {
        this.nowStage = nowStage;
    }

    @Basic
    @Column(name = "now_year", nullable = false)
    public Integer getNowYear() {
        return nowYear;
    }

    public void setNowYear(Integer nowYear) {
        this.nowYear = nowYear;
    }

    @Basic
    @Column(name = "now_quarter", nullable = false)
    public Byte getNowQuarter() {
        return nowQuarter;
    }

    public void setNowQuarter(Byte nowQuarter) {
        this.nowQuarter = nowQuarter;
    }

    @Basic
    @Column(name = "now_month", nullable = false)
    public Byte getNowMonth() {
        return nowMonth;
    }

    public void setNowMonth(Byte nowMonth) {
        this.nowMonth = nowMonth;
    }

    @Basic
    @Column(name = "now_week_month", nullable = false)
    public Byte getNowWeekMonth() {
        return nowWeekMonth;
    }

    public void setNowWeekMonth(Byte nowWeekMonth) {
        this.nowWeekMonth = nowWeekMonth;
    }

    @Basic
    @Column(name = "now_week_year", nullable = false)
    public Byte getNowWeekYear() {
        return nowWeekYear;
    }

    public void setNowWeekYear(Byte nowWeekYear) {
        this.nowWeekYear = nowWeekYear;
    }

    @Basic
    @Column(name = "now_day", nullable = false)
    public Integer getNowDay() {
        return nowDay;
    }

    public void setNowDay(Integer nowDay) {
        this.nowDay = nowDay;
    }

    @Basic
    @Column(name = "custom_stage", nullable = true, length = 255)
    public String getCustomStage() {
        return customStage;
    }

    public void setCustomStage(String customStage) {
        this.customStage = customStage;
    }

    @Basic
    @Column(name = "custom_time_interval", nullable = true, length = 255)
    public String getCustomTimeInterval() {
        return customTimeInterval;
    }

    public void setCustomTimeInterval(String customTimeInterval) {
        this.customTimeInterval = customTimeInterval;
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

        GtdInitParams that = (GtdInitParams) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (initYear != null ? !initYear.equals(that.initYear) : that.initYear != null) return false;
        if (nowStage != null ? !nowStage.equals(that.nowStage) : that.nowStage != null) return false;
        if (nowYear != null ? !nowYear.equals(that.nowYear) : that.nowYear != null) return false;
        if (nowQuarter != null ? !nowQuarter.equals(that.nowQuarter) : that.nowQuarter != null) return false;
        if (nowMonth != null ? !nowMonth.equals(that.nowMonth) : that.nowMonth != null) return false;
        if (nowWeekMonth != null ? !nowWeekMonth.equals(that.nowWeekMonth) : that.nowWeekMonth != null) return false;
        if (nowWeekYear != null ? !nowWeekYear.equals(that.nowWeekYear) : that.nowWeekYear != null) return false;
        if (nowDay != null ? !nowDay.equals(that.nowDay) : that.nowDay != null) return false;
        if (customStage != null ? !customStage.equals(that.customStage) : that.customStage != null) return false;
        if (customTimeInterval != null ? !customTimeInterval.equals(that.customTimeInterval) : that.customTimeInterval != null)
            return false;
        if (del != null ? !del.equals(that.del) : that.del != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (initYear != null ? initYear.hashCode() : 0);
        result = 31 * result + (nowStage != null ? nowStage.hashCode() : 0);
        result = 31 * result + (nowYear != null ? nowYear.hashCode() : 0);
        result = 31 * result + (nowQuarter != null ? nowQuarter.hashCode() : 0);
        result = 31 * result + (nowMonth != null ? nowMonth.hashCode() : 0);
        result = 31 * result + (nowWeekMonth != null ? nowWeekMonth.hashCode() : 0);
        result = 31 * result + (nowWeekYear != null ? nowWeekYear.hashCode() : 0);
        result = 31 * result + (nowDay != null ? nowDay.hashCode() : 0);
        result = 31 * result + (customStage != null ? customStage.hashCode() : 0);
        result = 31 * result + (customTimeInterval != null ? customTimeInterval.hashCode() : 0);
        result = 31 * result + (del != null ? del.hashCode() : 0);
        return result;
    }
}
