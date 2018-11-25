package com.glodon.water.common.util;

import java.util.Date;


/**
 * @Description 反射获取时间参数类型并换成date
 * @author zhouzy-a
 *
 */
public class DateReflectUtil {
    
    public static Date DateReflect(Object startTime){
        Date date = null;
        if(startTime==null){
            return date;
        }
        Class<? extends Object> clazz = startTime.getClass(); 
        String classString = clazz.toString();
        if(classString.equals("class java.lang.Long")){
            date = new Date(Long.parseLong(startTime.toString()));
        }else if(classString.equals("class java.lang.String")){
            if(startTime.toString()==""){
                return date;
            }
            try {
                date = DateUtil.string2Date(startTime.toString(),"yyyy-MM-dd");
            } catch (Exception e) {
                date =null;
            } 
        }else if(classString.equals("class java.util.Date")){
            date = (Date) startTime;
        }
        
        return date;
    }
}
