package com.hy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
** 主要用于mm模块中时间处理
 */
public class DateUtil {

    private static final String state1 = "未开始";
    private static final String state2 = "进行中";
    private static final String state3 = "已结束";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf_bre = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 将date转化成String
     */
    public static String translate(Date date){
        return sdf.format(date);
    }

    /**
     * 重写函数
     * 将string转化成date
     */
    public static Date translate(String dateString){
        try {
            return sdf.parse(dateString);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过起始时间和结束时间返回当前状态
     */
    public static String getState(Date begindate, Date enddate){
        Date now = new Date();
        if(now.before(begindate)){
            return state1;
        }
        else if(now.after(enddate)){
            return state3;
        }
        else {
            return state2;
        }
    }
    /**
     * 将date转化成没有时间的格式的string类型
     */
    public static String breviary(Date date){
        return sdf_bre.format(date);
    }

    /**
     * 将string转化成没有时间的格式的date类型
     */
    public static Date breviary(String dateString){
        try {
            return sdf_bre.parse(dateString);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取两段日期中间的日期+前一天+后一天
     */
    public static List<String> getDays(Date begindate, Date enddate){
        if(enddate.before(begindate)){
            return null;
        }
        else {
            List<String> days = new ArrayList<>();
            Calendar begin = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            begin.setTime(begindate);
            end.setTime(enddate);
            int day = end.get(Calendar.DAY_OF_YEAR) - begin.get(Calendar.DAY_OF_YEAR);

            //将开始的前一天加入到列表中
            begin.add(Calendar.DAY_OF_YEAR, -1);
            days.add(sdf_bre.format(begin.getTime()));
            begin.add(Calendar.DAY_OF_YEAR, 1);
//            //将开始的前两天加入到列表中
//            begin.add(Calendar.DAY_OF_YEAR, -2);
//            days.add(sdf_bre.format(begin.getTime()));
//            begin.add(Calendar.DAY_OF_YEAR, 1);
//            days.add(sdf_bre.format(begin.getTime()));
//            begin.add(Calendar.DAY_OF_YEAR, 1);
//        for(int i = 2 ; i < day + 4 ; i++){
//            days.add(sdf_bre.format(begin.getTime()));
//            begin.add(Calendar.DAY_OF_YEAR, 1);
//        }
            for(int i = 1 ; i < day + 3 ; i++){
                days.add(sdf_bre.format(begin.getTime()));
                begin.add(Calendar.DAY_OF_YEAR, 1);
            }
            return days;
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前年月的第一天的日期
     * @Date 2018/11/10 15:11
     * @Param [year, month]
     * @return java.util.Calendar
     **/
    public static Calendar getFirstDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return cal;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前年月的最后一天的日期
     * @Date 2018/11/10 15:11
     * @Param [year, month]
     * @return java.util.Calendar
     **/
    public static Calendar getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return cal;
    }
}
