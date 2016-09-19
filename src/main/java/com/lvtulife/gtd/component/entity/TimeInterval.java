package com.lvtulife.gtd.component.entity;

import com.lvtulife.base.utils.StringUtil;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

/**
 * 时段
 * Created by lvtulife on 2016-06-10 .
 */
public class TimeInterval implements Serializable {
    // 时段值
    private int value;
    // 时段名称
    private String timeName;
    // 时段开始时间
    private LocalTime startTime;
    // 时段结束时间
    private LocalTime endTime;

    public TimeInterval() {
    }

    public TimeInterval(int value, String timeName, LocalTime startTime, LocalTime endTime) {
        this.value = value;
        this.timeName = timeName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTimeName() {
        return timeName;
    }

    public void setTimeName(String timeName) {
        this.timeName = timeName;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return StringUtil.formateString("时段:{0}\t时段值:{1}\t时段开始:{2}\t时段结束:{3}", getTimeName(), String.valueOf(getValue()), getStartTime().toString(), getEndTime().toString());
    }
}
