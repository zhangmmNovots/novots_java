package com.weilaios.iqxceqhnhubt.utils;

import org.apache.commons.lang3.time.DateUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间转换工具类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public class TimeUtils {

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat startTimeFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    public static final SimpleDateFormat endTimeFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

    /**
     * 获取过去天数的起始
     */
    public static String getPastNDayStart(String value) {
        int Day = Integer.parseInt(value);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -Day);
        date = calendar.getTime();
        return startTimeFormat.format(date);
    }
    /**
     * 获取过去天数的结束
     */
    public static String getPastNDayEnd() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return endTimeFormat.format(date);
    }

    /**
     * 获取今天的起始
     */
    public static String getToDayStart() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        date = calendar.getTime();
        return startTimeFormat.format(date);
    }

    /**
     * 获取今天的结束
     */
    public static String getToDayEnd() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        date = calendar.getTime();
        return endTimeFormat.format(date);
    }
    /**
     * 获取未来天数的起始
     */
    public static String getFutureDayStart() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        date = calendar.getTime();
        return startTimeFormat.format(date);
    }

    /**
     * 获取未来天数的结束
     */
    public static String getFutureDayEnd(String value) {
        int Day = Integer.parseInt(value);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, +Day);
        date = calendar.getTime();
        return endTimeFormat.format(date);
    }

    /**
     * 获取本周的开始
     */
    public static String getThisWeekStart() {
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0){
            day_of_week = 7;
        }
        calendar.add(Calendar.DATE, -day_of_week + 1);
        return startTimeFormat.format(calendar.getTime());
    }

    /**
     * 获取本周的结束
     */
    public static String getThisWeekEnd() {
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        calendar.add(Calendar.DATE, -day_of_week + 7);
        return endTimeFormat.format(calendar.getTime());
    }

    /**
     * 获取过去几周的开始
     */
    public static String getLastWeekStart(String value) {
        int week = Integer.parseInt(value);
        Date a = DateUtils.addDays(new Date(), -1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.WEEK_OF_YEAR, -week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return startTimeFormat.format(calendar.getTime());
    }

    /**
     * 获取过去几周的结束
     */
    public static String  getLastWeekEnd() {

        Date a = DateUtils.addDays(new Date(), -1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.set(Calendar.DAY_OF_WEEK, 1);

        return endTimeFormat.format(calendar.getTime());
    }

    /**
     * 获取未来几周的开始
     */
    public static String getNextWeekStart() {
        Date a = DateUtils.addDays(new Date(), -1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return startTimeFormat.format(calendar.getTime());
    }
    /**
     * 获取未来几周的结束
     */
    public static String  getNextWeekEnd(String value) {
        int week = Integer.parseInt(value);
        Date a = DateUtils.addDays(new Date(), 6);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, 1);

        return endTimeFormat.format(calendar.getTime());
    }
    /**
     * 获取过去几月的开始
     */
    public static String getLastMonthStart(String value) {
        int month = Integer.parseInt(value);
        Date a = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.MONTH, -month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return startTimeFormat.format(calendar.getTime());
    }
    /**
     * 获取过去几月的结束
     */
    public static String  getLastMonthEnd() {
        Date a = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        return endTimeFormat.format(calendar.getTime());
    }

    /**
     * 获取未来几月的开始
     */
    public static String getNextMonthStart() {
        Date a = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return startTimeFormat.format(calendar.getTime());
    }
    /**
     * 获取未来几月的结束
     */
    public static String  getNextMonthEnd(String value) {
        int month = Integer.parseInt(value) + 1;
        Date a = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 0);

        return endTimeFormat.format(calendar.getTime());
    }

    /**
     * 获取本月的开始
     */
    public static String getThisMonthStart() {
        Date a = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return startTimeFormat.format(calendar.getTime());
    }
    /**
     * 获取本月的结束
     */
    public static String  getThisMonthEnd() {
        Date a = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);

        return endTimeFormat.format(calendar.getTime());
    }

    /**
     * 获取今年的开始
     */
    public static String getThisYearStart() {
        Date a = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.YEAR, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);

        return startTimeFormat.format(calendar.getTime());
    }
    /**
     * 获取今年的结束
     */
    public static String  getThisYearEnd() {
        Date a = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 0);

        return endTimeFormat.format(calendar.getTime());
    }

    /**
     * 获取过去N年的开始
     */
    public static String getLastYearStart(String value) {
        int year = Integer.parseInt(value);
        Date a = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.YEAR, -year);
        calendar.set(Calendar.DAY_OF_YEAR, 1);

        return startTimeFormat.format(calendar.getTime());
    }
    /**
     * 获取过去N年的结束
     */
    public static String  getLastYearEnd() {
        Date a = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.YEAR, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 0);

        return endTimeFormat.format(calendar.getTime());
    }

    /**
     * 获取未来几年的开始
     */
    public static String getNextYearStart() {
        Date a = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);

        return startTimeFormat.format(calendar.getTime());
    }
    /**
     * 获取未来几年的结束
     */
    public static String  getNextYearEnd(String value) {
        int year = Integer.parseInt(value) + 1;
        Date a = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        calendar.add(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, 0);

        return endTimeFormat.format(calendar.getTime());
    }


}
