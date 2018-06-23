package com.xhgx.commons.lang;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @ClassName DateUtils
 * @Description 时间工具类
 * @author zohan(inlw@sina.com)
 * @date 2017-03-30 17:10
 * @vresion 1.0
 */
public class DateUtils {

    /**默认日期的格式化*/
    public final static String DEFAULT_DATE_FORMAT="yyyy-MM-dd";
    /**默认的时间格式*/
    public final static String DEFAULT_TIME_FORMAT="HH:mm:ss";
    /**默认日期+时间的格式*/
    public final static String DEFAULT_DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss";

    /**
     * 系统默认的日期格式化
     * @param date
     * @return
     */
    public static String defaultFormateDate(Date date){
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
        return df.format(date);
    }


    /**
     * 日期格式化
     * @param date
     * @param pattern
     * @return
     */
    public static String formateDate(Date date,String pattern){
        if(StringUtils.isBlank(pattern)){
            pattern = DEFAULT_DATE_FORMAT;
        }
        return formateDateTime(date,pattern);
    }


    /**
     * 时间格式化
     * @param date
     * @param pattern
     * @return
     */
    public static String formateTime(Date date,String pattern){
        if(StringUtils.isBlank(pattern)){
            pattern = DEFAULT_TIME_FORMAT;
        }
        return formateDateTime(date,pattern);
    }


    /**
     * 日期时间格式化
     * @param date
     * @param pattern
     * @return
     */
    public static String formateDateTime(Date date,String pattern){
        if(StringUtils.isBlank(pattern)){
            pattern = DEFAULT_DATE_TIME_FORMAT;
        }
        DateFormat df = new SimpleDateFormat(pattern) ;
        return df.format(date);
    }


}
