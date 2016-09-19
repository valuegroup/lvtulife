package com.lvtulife.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期工具类
 *
 * @author 孙宇
 */
public class DateUtil {

    private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    //日期常量
    static enum DateConstants {
        TODAY(0), NEARLYWEEK(1), MONTH(2), NEARLYMONTH(3);
        public int value;

        DateConstants(int value) {
            this.value = value;
        }
    }

    /**
     * 显示日期的格式,yyyy-MM-dd
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 显示日期的格式,yyyy-MM-dd
     */
    public static final String DATE_HOUR_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 显示日期的格式,yyyy-MM
     */
    public static final String DATE_YEAE_MONTH = "yyyy-MM";

    /**
     * 显示日期的格式,yyyy-MM-dd HH:mm:ss
     */
    public static final String TIMEF_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 显示日期的格式,yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String FULL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 显示日期的格式,yyyy年MM月dd日HH时mm分ss秒
     */
    public static final String ZHCN_TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";
    /**
     * 显示日期的格式,yyyy年MM月dd日HH时mm分
     */
    public static final String ZHCN_TIME_FORMAT1 = "yyyy年MM月dd日HH时mm分";

    /**
     * 显示日期的格式,yyyyMMddHHmmss
     */
    public static final String TIME_STR_FORMAT = "yyyyMMddHHmmss";
    /**
     * 显示日期的格式,yyyyMMddHHmmssSSS
     */
    public static final String TIMESSS_STR_FORMAT = "yyyyMMddHHmmssSSS";
    /**
     * 显示日期的格式,yyyyMMdd
     */
    public static final String DATE_STR_FORMAT = "yyyyMMdd";

    /**
     * 显示日期的格式,yyMMdd
     */
    public static final String DATE_SIMPLE_SHORT_FORMAT = "yyMMdd";

    /**
     * DateFormat,格式:yyyy-MM-dd
     */
    private static DateFormat dateFormat;

    /**
     * DateFormat,格式:yyyy-MM-dd HH:mm:ss
     */
    private static DateFormat dateTimeFormat;

    /**
     * DateFormat,格式:yyyyMMddHHmmss
     */
    private static DateFormat dateTimeStrFormat;

    /**
     * DateFormat,格式:yyyy年MM月dd日HH时mm分ss秒
     */
    private static DateFormat zhcnDateTimeStrFormat;

    static {
        dateFormat = SimpleDateFormatFactory.getInstance(DATE_FORMAT);
        dateTimeFormat = SimpleDateFormatFactory.getInstance(TIMEF_FORMAT);
        dateTimeStrFormat = SimpleDateFormatFactory.getInstance(TIME_STR_FORMAT);
        zhcnDateTimeStrFormat = SimpleDateFormatFactory.getInstance(ZHCN_TIME_FORMAT);
    }

    /**
     * 获取当前时间在＋－n分钟后的字符串时间
     *
     * @param n int
     * @return String
     */
    public static String getTimebyMinAfter(int n) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.add(Calendar.MINUTE, n);
        return (dateTimeFormat.format(now.getTime()));
    }

    /**
     * 获取当前时间在＋－n秒后的字符串时间
     *
     * @param n int
     * @return String
     */
    public static String getTimebySecAfter(int n) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.add(Calendar.SECOND, n);
        return (dateTimeFormat.format(now.getTime()));
    }

    /**
     * 获取当前时间在＋－n分钟后的时间(java.util.Date)
     *
     * @param n int
     * @return String
     */
    public static Date getTimebyMinAfterDate(int n) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.add(Calendar.MINUTE, n);
        return now.getTime();
    }

    /**
     * 获取定义的DateFormat格式
     *
     * @param formatStr
     * @return
     */
    private static DateFormat getDateFormat(String formatStr) {
        if (formatStr.equalsIgnoreCase(DATE_FORMAT)) {
            return dateFormat;
        } else if (formatStr.equalsIgnoreCase(TIMEF_FORMAT)) {
            return dateTimeFormat;
        } else {
            return SimpleDateFormatFactory.getInstance(formatStr);
        }
    }

    /**
     * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
     *
     * @param date 日期
     * @return String 字符串
     */
    public static String dateToDateString(Date date) {
        return dateToDateString(date, TIMEF_FORMAT);
    }

    /**
     * 将Date转换成formatStr格式的字符串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToDateString(Date date, String formatStr) {
        DateFormat df = getDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 将Date转换成yyyy-MM-dd的字符串
     *
     * @param date
     * @return
     */
    public static String getDateString(Date date) {
        DateFormat df = getDateFormat(DATE_FORMAT);
        return df.format(date);
    }

    /**
     * 将小时数换算成返回以毫秒为单位的时间
     *
     * @param hours
     * @return
     */
    public static long getMicroSec(BigDecimal hours) {
        BigDecimal bd;
        bd = hours.multiply(new BigDecimal(3600 * 1000));
        return bd.longValue();
    }

    /**
     * 获取今天的日期，格式自定
     *
     * @param pattern -
     *                设定显示格式
     * @return String - 返回今天的日期
     */
    public static String getToday(String pattern) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        DateFormat sdf = getDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

/*    public static String getToday(String fm) {
        SimpleDateFormat formatter = new SimpleDateFormat(fm); // 规定日期格式
        Date date = new Date(); // 将符合格式的String转换为Date
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }*/

    // 得到系统当前的分钟数,如当前时间是上午12:00,系统当前的分钟数就是12*60
    public static int getCurrentMinutes() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        int iMin = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE);
        return iMin;
    }

    /**
     * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
     *
     * @return 当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
     */
    public static String getCurZhCNDateTime() {
        return dateToDateString(new Date(), ZHCN_TIME_FORMAT);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * @return 得到本月月份 如09
     */
    public static String getMonth() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH) + 1;
        String monStr = String.valueOf(month);

        // 对于10月份以下的月份,加"0"在前面

        if (month < 10)
            monStr = "0" + monStr;
        return monStr;
    }

    /**
     * @return 得到本月第几天
     */
    public static String getDay() {
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        String dayStr = String.valueOf(day);

        // 对于10月份以下的月份,加"0"在前面

        if (day < 10)
            dayStr = "0" + dayStr;
        return dayStr;
    }

    /**
     * @return 获取指定日期月份 如09
     */
    public static String getMonth(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int month = now.get(Calendar.MONTH) + 1;
        String monStr = String.valueOf(month);
        // 对于10月份以下的月份,加"0"在前面
        if (month < 10)
            monStr = "0" + monStr;
        return monStr;
    }

    /**
     * @return 获取指定日期天数
     */
    public static String getDay(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int day = now.get(Calendar.DAY_OF_MONTH);
        String dayStr = String.valueOf(day);
        // 对于10月份以下的月份,加"0"在前面
        if (day < 10)
            dayStr = "0" + dayStr;
        return dayStr;
    }

    /**
     * 根据失效时间判断是否依然有效
     *
     * @param expireTime 失效时间的字符串形式,格式要求:yyMMddHHmmss
     * @return true:失效 false:没有失效
     * @throws ParseException
     */
    public static boolean isTimeExpired(String expireTime) throws ParseException {
        boolean ret = false;

        // SimpleDateFormat不是线程安全的,所以每次调用new一个新的对象

        Date date = SimpleDateFormatFactory.getInstance(TIME_STR_FORMAT).parse(expireTime);
        Calendar expire = Calendar.getInstance();
        expire.setTime(date);
        Calendar now = Calendar.getInstance();
        // 将毫秒字段设为0,保持精度一致性

        now.set(Calendar.MILLISECOND, 0);
        if (now.after(expire)) {
            ret = true;
        }
        return ret;
    }

    /**
     * 根据开始时间和可用时间计算出失效时间
     *
     * @param startTime     开始时间
     * @param availableTime 有效时长（单位：秒）
     * @return 失效时间
     * @throws ParseException
     */
    public static String getExpireTimeByCalculate(Date startTime, int availableTime) {

        Calendar expire = Calendar.getInstance();

        // 设置为开始时间

        expire.setTime(startTime);

        // 开始时间向后有效时长秒得到失效时间
        expire.add(Calendar.SECOND, availableTime);

        // 返回失效时间字符串

        // SimpleDateFormat不是线程安全的,所以每次调用new一个新的对象

        return SimpleDateFormatFactory.getInstance(TIME_STR_FORMAT).format(expire.getTime());

    }

    /**
     * 将Date转换为yyyyMMddHHmmss的形式
     *
     * @param date
     * @return 日期的字符串形式, 格式：yyyyMMddHHmmss
     */
    public static String convertDateToString(Date date) {
        return SimpleDateFormatFactory.getInstance(TIME_STR_FORMAT).format(date);
    }

    /**
     * 将Date转换为yyMMddHHmmss的形式
     *
     * @param date
     * @return 日期的字符串形式, 格式：yyMMddHHmmss
     */
    public static String convertDateToString(Date date, String format) {
        return SimpleDateFormatFactory.getInstance(format).format(date);
    }

    /**
     * 将yyMMddHHmmss形式的字符串转换成Date的形式
     *
     * @param date yyMMddHHmmss形式的日期字符串
     * @return Date对象
     * @throws ParseException
     */
    public static Date convertStringToDate(String date) throws ParseException {
        return SimpleDateFormatFactory.getInstance(TIME_STR_FORMAT).parse(date);
    }

    /**
     * 字符串转化为日期
     *
     * @param date         日期字符串
     * @param formatString 格式化字符串
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String date, String formatString) throws ParseException {
        return SimpleDateFormatFactory.getInstance(formatString).parse(date);
    }

    /**
     * 日期转化为格式化日期
     *
     * @param date         日期
     * @param formatString 格式化字符串
     * @return
     * @throws ParseException
     */
    public static Date convertDateToDate(Date date, String formatString) throws ParseException {
        return SimpleDateFormatFactory.getInstance(formatString).parse(convertDateToString(date, formatString));
    }

    /**
     * 字符串转化为格式化字符串格式
     *
     * @param date         日期
     * @param formatString 格式化字符串
     * @return
     * @throws ParseException
     */
    public static String convertStringToString(String date, String formatString) throws ParseException {
        return SimpleDateFormatFactory.getInstance(formatString).format(date);
    }

    /**
     * 将yy-MM-dd形式的字符串转换成Date的形式
     *
     * @param date yy-MM-dd形式的日期字符串
     * @return Date对象
     * @throws ParseException
     */
    public static Date convertSimpleStringToDate(String date) throws ParseException {
        return SimpleDateFormatFactory.getInstance(DATE_FORMAT).parse(date);
    }

    /**
     * @param date yyyyMMddHHmmss格式的日期字符转换为yyyy年MM月dd日HH时mm分ss秒格式的字符串
     * @return yyyy年MM月dd日HH时mm分ss秒格式的日期字符串
     * @throws ParseException
     */
    public static String convertStringToZhCN(String date) throws ParseException {
        return zhcnDateTimeStrFormat.format(dateTimeStrFormat.parse(date));
    }

    /**
     * 时间字符串转换成日期时间的形式
     *
     * @param date yy-MM-dd HH:mm:ss形式的日期字符串
     * @return Date对象
     * @throws ParseException
     */
    public static Date convertSimpleStringToDateTime(String date) throws ParseException {
        return SimpleDateFormatFactory.getInstance(TIMEF_FORMAT).parse(date);
    }

    /**
     * 获取当天日期
     */
    public static Date getTodayDate() {
        // 获取昨日的日期
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        return today;
    }

    /**
     * 获取昨日日期 返回格式：yyyy-MM-dd
     */
    public static String getYesterdayDateStr() {
        // 获取昨日的日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = cal.getTime();
        return SimpleDateFormatFactory.getInstance(DATE_FORMAT).format(yesterday);
    }

    /**
     * 获取昨日日期 返回格式：yyyy-MM-dd
     */
    public static Date getYesterdayDate() {
        // 获取昨日的日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = cal.getTime();
        return yesterday;
    }

    /**
     * 获取明天日期 返回格式：yyyy-MM-dd
     */
    public static String getTomorrowDateStr() {
        // 获取昨日的日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = cal.getTime();
        return SimpleDateFormatFactory.getInstance(DATE_FORMAT).format(tomorrow);
    }

    /**
     * 获取明天日期 返回格式：yyyy-MM-dd
     */
    public static Date getTomorrowDate() {
        // 获取昨日的日期

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = cal.getTime();
        return tomorrow;
    }

    /**
     * 根据Calendar对象、字符串日期类型获得字符串日期
     *
     * @param calendar    Calendar对象
     * @param strDateType 字符串日期类型（1：XXXX年XX月XX日，2：XX月XX日，3，XXXX年，4：XXXX-XX-XX，5：XX-XX，6：XXXX）
     * @return
     */
    public static String getStrDate(Calendar calendar, int strDateType) {
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = (calendar.get(Calendar.MONTH) + 1) < 10 ? "0" + (calendar.get(Calendar.MONTH) + 1) : String
                .valueOf

                        ((calendar.get(Calendar.MONTH) + 1));
        String day = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : String
                .valueOf

                        (calendar.get(Calendar.DAY_OF_MONTH));
        String strDate = "";

        switch (strDateType) {
            case 1:
                strDate = year + "年" + month + "月" + day + "日";
                break;
            case 2:
                strDate = month + "月" + day + "日";
                break;
            case 3:
                strDate = year + "年";
                break;
            case 4:
                strDate = year + "-" + month + "-" + day;
                break;
            case 5:
                strDate = month + "-" + day;
                break;
            case 6:
                strDate = year;
                break;
            default:
                strDate = year + "-" + month + "-" + day;
                break;
        }

        return strDate;
    }

    /**
     * 根据原来的时间（Date）获得相对偏移 N 月的时间（Date）
     *
     * @param protoDate                原来的时间（java.util.Date）
     * @param monthOffset（向前移正数，向后移负数）
     * @return 时间（java.util.Date）
     */
    public static Date getOffsetMonthDate(Date protoDate, int monthOffset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(protoDate);
        //		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - monthOffset);错误写法
        cal.add(Calendar.MONTH, -monthOffset);
        return cal.getTime();
    }

    /**
     * 根据原来的时间（Date）获得相对偏移 N 天的时间（Date）
     *
     * @param protoDate               原来的时间（java.util.Date）
     * @param dateOffset（向前移正数，向后移负数）
     * @return 时间（java.util.Date）
     */
    public static Date getOffsetDayDate(Date protoDate, int dateOffset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(protoDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - dateOffset);
        return cal.getTime();
    }

    /**
     * 根据原来的时间（Date）获得相对偏移 N 小时的时间（Date）
     *
     * @param protoDate  原来的时间（java.util.Date）
     * @param offsetHour （向前移正数，向后移负数）
     * @return 时间（java.util.Date）
     */
    public static Date getOffsetHourDate(Date protoDate, int offsetHour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(protoDate);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - offsetHour);
        return cal.getTime();
    }

    /**
     * 获取指定月份和日子的日期(未做日期效验)
     *
     * @param currentDate 当前日期
     * @param assignYear  指定年份,-1代表年不做修改
     * @param assignMonth 指定月份,从0开始,超过月最大值自动往后加一个月年,-1代表月不做修改
     * @param assignDay   指定日,从1开始,超过日最大值往后加一个月,-1代表日不做修改
     * @return 修改后的日期
     */
    public static Date getAssignDate(Date currentDate, int assignYear, int assignMonth, int assignDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        if (assignYear > -1) {
            cal.set(Calendar.YEAR, assignYear);
        }
        if (assignMonth > -1) {
            cal.set(Calendar.MONTH, assignMonth);
        }
        if (assignDay > -1) {
            cal.set(Calendar.DAY_OF_MONTH, assignDay);
        }
        return cal.getTime();
    }

    /**
     * 获得两个日前之间相差的天数,有时分秒的影响
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 天数
     */
    public static int getDayCountBetweenDate(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        int i = 0;
        while (endCalendar.compareTo(startCalendar) >= 0) {
            startCalendar.set(Calendar.DAY_OF_MONTH, startCalendar.get(Calendar.DAY_OF_MONTH) + 1);
            i++;
        }
        return i;
    }

    /**
     * 获得两个日前之间相差的月份
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 月数
     */
    public static int getMonthCountBetweenDate(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        int i = 0;
        while (endCalendar.compareTo(startCalendar) >= 0) {
            startCalendar.set(Calendar.MONTH, startCalendar.get(Calendar.MONTH) + 1);
            i++;
        }
        return i;
    }

    /**
     * 获得两个日前之间相差的年
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 年
     */
    public static int getYearlyCountBetweenDate(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        int i = 0;
        while (endCalendar.compareTo(startCalendar) >= 0) {
            startCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR + 1));
            i++;
        }
        return i;
    }

    /**
     * 根据原来的时间（Date）获得相对偏移 N 天的时间（Date）
     *
     * @param protoDate               原来的时间（java.util.Date）
     * @param dateOffset（向前移正数，向后移负数）
     * @param type                    指定不同的格式（1：年月日，2：年月日时，3：年月日时分）
     * @return 时间（java.util.Date），没有时分秒
     */
    public static Date getOffsetSimpleDate(Date protoDate, int dateOffset, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(protoDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - dateOffset);
        if (type == 1) {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
        }
        if (type == 2) {
            cal.set(Calendar.MINUTE, 0);
        }
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 时间转为化为字符串
     * <p>
     * 格式为：yyyyMMddHHmmssSSS
     *
     * @return
     */
    public static String getDateToString() {
        SimpleDateFormat dateFormat = SimpleDateFormatFactory.getInstance(TIMESSS_STR_FORMAT);
        Date date = new Date();
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * 时间转为化为字符串
     * <p>
     * 格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getTodayTimeString() {
        SimpleDateFormat dateFormat = SimpleDateFormatFactory.getInstance(TIMEF_FORMAT);
        Date date = new Date();
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * 增加一天
     *
     * @param s
     * @param n
     * @return
     */
    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = SimpleDateFormatFactory.getInstance(DateUtil.DATE_FORMAT);
            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, n);// 增加一天
            return sdf.format(cd.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 增加一天
     *
     * @param s
     * @param n
     * @return
     */
    public static Date addDay(Date s, int n) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(s);
        cd.add(Calendar.DATE, n);// 增加一天
        return cd.getTime();
    }

    /**
     * 增加一个月
     *
     * @param m
     * @param n
     * @return
     */
    public static Date addMonth(Date m, int n) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(m);
        cd.add(Calendar.MONTH, n);// 增加一个月
        return cd.getTime();
    }

    /**
     * 增加一个月
     *
     * @param m
     * @param n
     * @param formatstring
     * @return
     */
    public static String addMonth(Date m, int n, String formatstring) {
        try {
            SimpleDateFormat sdf = SimpleDateFormatFactory.getInstance(formatstring);
            Calendar cd = Calendar.getInstance();
            cd.setTime(m);
            cd.add(Calendar.MONTH, n);//增加一个月
            return sdf.format(cd.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取需要执行的统计的日期数组
     *
     * @return 格式['2011-01-01',2011-01-02']
     */
    public static String[] getExecDay(Date lastDate) {
        String[] day = null;
        // 获取昨天日期
        Date yesdate = null;
        try {
            yesdate = DateUtil.convertDateToDate(DateUtil.getYesterdayDate(), DateUtil.DATE_FORMAT);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 获取上次最后执行日期与昨天相隔天数
        int dayCount = DateUtil.getDayCountBetweenDate(lastDate, yesdate);
        if (dayCount <= 0) {
            return day;
        }
        if (dayCount == 1) {
            return new String[]{DateUtil.getYesterdayDateStr()};
        } else {
            day = new String[dayCount];
            for (int i = 0; i < dayCount; i++) {
                String date = SimpleDateFormatFactory.getInstance(DateUtil.DATE_HOUR_FORMAT).format(DateUtil.addDay(lastDate, i));
                day[i] = date;
            }
        }
        return day;
    }

    /**
     * 获取需要执行的统计的年-月数组
     *
     * @return 格式['2011-01',2011-01']
     */
    public static String[] getExecYearMonth(Date lastYearMonth) {
        String[] yearMonth = null;
        // 获取上个月日期
        Date lastMonth = DateUtil.getOffsetMonthDate(new Date(), 1);
        try {
            lastMonth = DateUtil.convertDateToDate(lastMonth, DateUtil.DATE_YEAE_MONTH);
            //			System.out.println(lastMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 获取上次最后执行日期与昨天相隔天数
        int monthCount = DateUtil.getMonthCountBetweenDate(lastYearMonth, lastMonth);
        if (monthCount <= 0) {
            return yearMonth;
        }
        if (monthCount == 1) {
            return yearMonth = new String[]{DateUtil.convertDateToString(lastMonth, DateUtil.DATE_YEAE_MONTH)};
        } else {
            yearMonth = new String[monthCount];
            for (int i = 0; i < monthCount; i++) {
                String date = DateUtil.addMonth(lastYearMonth, i, DateUtil.DATE_YEAE_MONTH);
                yearMonth[i] = date;
            }
        }
        return yearMonth;
    }

    /**
     * 获取这个月第一天
     *
     * @return
     */
    public static Date getFirstDayOfMonth() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = ca.getTime();
        ca.add(Calendar.MONTH, 1);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        return firstDate;
    }

    /**
     * 获这个月的最后一天
     *
     * @return
     */
    public static Date getLastDayOfMonth() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.add(Calendar.MONTH, 1);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = ca.getTime();
        return lastDate;
    }

    /**
     * 获取一天的最后时间，常用于时间段查询的结束时间的处理
     *
     * @param d
     * @return 得到的时间为在d的23:59:59
     */
    public static Date getLastTimeOfDay(Date d) {
        if (null == d) {
            d = DateUtil.getCurrentDate();
        }
        String dateStr = SimpleDateFormatFactory.getInstance(DATE_FORMAT).format(d);
        try {
            d = DateUtil.convertStringToDate(dateStr, DATE_FORMAT);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        ca.add(Calendar.DAY_OF_MONTH, 1);
        ca.add(Calendar.MILLISECOND, -1);
        d = ca.getTime();
        return d;
    }

    /**
     * 获查询日期区间
     * 今天(0), 近一周(1), 本月(2),近一月(3) ;
     *
     * @return Date[0] 开始时间  Date[1] 结束时间
     */
    public static Date[] getDateSection(int dateType) {
        Date[] dateSection = new Date[2];
        if (DateConstants.TODAY.value == dateType) {
            dateSection[0] = getTodayDate();
            dateSection[1] = dateSection[0];
        } else if (DateConstants.NEARLYWEEK.value == dateType) {
            dateSection[0] = getOffsetDayDate(getTodayDate(), 7);
            dateSection[1] = getTodayDate();
        } else if (DateConstants.NEARLYMONTH.value == dateType) {
            dateSection[0] = getOffsetMonthDate(getTodayDate(), 1);
            dateSection[1] = getTodayDate();
        } else if (DateConstants.MONTH.value == dateType) {
            dateSection[0] = getFirstDayOfMonth();
            dateSection[1] = getLastDayOfMonth();
        } else {
            dateSection = null;
        }
        return dateSection;
    }

    ;

    /**
     * JDK 1.8 新特性 LocalDateTime   start
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static String toDateTime(LocalDateTime date) {
        return toDateTime(date, DEFAULT_DATE_TIME_FORMAT);
    }

    public static String toDateTime(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.SIMPLIFIED_CHINESE));
    }


    public static String toDateText(LocalDate date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(pattern, Locale.SIMPLIFIED_CHINESE));
    }

    /**
     * JDK 1.8 新特性 end
     */


    private static Calendar c = Calendar.getInstance();

    /**
     * 日期转字符串
     *
     * @param date    日期
     * @param pattern 格式
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
        return "";
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * startDate 起始时间，必须是DEFAULT_DATE_TIME_FORMAT这样的格式
     *
     * @return
     */
    public static String getBeginTime() {
        String startDate = DateUtil.getToday();
        int y = Integer.parseInt(startDate.substring(0, 4));
        int m = Integer.parseInt(startDate.substring(5, 7));

        if (m == 1) {
            m = 12;
            y--;
        } else {
            m--;
        }

        String mString = String.valueOf(m);

        if (m < 10) {
            mString = "0" + m;
        }
        String beginTime = String.valueOf(y) + "-" + mString + "-26 00:00:00";
        return beginTime;

    }

    /**
     * startDate 起始时间，必须是DEFAULT_DATE_TIME_FORMAT这样的格式
     *
     * @return
     */
    public static String getEndTime() {
        String startDate = DateUtil.getToday();
        int y = Integer.parseInt(startDate.substring(0, 4));
        int m = Integer.parseInt(startDate.substring(5, 7));
        int d = Integer.parseInt(startDate.substring(8, 10));
        String endTime = startDate.substring(0, 8) + "25 23:59:59";
        return endTime;

    }

    /**
     * 起始和当前时间之间随机产生一个时间，如果给定的起始点小的话，则以默认起始时间开始
     *
     * @param startDate
     * @return
     */
    public static String getRandomDateAfterAndBeforeCurrentDate(String startDate) {
        String latestDate = "2009-01-01 00:00:00";
        if (startDate != null && DateUtil.isDateBefore(startDate, latestDate) == false) {
            latestDate = startDate;
        }
        Date d1 = formatStringToDate(latestDate);
        Date d2 = new Date();
        long time1 = d1.getTime();
        long time2 = d2.getTime();
        long dif = Math.abs(time1 - time2);
        long rnd = Math.round(Math.random() * dif);
        long randomTime = Math.min(time1, time2) + rnd;
        Date randomDate = new Date(randomTime);
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT); // 规定日期格式
        String today = formatter.format(randomDate); // 将Date转换为符合格式的String
        return today;
    }

    /**
     * 从date1 和date2的区间里面随机取一个时间，并返回
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String getRandomDateByDate1AndDate2(String date1, String date2) {
        Date d1 = formatStringToDate(date1);
        Date d2 = formatStringToDate(date2);
        long time1 = d1.getTime();
        long time2 = d2.getTime();
        long dif = Math.abs(time1 - time2);
        long rnd = Math.round(Math.random() * dif);
        long randomTime = Math.min(time1, time2) + rnd;
        Date randomDate = new Date(randomTime);
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT); // 规定日期格式
        String today = formatter.format(randomDate); // 将Date转换为符合格式的String
        return today;
    }

    public static Date formatCnDateToDate(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 按照yyyy年MM月dd日 HH:mm:ss 格式转换成时间类型
     *
     * @param strDate
     * @return
     */
    public static Date formatCnDateToDate1(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 按照ch格式转换成时间类型
     *
     * @param strDate
     * @param format
     * @return
     */
    public static Date formatStringToDateByFormat(String strDate, String format) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    public static Date formatCnDateToDate2(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    public static Date formatNumDateToDate(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    public static Date formatDateToDate(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        Date date = null;
        try {
            if (strDate.trim().length() < 12) {
                SimpleDateFormat formatter1 = new SimpleDateFormat(DATE_FORMAT);
                strDate = formatter1.format(formatter1.parse(strDate)) + " 00:00:00";
            }
            date = formatter.parse(strDate);
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    public static Date formatDateToDate1(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Date date = null;
        try {
            if (strDate.trim().length() < 12) {
                strDate = formatDateToDate(strDate) + " 00:00:00";
            }
            date = formatter.parse(strDate);
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    public static Date formatStringDateToDate(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    public static Date formatStringToDate(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    public static Date formatStringToDate_DayMinZero(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = formatter.parse(strDate.substring(0, 10) + " 00:00:00");
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    public static Date formatStringToDate_DayMin(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = formatter.parse(strDate.substring(0, 10) + " 00:00:01");
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    public static Date formatStringToDate_DayMax(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = formatter.parse(strDate.substring(0, 10) + " 23:59:59");
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 把yyyy-M-dd H:mm:ss格式类型转换为date型
     *
     * @param strDate
     * @return
     */
    public static Date formatStringToDate2(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd H:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 把yyyy-MM-dd HH:mm格式类型转换为date型
     *
     * @param strDate
     * @return
     */
    public static Date formatStringToDate3(String strDate) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_HOUR_FORMAT);
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    public static Date _formatStringToDate(String strDate) throws ParseException {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        return formatter.parse(strDate);
    }

    public static Date formatStringToDate(String strDate, String format) {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            logger.error("Format string to date error!");
            e.printStackTrace();
        }
        return date;
    }

    public static String formatStringDateToString(String date, String format) {
        if (null == date)
            return null;
        Date newDate = formatStringToDate(date, format);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String strDate = formatter.format(newDate);
        return strDate;
    }

    public static String formatDateToString(Date date) {
        if (null == date)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        String strDate = formatter.format(date);
        return strDate;
    }

    public static String formatDateToString1(Date date) {
        if (null == date)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String strDate = formatter.format(date);
        return strDate;
    }

    public static Date formatStringToDate(Date date) throws ParseException {
        if (null == date)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String strDate = formatter.format(date);
        return formatter.parse(strDate);
    }

    public static Date formatStringToDate1(String date) throws ParseException {
        if (null == date)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        String strDate = formatter.format(date);
        return formatter.parse(strDate);
    }

    public static Date formatStringToDateYMD(String date) throws ParseException {
        if (null == date)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        logger.info("转换====" + date);
        String strDate = formatter.format(date);
        return formatter.parse(strDate);
    }

    /**
     * 得到从currDate往前数hours小时的日期,注意不能计算超过20*24小时的日期， 若要计算若干天之前的日期，则用后面的getDateBeforeDays()方法。
     *
     * @param currDate
     * @param hours
     * @return
     * @author 裴伟斌 2006-11-9
     */
    public static Date getDateBeforeHours(Date currDate, int hours) {
        if (null == currDate)
            return null;
        long longCurr = currDate.getTime();
        long longPre = longCurr - hours * 3600000;
        Date preDate = new Date(longPre);
        return preDate;
    }

    public static String getDateBeforeHours(String currDate, int hours) {
        if (null == currDate)
            return null;
        Date dateCurr = formatStringToDate(currDate);
        Date datePre = getDateBeforeHours(dateCurr, hours);
        return formatDateToString(datePre);
    }

    /**
     * 得到从currDate往前数days天的日期。
     *
     * @param currDate
     * @param days
     * @return
     * @author 裴伟斌 2006-11-15
     */
    public static Date getDateBeforeDays(Date currDate, int days) {
        if (null == currDate)
            return null;
        Calendar working = (Calendar) c.clone();
        working.setTime(currDate);
        working.add(Calendar.DAY_OF_MONTH, -days);
        Date newDate = working.getTime();
        return newDate;
    }

    public static String getDateBeforeDays(String currDate, int days) {
        if (null == currDate)
            return null;
        Date dateCurr = formatStringToDate(currDate);
        Date datePre = getDateBeforeDays(dateCurr, days);
        return formatDateToString(datePre);
    }

    public static String getDateBeforeDaysStr(Date currDate, int days) {
        if (null == currDate)
            return null;
        Calendar working = (Calendar) c.clone();
        working.setTime(currDate);
        working.add(Calendar.DAY_OF_MONTH, -days);
        Date newDate = working.getTime();
        return formatDateToString1(newDate);
    }

    /**
     * 根据当前日期currDate取每周的第几天的日期，n为Calendar类的静态变量，从Calendar.SUNDAY到Calendar. SATURDAY。 比如传入的currDate=2007-01-24 09:43:26，是周三，给出的day为Calendar.SUNDAY，那么， 返回的日期则为2007-01-21 09:43:26。
     *
     * @param currDate
     * @param day
     * @return
     * @author 裴伟斌 2007-1-24
     */
    public static Date getDateOfWeek(Date currDate, int day) {
        if (null == currDate || day < 1 || day > 7)
            return null;
        Calendar working = (Calendar) c.clone();
        working.setTime(currDate);
        working.set(Calendar.DAY_OF_WEEK, day);
        Date newDate = working.getTime();
        return newDate;
    }

    // 判断2个时间差 是否是 分钟
    public static boolean isDateExceededMinutes(Date date1, Date date2, int minutes) {
        if (null == date1 || null == date2 || minutes <= 0)
            return false;
        long longDate1 = date1.getTime();
        long longDate2 = date2.getTime();
        long threshold = minutes * 60 * 1000;
        if ((longDate1 - longDate2) > threshold)
            return true;
        else
            return false;
    }

    // 求2个时间差 返回3位小数
    public static String distanceMinutes(Date date1, Date date2) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.000");
        double longDate1 = date1.getTime();
        double longDate2 = date2.getTime();
        double resultLong = (longDate1 - longDate2) / 1000 / 60;
        return df.format(resultLong);
    }

    public static String getDateOfWeek(String currDate, int day) {
        if (null == currDate || day < 1 || day > 7)
            return null;
        Date dateCurr = formatStringToDate(currDate);
        Date dateTarget = getDateOfWeek(dateCurr, day);
        return formatDateToString(dateTarget);
    }

    /**
     * 根据当前日期currDate得到往前数weeks周数之后的日期，比如输入currDate=2007-01-24 09:43:26,weeks=2, 则返回日期为2007-01-10 09:43:26。
     *
     * @param currDate
     * @param weeks
     * @return
     * @author 裴伟斌 2007-1-24
     */
    public static Date getDateBeforeWeeks(Date currDate, int weeks) {
        if (null == currDate)
            return null;
        Calendar working = (Calendar) c.clone();
        working.setTime(currDate);
        working.add(Calendar.WEEK_OF_YEAR, -weeks);
        Date newDate = working.getTime();
        return newDate;
    }

    public static String getDateBeforeWeeks(String currDate, int weeks) {
        if (null == currDate)
            return null;
        Date dateCurr = formatStringToDate(currDate);
        Date dateTarget = getDateBeforeWeeks(dateCurr, weeks);
        return formatDateToString(dateTarget);
    }

    /**
     * 根据当前日期currDate得到往前数weeks周数之后的日期，然后再根据day，取到这一周星期几的日期， 比如输入currDate=2007-01-24 09:43:26,weeks=2,day=Calendar.SUNDAY，则先得到两周之前的日期2007-01-10 09:43:26， 然后再得到周日的日期2007-01-07 09:43:26。
     *
     * @param currDate
     * @param weeks
     * @param day
     * @return
     * @author 裴伟斌 2007-1-24
     */
    public static Date getDateBeforeWeeksDay(Date currDate, int weeks, int day) {
        if (null == currDate || day < 1 || day > 7)
            return null;
        Calendar working = (Calendar) c.clone();
        working.setTime(currDate);
        working.add(Calendar.WEEK_OF_YEAR, -weeks);
        working.set(Calendar.DAY_OF_WEEK, day);
        Date newDate = working.getTime();
        return newDate;
    }

    /**
     * 获取当前日期是星期几
     *
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String getDateBeforeWeeksDay(String currDate, int weeks, int day) {
        if (null == currDate || day < 1 || day > 7)
            return null;
        Date dateCurr = formatStringToDate(currDate);
        Date dateTarget = getDateBeforeWeeksDay(dateCurr, weeks, day);
        return formatDateToString(dateTarget);
    }

    /**
     * 得到给定日期date当月起始的那一时刻，比如给定date=2006-03-14 15:29:20, 则返回2006-03-01 00:00:00.
     *
     * @param date
     * @return
     * @author 裴伟斌 2006-11-16
     */
    public static Date getBeginTimeOfTheMonth(Date date) {
        if (null == date)
            return null;
        Calendar working = (Calendar) c.clone();
        working.setTime(date);
        working.set(Calendar.DAY_OF_MONTH, 1);
        working.set(Calendar.HOUR_OF_DAY, 0);
        working.set(Calendar.MINUTE, 0);
        working.set(Calendar.SECOND, 0);
        Date newDate = working.getTime();
        return newDate;
    }

    public static String getBeginTimeOfTheMonth(String date) {
        if (null == date || "".equals(date))
            return null;
        Date dateCurr = formatStringToDate(date);
        Date dateNew = getBeginTimeOfTheMonth(dateCurr);
        return formatDateToString(dateNew);
    }

    /**
     * 获得传入的日期的当前月的最后一天
     *
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date getLastDayOfMonth(Date date) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(date);
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        lastDate.setDate(lastDay);
        return lastDate;
    }

    @SuppressWarnings("deprecation")
    public static Date getLastDayOfMonth(String strDate) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(formatStringToDate(strDate));
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        lastDate.setDate(lastDay);
        return lastDate;
    }

    @SuppressWarnings("deprecation")
    public static String getLastDayOfMonthStr(Date date) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(date);
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        lastDate.setDate(lastDay);
        return formatDateToString(lastDate);
    }

    @SuppressWarnings("deprecation")
    public static String getLastDayOfMonthStr(String strDate) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(formatStringToDate(strDate));
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        lastDate.setDate(lastDay);
        return formatDateToString(lastDate);
    }

    public static String getFirstDayOfMonthStr(Date date) {
        SimpleDateFormat datef = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 当前月的第一天
        cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
        Date beginTime = cal.getTime();

        return datef.format(beginTime) + " 00:00:00";
    }

    /**
     * 判断两个日期之间的距离是否超过了指定的小时数hours
     *
     * @param date1
     * @param date2
     * @param hours
     * @return
     */
    public static boolean isDateExceeded(Date date1, Date date2, int hours) {
        if (null == date1 || null == date2 || hours <= 0)
            return false;
        long longDate1 = date1.getTime();
        long longDate2 = date2.getTime();
        long threshold = hours * 3600000;
        if (Math.abs(longDate1 - longDate2) > threshold)
            return true;
        else
            return false;
    }

    /**
     * 判断两个日期之间的距离是否超过了指定的分钟minutes
     *
     * @param date1
     * @param date2
     * @param minutes
     * @return
     */
    public static boolean isDateExceededForMinutes(Date date1, Date date2, int minutes) {
        if (null == date1 || null == date2 || minutes <= 0)
            return false;
        long longDate1 = date1.getTime();
        long longDate2 = date2.getTime();
        long threshold = minutes * 60000;
        if (Math.abs(longDate1 - longDate2) > threshold)
            return true;
        else
            return false;
    }

    /**
     * 获取传过来的时间和当前时间的时间差，兵返回相差多少分钟
     *
     * @param date2
     * @param minutes
     * @return
     */
    public static String getMinutesBetwenNow(Date date2, int minutes) {
        Date date1 = getToday0();
        if (null == date1 || null == date2 || minutes <= 0)
            return "";
        long longDate1 = date1.getTime();
        long longDate2 = date2.getTime();
        long threshold = minutes * 60000;
        if (Math.abs(longDate1 - longDate2) > threshold)
            return "";
        else
            return String.valueOf(Math.abs(longDate1 - longDate2) / 60000);
    }

    public static boolean isDateExceeded(String strDate1, String strDate2, int hours) {
        if (null == strDate1 || null == strDate1 || hours <= 0)
            return false;
        Date date1 = formatStringToDate(strDate1);
        Date date2 = formatStringToDate(strDate2);
        return isDateExceeded(date1, date2, hours);
    }

    public static boolean isDateExceeded(long time1, long time2, int hours) {
        long threshold = hours * 3600000;
        if (Math.abs(time1 - time2) > threshold)
            return true;
        else
            return false;
    }

    /**
     * month 要得到的几个月的个数
     *
     * @param date
     * @param month
     * @return
     */
    public static Date getDateAfterMonth(Date date, int month) {
        if (date == null || null == date)
            return null;
        else {
            logger.info(date.getMonth() + "===" + month);
            date.setMonth(date.getMonth() + month);
            return date;
        }
    }

    public static Date getDateAfterMonth(String date, int month) {
        if (date == null || null == date)
            return null;
        else {
            Date returnDate = formatStringToDate(date);
            returnDate.setMonth(returnDate.getMonth() + month);
            return returnDate;
        }
    }

    /**
     * 找出日期数组返回几个月前或后的时间
     *
     * @param date
     * @param month
     * @param pn
     * @return
     */
    public static String getDateMonth(String date, int month, String pn) {
        if (date == null || null == date)
            return null;
        else {
            if (pn == null) {
                Date returnDate = parseDate(date);
                returnDate.setMonth(returnDate.getMonth() + month);
                SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT); // 规定日期格式
                String today = formatter.format(returnDate); // 将Date转换为符合格式的String
                return today;
            } else {
                Date returnDate = parseDate(date);
                returnDate.setMonth(returnDate.getMonth() - month);
                SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT); // 规定日期格式
                String today = formatter.format(returnDate); // 将Date转换为符合格式的String
                return today;
            }

        }
    }

    public static long parseLong(String str) {
        long ret = -1;
        try {
            ret = Long.parseLong(str);
        } catch (Exception e) {
            ret = -1;
        }
        return ret;
    }

    private static DateFormat df = new SimpleDateFormat(DATE_FORMAT);

    public static Date parseDate(String str) {
        Date ret = null;
        try {
            ret = df.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
        }
        return ret;
    }

    public static String formatDate(Date date) {
        String ret = "";
        try {
            ret = df.format(date);
        } catch (Exception e) {
            // e.printStackTrace();
            ret = "";
        }
        return ret;
    }

    public static String nowDate() {
        return df.format(new Date());
    }

    /**
     * 找出日期数组dates中最老的一个日期。
     *
     * @param dates
     * @return
     * @author 裴伟斌 2006-11-16
     */
    public static String findOldestDate(String[] dates) {
        if (null == dates || dates.length < 1)
            return null;
        int len = dates.length;
        String oldestDate = dates[0];
        for (int i = 0; i < len; i++)
            for (int j = i + 1; j < len; j++)
                if (dates[j].compareTo(dates[i]) < 0)
                    oldestDate = dates[j];
        return oldestDate;
    }

    /**
     * 取得两个日期时间之间的时间差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String getDiffDate(Date date1, Date date2) {
        if (null == date1 || null == date2)
            return "无法比较";
        long longDate1 = date1.getTime();// 单位毫秒
        long longDate2 = date2.getTime();// 单位毫秒
        long diff = Math.abs(longDate1 - longDate2) / 1000;// 单位为秒
        long day = diff / 86400;
        long hour = (diff - day * 86400) / 3600;
        long minute = (diff - day * 86400 - hour * 3600) / 60;
        long second = (diff - day * 86400 - hour * 3600) - minute * 60;
        return String.valueOf(day) + "天" + String.valueOf(hour) + "时" + String.valueOf(minute) + "分" + String.valueOf(second) + "秒";
    }

    /**
     * 判断时间date1是否在时间date2之前
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isDateBefore(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).before(df.parse(date2));
        } catch (ParseException e) {
            logger.info("[SYS] " + e.getMessage());
            return false;
        }
    }

    /**
     * 判断时间date1是否在时间date2之前
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isDateBefore(Date date1, Date date2) {
        return date1.before(date2);
    }

    /**
     * 按YYYY-MM-DD HH:MM:SS格式返回今天的日期.
     *
     * @return
     */
    public static String getToday() {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT); // 规定日期格式
        Date date = new Date(); // 将符合格式的String转换为Date
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }

    public static String getToday4() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d"); // 规定日期格式
        Date date = new Date(); // 将符合格式的String转换为Date
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }

    public static String getToday5() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT); // 规定日期格式
        Date date = new Date(); // 将符合格式的String转换为Date
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }

    public static Date getToday0() {
        return formatStringToDate(getToday());
    }

    /**
     * 按YYYY-MM-DD HH:MM:SS格式返回昨天的日期.
     *
     * @return
     */
    public static String getYesterday() {
        long nowTime = System.currentTimeMillis();
        long yesterday = nowTime - 86400000;// 减一天
        Date date = new Date(yesterday);
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT); // 规定日期格式
        String today = formatter.format(date); // 将Date转换为符合格式的String
        return today;
    }

    public static String getYesterday1() {
        long nowTime = System.currentTimeMillis();
        long yesterday = nowTime - 86400000;// 减一天
        Date date = new Date(yesterday);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d"); // 规定日期格式
        String today = formatter.format(date); // 将Date转换为符合格式的String
        return today;
    }

    /**
     * 按YYYY-MM-DD HH:MM:SS格式返回前天的日期.
     *
     * @return
     */
    public static String getTheDayBeforeYesterday() {
        long nowTime = System.currentTimeMillis();
        long yesterday = nowTime - 86400000 * 2;// 减一天
        Date date = new Date(yesterday);
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT); // 规定日期格式
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }

    /**
     * 按yyyy-MM-dd格式返回今天的日期.
     *
     * @return
     */
    public static String getToday1() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT); // 规定日期格式
        Date date = new Date(); // 将符合格式的String转换为Date
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }


    public static String getTomorrow(String fm) {
        long nowTime = System.currentTimeMillis();
        long yesterday = nowTime + 86400000;// 减一天
        Date date = new Date(yesterday);
        SimpleDateFormat formatter = new SimpleDateFormat(fm); // 规定日期格式
        String today = formatter.format(date); // 将Date转换为符合格式的String
        return today;
    }

    /**
     * 按yyyy年MM月dd日HH时mm分ss秒格式返回今天的日期.
     *
     * @return
     */
    public static String getToday2() {
        SimpleDateFormat formatter = new SimpleDateFormat(ZHCN_TIME_FORMAT); // 规定日期格式
        Date date = new Date(); // 将符合格式的String转换为Date
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }

    /**
     * 按yyyyMMddHHmmss格式返回今天的日期.
     *
     * @return
     */
    public static String getToday3() {
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_STR_FORMAT); // 规定日期格式
        Date date = new Date(); // 将符合格式的String转换为Date
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }

    /**
     * 返回今天的年份.
     *
     * @return
     */
    public static String getYear() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy"); // 规定日期格式
        Date date = new Date(); // 将符合格式的String转换为Date
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }

    /**
     * 返回今天的月份.
     *
     * @return
     */
    public static String getCurrentMonth() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM"); // 规定日期格式
        Date date = new Date(); // 将符合格式的String转换为Date
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }

    /**
     * 返回今天的日子数.
     *
     * @return
     */
    public static String getCurrentDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd"); // 规定日期格式
        Date date = new Date(); // 将符合格式的String转换为Date
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }

    /**
     * 返回今天的当前的小时
     *
     * @return
     */
    public static String getHours() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH"); // 规定日期格式
        Date date = new Date(); // 将符合格式的String转换为Date
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }

    /**
     * 返回今天的当前的分
     *
     * @return
     */
    public static String getMin() {
        SimpleDateFormat formatter = new SimpleDateFormat("mm"); // 规定日期格式
        Date date = new Date(); // 将符合格式的String转换为Date
        String today = formatter.format(date); // 将Date转换为符合格式的String
        // logger.info(today);
        return today;
    }

    /**
     * 自定义格式化时间
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String formatDate(Date date, String formatStr) {
        if (date == null || CommonUtils.nullOrBlank(formatStr))
            return "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatStr); // 规定日期格式
            String today = formatter.format(date); // 将Date转换为符合格式的String
            return today;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 根据一个日期，返回是次日起的前几天后几天
     *
     * @param sdate 日期字符串 ；i l 可以为负值
     * @return
     */
    public static String DayAdd(String sdate, int l) {
        Calendar calendar = Calendar.getInstance();
        // Date date=new Date("2009-06-21");
        calendar.setTime(StringToDate(sdate));
        calendar.add(Calendar.DATE, l);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(calendar.getTime());
    }

    public static String DayAdd(String sdate, int l, String fm) {
        Calendar calendar = Calendar.getInstance();
        // Date date=new Date("2009-06-21");
        calendar.setTime(StringToDate(sdate));
        calendar.add(Calendar.DATE, l);
        SimpleDateFormat formatter = new SimpleDateFormat(fm);
        return formatter.format(calendar.getTime());
    }

    public static String MonthAdd(String sdate, int l) {
        Calendar calendar = Calendar.getInstance();
        // Date date=new Date("2009-06-21");
        calendar.setTime(StringToDate(sdate));
        calendar.add(Calendar.MONTH, l);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(calendar.getTime());
    }

    /**
     * 根据一个日期字符串，返回Date类型
     *
     * @param std - 日期字符串 返回Date类型固定
     * @return
     */
    public static Date StringToDate(String std) {
        SimpleDateFormat formatter1 = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        try {
            // logger.info("1");
            if (std.trim().length() < 12) {
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                std = formatter.format(formatter.parse(std)) + " 00:00:00";
            }
            return formatter1.parse(std);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static java.sql.Date StringDateToSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.sql.Date StringStringToSqlDate(String strdate) {
        logger.info("传值===" + strdate);
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        try {
            return new java.sql.Date(formatter.parse(strdate).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static java.sql.Date StringStringToSqlDate1(String strdate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        try {
            if (strdate.trim().length() < 12) {
                return new java.sql.Date(formatter.parse(strdate + " 00:00:01").getTime());
            } else {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证时间格式是否符合
     *
     * @param pattern 需要匹配的时间格式
     * @param timeStr 时间值
     * @return <code>boolean</code> 返回<code>true</code>表示时间格式匹配 返回 <code>false</code>表示时间格式不匹配
     */
    public static boolean match(String pattern, String timeStr) {

        // 修改发送时间
        Pattern patternObj = Pattern.compile(pattern);
        Matcher matcher = patternObj.matcher(timeStr);
        boolean result = matcher.matches();
        return result;

    }

    public static Date _formatDateToDate(String strDate) throws ParseException {
        if (null == strDate)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        Date date = null;
        try {
            logger.info(strDate);
            if (strDate.trim().length() < 12) {
                SimpleDateFormat formatter1 = new SimpleDateFormat(DATE_FORMAT);
                strDate = formatter1.format(formatter1.parse(strDate)) + " 00:00:00";
            }
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            throw new ParseException(e.getMessage(), e.getErrorOffset());
        }
        return date;
    }

    /**
     * 接受YYYY-MM-DD的日期字符串参数,返回两个日期相差的天数
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static long getDistDates(String start, String end) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(start);
            endDate = sdf.parse(end);
        } catch (ParseException e) {
            throw new ParseException(e.getMessage(), e.getErrorOffset());
        }
        return getDistDates(startDate, endDate);
    }

    /**
     * 返回两个日期相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static long getDistDates(Date startDate, Date endDate) {
        long totalDate = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timestart = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeend = calendar.getTimeInMillis();
        if (timeend - timestart < 0) {
            return -1;
        }
        totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
        return totalDate;
    }

    /**
     * 判断两个时间的大小
     *
     * @param date1 时间格式为：yyyy-mm-dd 00:00:00
     * @param date2 时间格式为：yyyy-mm-dd 00:00:00
     * @return date1小于date2 n = 1;//date1大于date2 n = 2;//date1等于date2 n = 3;
     */
    public static Integer timesCompare(Date date1, Date date2) {
        String da1 = formatDateToString(date1);
        String da2 = formatDateToString(date2);
        int n = 0;
        // date1小于date2
        if (formatDateToDate(da1).before(formatDateToDate(da2))) {
            n = 1;
        }
        // date1大于date2
        if (formatDateToDate(da1).after(formatDateToDate(da2))) {
            n = 2;
        }
        // date1等于date2
        if (formatDateToDate(da1).equals(formatDateToDate(da2))) {
            n = 3;
        }
        return n;
    }

    /**
     * 判断两个时间的大小
     *
     * @param date1 时间格式为：yyyy-mm-dd 00:00:00
     * @param date2 时间格式为：yyyy-mm-dd 00:00:00
     * @return date1小于date2 n = 1;//date1大于date2 n = 2;//date1等于date2 n = 3;
     */
    public static Integer timesCompare(String date1, String date2) {
        int n = 0;
        // date1小于date2
        if (formatDateToDate(date1).before(formatDateToDate(date2))) {
            n = 1;
        }
        // date1大于date2
        if (formatDateToDate(date1).after(formatDateToDate(date2))) {
            n = 2;
        }
        // date1等于date2
        if (formatDateToDate(date1).equals(formatDateToDate(date2))) {
            n = 3;
        }
        return n;
    }

    /**
     * 判断两个时间的大小（年月的判断）
     *
     * @param date1
     * @param date2
     * @return date1小于date2 n = 1;//date1大于date2 n = 2;//date1等于date2 n = 3;
     */
    public static Integer timesCompareM(Date date1, Date date2) {
        SimpleDateFormat datef = new SimpleDateFormat(DATE_YEAE_MONTH);
        String dat1 = datef.format(date1) + "-01 00:00:00";
        String dat2 = datef.format(date2) + "-01 00:00:00";
        int n = timesCompare(dat1, dat2);
        return n;
    }

    /**
     * 判断两个时间的大小（年月的判断）
     *
     * @param date1
     * @param date2
     * @return date1小于date2 n = 1;//date1大于date2 n = 2;//date1等于date2 n = 3;
     */
    public static Integer timesCompareM(String date1, String date2) {
        SimpleDateFormat datef = new SimpleDateFormat(DATE_YEAE_MONTH);
        String dat1 = datef.format(formatDateToDate(date1)) + "-01 00:00:00";
        String dat2 = datef.format(formatDateToDate(date2)) + "-01 00:00:00";
        int n = timesCompare(dat1, dat2);
        return n;
    }

    /**
     * 获取数据同步时间段 返回当前日期 如果不是周日则返回周一到周日 如果当前日期是周日返回下周一到周日的时间
     *
     * @return ;
     */
    public static int getWeekInteger(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // String weekStr = "";
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        // switch (week) {
        // case 0:
        // weekStr = "星期日";
        // break;
        // case 1:
        // weekStr = "星期一";
        // break;
        // case 2:
        // weekStr = "星期二";
        // break;
        // case 3:
        // weekStr = "星期三";
        // break;
        // case 4:
        // weekStr = "星期四";
        // break;
        // case 5:
        // weekStr = "星期五";
        // break;
        // case 6:
        // weekStr = "星期六";
        // break;
        // }
        // logger.info(date + "====" + weekStr);
        // logger.info(getMonday(date));
        // logger.info(getSunday(date));
        // logger.info(getNextSunday(date));
        return week;

    }

    /**
     * 获取数据同步时间段 返回当前日期 如果不是周日则返回周一到周日 如果当前日期是周日返回下周一到周日的时间
     *
     * @return ;
     */
    public static String[] getSHDates(Date date) {
        String[] dateMSG = new String[2];
        String dateStr = "";
        String weekStr = "";
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        Date dateBegin = new Date();
        Date dateEnd = new Date();
        DateFormat shortDateFormat = DateFormat.getDateInstance(0);
        dateStr = shortDateFormat.format(date);
        dateBegin.setTime(date.getTime() - (long) (week) * 24 * 60 * 60 * 1000);
        String dateBeginStr = shortDateFormat.format(dateBegin);
        dateEnd.setTime(date.getTime() + (long) (7 - week - 1) * 24 * 60 * 60 * 1000);
        String dateEndStr = shortDateFormat.format(dateEnd);
        logger.info("今天是当年的第" + calendar.get(Calendar.WEEK_OF_YEAR) + "周");
        logger.info("今天是当月的" + calendar.get(calendar.DAY_OF_WEEK_IN_MONTH) + "周");
        logger.info("今天是" + weekStr);
        logger.info("本周的开始时间是" + dateBeginStr);
        logger.info("本周的结束时间是" + dateEndStr);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        logger.info("本周的开始时间是" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DATE) + "日");
        calendar.set(Calendar.DAY_OF_WEEK, 7);
        logger.info("本周的开始结束时间是" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DATE) + "日");

        if (week == 0) {
            dateMSG[0] = dateBeginStr;
            dateMSG[1] = dateEndStr;
            return dateMSG;
        } else {
            String dateMonday = getMonday(date);
            // String dateSunDay = getSunday(date);
            // logger.info("星期一" + dateMonday);
            // logger.info("星期日" + dateSunDay);
            return dateMSG;
        }
        // switch(week){
        // case 0:
        // weekStr = "星期日";
        // break;
        // case 1:
        // weekStr = "星期一";
        // break;
        // case 2:
        // weekStr = "星期二";
        // break;
        // case 3:
        // weekStr = "星期三";
        // break;
        // case 4:
        // weekStr = "星期四";
        // break;
        // case 5:
        // weekStr = "星期五";
        // break;
        // case 6:
        // weekStr = "星期六";
        // break;
        // }

    }

    public static String getMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        if (week > 2) {
            cal.add(Calendar.DAY_OF_MONTH, -(week - 2));
        } else {
            cal.add(Calendar.DAY_OF_MONTH, 2 - week);
        }

        DateFormat shortDateFormat = DateFormat.getDateInstance(0);
        String dateStr = shortDateFormat.format(cal.getTime());

        return dateStr;
    }

    public static Date getSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        if (week > 1) {
            cal.add(Calendar.DAY_OF_MONTH, -(week - 1));
        } else {
            cal.add(Calendar.DAY_OF_MONTH, 1 - week);
        }
        return cal.getTime();
    }

    public static String getNextMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        if (week > 2) {
            cal.add(Calendar.DAY_OF_MONTH, -(week - 2) + 7);
        } else {
            cal.add(Calendar.DAY_OF_MONTH, 2 - week + 7);
        }

        DateFormat shortDateFormat = DateFormat.getDateInstance(0);
        String dateStr = shortDateFormat.format(cal.getTime());
        return dateStr;
    }

    public static Date getNextSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        if (week > 1) {
            cal.add(Calendar.DAY_OF_MONTH, -(week - 1) + 7);
        } else {
            cal.add(Calendar.DAY_OF_MONTH, 1 - week + 7);
        }
        return cal.getTime();
    }

    /**
     * 判断给定时间在否在给定两个时间之前
     */
    public static void do7(String star, String end) {
        SimpleDateFormat localTime = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        try {
            Date sdate = localTime.parse(star);
            Date edate = localTime.parse(end);
            long time = System.currentTimeMillis();
            System.out.println(time + "##" + sdate.getTime() + "##" + edate.getTime());
            if (time >= sdate.getTime() && time <= edate.getTime()) {
                System.out.println("true");
            }
        } catch (Exception e) {
        }
    }

    public static int getMaxDaysOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static int getMaxDaysOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, year);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
