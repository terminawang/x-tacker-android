package com.finance.geex.statisticslibrary.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created on 2019/8/20 15:45.
 *
 * @author Geex302
 */
public class TimeUtil {

    /**
     * 时间格式转换(yyyy-MM-dd HH:mm:ss ----> yy年MM月dd日 )
     * @param time
     * @return
     */
    public static String stringToString(String time){

        String newTime = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int mouth = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            newTime = year + "年" + mouth + "月" + day + "日";
        } catch (ParseException e) {
            newTime = time;
        }

        return newTime;

    }

    /**
     * long型时间转化为String时间 //yyyy-MM-dd HH:mm:ss
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static String longToString(long currentTime, String formatType){
        String strTime = "";
        try {
            Date date = longToDate(currentTime, formatType); // long类型转成Date类型
            strTime = dateToString(date, formatType); // date类型转成String
        }catch (Exception e){
            strTime = "";
        }

        return strTime;
    }

    private static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    private static Date longToDate(long currentTime, String formatType){
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    private static Date stringToDate(String strTime, String formatType){
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
