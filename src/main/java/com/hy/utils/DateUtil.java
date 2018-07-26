package com.hy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
** 主要用于会议管理中时间处理
 */
public class DateUtil {

    private static final String state1 = "未开始";
    private static final String state2 = "进行中";
    private static final String state3 = "已结束";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String translate(Date date){
        return sdf.format(date);
    }

    public static Date translate(String dateString){
        try {
            return sdf.parse(dateString);
        }catch (ParseException e){
            return null;
        }
    }

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
}
