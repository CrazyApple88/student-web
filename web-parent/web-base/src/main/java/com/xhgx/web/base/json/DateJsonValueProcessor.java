package com.xhgx.web.base.json;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import org.springframework.util.StringUtils;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateJsonValueProcessor
 * @Description js日期格式化
 * @author zohan(inlw@sina.com)
 * @date 2017-03-29 14:33
 * @vresion 1.0
 */
public class DateJsonValueProcessor  implements JsonValueProcessor {




    /**默认时间格式*/
    private String pattern = "yyyy-MM-dd HH:mm:ss";



    public DateJsonValueProcessor(String pattern) {
        super();
        this.pattern = pattern;
    }


    public Object processArrayValue(Object value, JsonConfig jsonConfig) {

        return null;
    }

    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        if (null == value || value.equals(null)){
        	 return "";
        }
         Date date = null ;
        if(value instanceof Date){
            date = (Date) value;
        }else if(value instanceof Timestamp){
            Timestamp time = (Timestamp) value;
            date = new Date(time.getTime());
        }else if (value instanceof java.sql.Date){
            date = (java.sql.Date) value;
        }else{
            throw  new RuntimeException("未找到对应的日期类型:"+ value.getClass().getPackage());
        }

        return formatDate(date);
    }



    private String formatDate(Date value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        return dateformat.format(value);
    }


}
