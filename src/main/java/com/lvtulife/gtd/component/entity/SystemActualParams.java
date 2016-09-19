package com.lvtulife.gtd.component.entity;

import com.lvtulife.base.utils.DateUtil;
import com.lvtulife.base.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 系统的实时状态
 * 所有参数值通过当前时间进行计算获得
 * Created by lvtulife on 2016-06-09 .
 */
public class SystemActualParams {
    private static final Logger logger = LoggerFactory.getLogger(SystemActualParams.class);

    private static List<TimeInterval> timeIntervals = new ArrayList<TimeInterval>();

    static {
        //初始化常用时段 0早晨；1上午；2中午；3下午；4傍晚；5晚间；6凌晨；
        timeIntervals.add(new TimeInterval(0, "早晨", LocalTime.of(6, 0), LocalTime.of(9, 0)));
        timeIntervals.add(new TimeInterval(1, "上午", LocalTime.of(9, 0), LocalTime.of(12, 0)));
        timeIntervals.add(new TimeInterval(2, "中午", LocalTime.of(12, 0), LocalTime.of(14, 0)));
        timeIntervals.add(new TimeInterval(3, "下午", LocalTime.of(14, 0), LocalTime.of(18, 0)));
        timeIntervals.add(new TimeInterval(4, "傍晚", LocalTime.of(18, 0), LocalTime.of(20, 0)));
        timeIntervals.add(new TimeInterval(5, "晚间", LocalTime.of(20, 0), LocalTime.of(0, 0)));
        timeIntervals.add(new TimeInterval(6, "凌晨", LocalTime.of(0, 0), LocalTime.of(6, 0)));
    }

    public static void main(String[] args) {
        System.out.println("获取本年度周信息集合:");
        List<WeekInterval> weeks = SystemActualParams.getWeekIntervalsOfNow();
        for (WeekInterval week : weeks) {
            System.out.println(week.toString());
        }

        System.out.println("获取指定年度周信息集合:");
        weeks = SystemActualParams.getWeekIntervals(2018);
        for (WeekInterval week : weeks) {
            System.out.println(week.toString());
        }

        System.out.println("获取当前时段:" + SystemActualParams.getTimeIntervalOfNow().toString());
        System.out.println("获取指定时间所属时段:" + SystemActualParams.getTimeInterval(LocalTime.of(00, 0)).toString());

        System.out.println("获取当前年的总天数:" + SystemActualParams.getTotalDayOfYear());
        System.out.println("获取当前年的第几天:" + SystemActualParams.getDayOfYear());
        System.out.println("获取今年的第几天CN:" + SystemActualParams.getDayCnOfNow());
        System.out.println("获取当前年的剩余天数:" + SystemActualParams.getOverplusDayOfYear());

        System.out.println("获取当前月的总天数:"+SystemActualParams.getTotalDayOfMonth());
        System.out.println("获取当前月的第几天:" + SystemActualParams.getDayOfMonth());
        System.out.println("获取当前月的剩余天数:"+SystemActualParams.getOverplusDayOfMonth());


        System.out.println("获取今年第几周:" + SystemActualParams.getWeekOfNow());
        System.out.println("获取今年的第几周CN:" + SystemActualParams.getWeekCnOfNow());
        System.out.println("获取本月第几周:" + SystemActualParams.getMonthWeekOfNow());
        System.out.println("获取本月的第几周CN:" + SystemActualParams.getMonthWeekCnOfNow());
        //System.out.println(":"+SystemActualParams.());

        System.out.println("今年是:"+SystemActualParams.getYear());
        System.out.println("今月是:"+SystemActualParams.getMonth());
        System.out.println("今日是:"+SystemActualParams.getDay());

    }


    public static int getYear() {
        return LocalDate.now().getYear();
    }

    public static int getMonth() {
        return LocalDate.now().getMonthValue();
    }

    public static int getDay() {
        return LocalDate.now().getDayOfMonth();
    }

    /**
     * 获取当前月的总天数
     *
     * @return
     */
    public static int getTotalDayOfMonth() {
        return DateUtil.getMaxDaysOfMonth(getYear(),getMonth());
    }

    /**
     * 获取当前年的总天数
     *
     * @return
     */
    public static int getTotalDayOfYear() {
        return DateUtil.getMaxDaysOfYear(getYear());
    }

    /**
     * 获取当前月的剩余天数
     *
     * @return
     */
    public static int getOverplusDayOfMonth() {
        return getTotalDayOfMonth() - getDayOfMonth();
    }

    /**
     * 获取当前年的剩余天数
     *
     * @return
     */
    public static int getOverplusDayOfYear() {
        return getTotalDayOfYear() - getDayOfYear();
    }

    /**
     * 获取当前年的第几天
     *
     * @return
     */
    public static int getDayOfYear() {
        return LocalDate.now().getDayOfYear();
    }

    /**
     * 获取当前月的第几天
     *
     * @return
     */
    public static int getDayOfMonth() {
        return getDay();
    }

    /**
     * 获取当前年的第几天
     *
     * @return
     */
    public static String getDayCnOfNow() {
        return StringUtil.formateString("第{0}天", String.valueOf(getDayOfYear()));
    }

    /**
     * 获取当前年第几周
     *
     * @return
     */
    public static int getWeekOfNow() {
        return WeekUtil.getWeekOfYear(new Date());
    }

    /**
     * 获取当前年的第几周，返回中文第几周
     *
     * @return
     */
    public static String getWeekCnOfNow() {
        return StringUtil.formateString("第{0}周", String.valueOf(getWeekOfNow()));
    }

    /**
     * 获取当前月第几周
     *
     * @return
     */
    public static int getMonthWeekOfNow() {
        return WeekUtil.getWeekOfMonth(new Date());
    }

    /**
     * 获取当前月的第几周，返回中文第几月第几周
     *
     * @return
     */
    public static String getMonthWeekCnOfNow() {
        return StringUtil.formateString("第{0}月 第{1}周", String.valueOf(getMonth()), String.valueOf(getMonthWeekOfNow()));
    }


    //当前季度信息
    private int nowQuarterSummary;



    /**
     * 获取本年度周信息集合
     *
     * @return
     */
    public static List<WeekInterval> getWeekIntervalsOfNow() {
        return WeekUtil.getWeekIntervalByYear(getYear());
    }

    /**
     * 获取指定年度周信息集合
     *
     * @return
     */
    public static List<WeekInterval> getWeekIntervals(Integer year) {
        return WeekUtil.getWeekIntervalByYear(year);
    }


    /**
     * 获取常用时段信息集合
     *
     * @return
     */
    public static List<TimeInterval> getTimeIntervals() {
        return timeIntervals;
    }

    /**
     * 获取当前时段信息
     *
     * @return
     */
    public static TimeInterval getTimeIntervalOfNow() {
        return getTimeInterval(LocalTime.now());
    }

    /**
     * 获取指定时间所属时段
     *
     * @param localTime
     * @return
     */
    public static TimeInterval getTimeInterval(LocalTime localTime) {
        List<TimeInterval> times = getTimeIntervals();
        for (TimeInterval ti : times) {
            if (ti != null) {
                if (localTime.equals(ti.getStartTime()) || (localTime.isAfter(ti.getStartTime()) && localTime.isBefore(ti.getEndTime()))) {
                    return ti;
                }
            }
        }
        return null;
    }
}
