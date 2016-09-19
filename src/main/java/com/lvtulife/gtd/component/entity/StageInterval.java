package com.lvtulife.gtd.component.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 阶段
 * total:四个阶段
 * max:第四阶段
 * min:第一阶段
 * default:第一阶段
 * Created by lvtulife on 2016-06-10 .
 */
public class StageInterval implements Serializable {
    // 阶段值
    private int stage;
    // 阶段开始时间
    private Date startTime;
    // 阶段结束时间
    private Date endTime;

    private final static String[] cnNumber = new String[]{"零","一","二","三","四"};

    public StageInterval() {
    }

    public StageInterval(int stage, Date startTime, Date endTime) {
        this.stage = stage;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * 获取阶段名称
     * @return
     */
    public String getStageName() {
        stage = stage>10?4:stage;
        stage = stage<=0?1:stage;
        return "第"+cnNumber[stage]+"阶段";
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
