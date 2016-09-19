package com.lvtulife.gtd.component.entity;

import com.lvtulife.base.utils.DateUtil;
import org.springframework.format.datetime.joda.LocalDateParser;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 计算星期的工具类
 */
public class WeekUtil {

    public static void main(String[] args) {
        int year = 2016;
        int week = 23;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Calendar c = new GregorianCalendar();
        c.setTime(today);

        System.out.println("当前时间:current date = " + sdf.format(today));
        System.out.println("当前第几周:getWeekOfYear = " + getWeekOfYear(today));
        System.out.println("本月第几周:getWeekOfMonth = " + getWeekOfMonth(today));
        System.out.println("年度总周数:getMaxWeekNumOfYear = " + getMaxWeekNumOfYear(year));
        System.out.println("本周第一天:getFirstDayOfWeek = " + sdf.format(getFirstDayOfWeek(year, week)));
        System.out.println("本周最后一天:getLastDayOfWeek = " + sdf.format(getLastDayOfWeek(year, week)));
        System.out.println("本周第一天:getFirstDayOfWeek = " + sdf.format(getFirstDayOfWeek(today)));
        System.out.println("本周最后一天:getLastDayOfWeek = " + sdf.format(getLastDayOfWeek(today)));
        getWeeksByYear(year);
        //getWeekIntervalByYear(year);
        getWeeksByYear(2018);
    }

    // 获取当前时间所在年的周数
    public static int getWeekOfYear(Date date) {
        return getWeekValue(date, Calendar.WEEK_OF_YEAR);
    }

    // 获取指定日期为所在月的第几周
    public static int getWeekOfMonth(Date date) {
        return getWeekValue(date, Calendar.WEEK_OF_MONTH);
    }

    private static int getWeekValue(Date date, int type) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(type);
    }

    // 获取当前时间所在年的最大周数
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekOfYear(c.getTime());
    }

    // 获取某年的第几周的开始日期
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    // 获取某年的第几周的结束日期
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    // 获取当前时间所在周的开始日期
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    // 获取当前时间所在周的结束日期
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }


    /**
     * 返回指定年度的所有周。List中包含的是String[2]对象<br>
     * string[0]本周的开始日期,string[1]是本周的结束日期。<br>
     * 日期的格式为yyyy-MM-dd。<br>
     * 每年的第一个周，必须包含星期一且是完整的七天。<br>
     * 例如：2009年的第一个周开始日期为2009-01-05，结束日期为2009-01-11。 <br>
     * 星期一在哪一年，那么包含这个星期的周就是哪一年的周。<br>
     * 例如：2008-12-29是星期一，2009-01-04是星期日，哪么这个周就是2008年度的最后一个周。<br>
     *
     * @param year 格式 yyyy ，必须大于1900年度 小于9999年
     * @return
     */
    public static List<String[]> getWeeksByYear(final int year) {
        if (year < 1900 || year > 9999) {
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
        }


        //实现思路，首先计算当年有多少个周，然后找到每个周的开始日期和结束日期

        // Calendar calendar = new GregorianCalendar();
        // // 在具有默认语言环境的默认时区内使用当前时间构造一个默认的 GregorianCalendar。
        // calendar.setFirstDayOfWeek(Calendar.MONDAY); //设置每周的第一天为星期一
        // calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //每周从周一开始
        // 上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。
        // calendar.setMinimalDaysInFirstWeek(7); //设置每周最少为7天
        // calendar.set(Calendar.YEAR, year); // 设置年度为指定的年

        // //首先计算当年有多少个周,每年都至少有52个周，个别年度有53个周

        int weeks = getWeekNumByYear(year);
        // System.out.println(year+"共有"+weeks+"个周");
        List<String[]> result = new ArrayList<String[]>(weeks);
        for (int i = 1; i <= weeks; i++) {
            String[] tempWeek = new String[2];
            tempWeek[0] = getYearWeekFirstDay(year, i);
            tempWeek[1] = getYearWeekEndDay(year, i);
            //或者使用下面的代码，不过发现效率更低
            // tempWeek[0] = getDateAdd(firstWeekDay,(i-1)*7+0);
            // tempWeek[1] = getDateAdd(firstWeekDay,(i-1)*7+6);
            result.add(tempWeek);
            System.out.println(i + "=" + tempWeek[0] + "_" + tempWeek[1]);
        }
        return result;
    }

    public static List<WeekInterval> getWeekIntervalByYear(final int year) {
        if (year < 1900 || year > 9999) {
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
        }
        //实现思路，首先计算当年有多少个周，然后找到每个周的开始日期和结束日期
        int weeks = getWeekNumByYear(year);
        // System.out.println(year+"共有"+weeks+"个周");
        List<WeekInterval> result = new ArrayList<WeekInterval>(weeks);
        for (int i = 1; i <= weeks; i++) {
            WeekInterval week = new WeekInterval(i, LocalDate.parse(getYearWeekFirstDay(year, i)), LocalDate.parse(getYearWeekEndDay(year, i)));
            //或者使用下面的代码，不过发现效率更低
            // tempWeek[0] = getDateAdd(firstWeekDay,(i-1)*7+0);
            // tempWeek[1] = getDateAdd(firstWeekDay,(i-1)*7+6);
            result.add(week);
            System.out.println(week.toString());
        }
        return result;
    }


    /**
     * 计算指定年度共有多少个周。
     *
     * @param year 格式 yyyy ，必须大于1900年度 小于9999年
     * @return
     */
    public static int getWeekNumByYear(final int year) {
        if (year < 1900 || year > 9999) {
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
        }
        int result = 52;//每年至少有52个周 ，最多有53个周。
        String date = getYearWeekFirstDay(year, 53);
        if (date.substring(0, 4).equals(year + "")) { //判断年度是否相符，如果相符说明有53个周。
            result = 53;
        }
        return result;
    }

    /**
     * 计算某年某周的开始日期
     *
     * @param yearNum 格式 yyyy ，必须大于1900年度 小于9999年
     * @param weekNum 1到52或者53
     * @return 日期，格式为yyyy-MM-dd
     */
    public static String getYearWeekFirstDay(int yearNum, int weekNum) {
        if (yearNum < 1900 || yearNum > 9999) {
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
        }
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); //设置每周的第一天为星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//每周从周一开始
        // 上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。
        cal.setMinimalDaysInFirstWeek(7); //设置每周最少为7天
        cal.set(Calendar.YEAR, yearNum);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);
        //分别取得当前日期的年、月、日
        return DateUtil.formatDate(cal.getTime());
    }

    /**
     * 计算某年某周的结束日期
     *
     * @param yearNum 格式 yyyy ，必须大于1900年度 小于9999年
     * @param weekNum 1到52或者53
     * @return 日期，格式为yyyy-MM-dd
     */
    public static String getYearWeekEndDay(int yearNum, int weekNum) {
        if (yearNum < 1900 || yearNum > 9999) {
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
        }
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); //设置每周的第一天为星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//每周从周一开始
        // 上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。
        cal.setMinimalDaysInFirstWeek(7); //设置每周最少为7天
        cal.set(Calendar.YEAR, yearNum);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);

        return DateUtil.formatDate(cal.getTime());
    }
}
