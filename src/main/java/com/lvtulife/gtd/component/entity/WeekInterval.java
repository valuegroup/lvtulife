package com.lvtulife.gtd.component.entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 周期
 * 年度第1-~52周
 * 本月第1~4周
 * 一周的起止日期
 * Created by lvtulife on 2016-06-10 .
 */
public class WeekInterval implements Serializable {
    // 年度第几周
    private int weekValueOfYear;
    // 开始日期
    private LocalDate startDate;
    // 结束日期
    private LocalDate endDate;

    public WeekInterval() {
    }

    public WeekInterval(int weekValueOfYear, LocalDate startDate, LocalDate endDate) {
        this.weekValueOfYear = weekValueOfYear;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("年度第几周:").append(weekValueOfYear).append("\t");
        str.append("开始日期:").append(startDate).append("\t");
        str.append("结束日期:").append(endDate).append("\t");
        return str.toString();
    }
}
