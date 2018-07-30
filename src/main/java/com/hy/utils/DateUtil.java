package com.hy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
