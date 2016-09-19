package com.lvtulife.gtd.component.entity;

import java.util.List;

/**
 * 用户的实时状态
 * 用户当前当前的阶段、年度、季度等信息
 * Created by lvtulife on 2016-06-09 .
 */
public class UserActualParams {

    /**
     * 实时参数
     */

    //用户设置的起始年份
    private int startYear;

    //用户设置的阶段信息
    private List<StageInterval> stageSettings;

    //用户设置的常用时段
    private List<TimeInterval> timeIntervalSettings;

    //用户当前的天数信息
    private int nowDay;

    //用户当前的周期信息
    private int nowWeek;

    //用户当前的月度信息
    private int nowMonth;

    //用户当前的季度信息
    private int nowQuarterSummary;

    //用户当前的年度信息
    private int nowYear;

    //用户当前的阶段信息
    private StageInterval nowStage;

    /**
     * 业务判断
     */

    //用户是否执行了每日总结
    private boolean isDaySummary;

    //用户是否执行了每周总结
    private boolean isWeekSummary;

    //用户是否执行了月度总结
    private boolean isMonthSummary;

    //用户是否执行了季度总结
    private boolean isQuarterSummary;

    //用户是否执行了年度总结
    private boolean isYearSummary;


}
