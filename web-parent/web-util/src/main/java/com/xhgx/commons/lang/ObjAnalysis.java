package com.xhgx.commons.lang;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName ObjAnalysis
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */

public class ObjAnalysis {
	
	/**
	 * 对象转换成Map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> convertObjToMap(Object obj) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null){
			return null;
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				try {
					Field f = obj.getClass().getDeclaredField(fields[i].getName());
					f.setAccessible(true);
					Object o = f.get(obj);
					if(o != null){
						reMap.put(fields[i].getName(), o);
					}
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return reMap;
	}
	
	/**
	 * 获取一个随机字符串
	 * @param length	长度
	 * @return
	 */
	public static String getRandomString(int length) {   
        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");   
        StringBuffer sb = new StringBuffer();   
        Random random = new Random();   
        int range = buffer.length();   
        for (int i = 0; i < length; i ++) {   
            sb.append(buffer.charAt(random.nextInt(range)));   
        }   
        return sb.toString();   
    }  
	
	/**
	 * 根据code,获取新code的值（也就是自增加1《如code=001002003 ，len = 3，返回001002004
	 * @param code	编号
	 * @param len	编号以多少位进行循环使用
	 * @return
	 */
	public static String getNewCodeByCode(String code, int len){
		if(code == null){
			return "";
		}
		String firstStr = code.substring(0,code.length()-len);
    	String endStr = code.substring(code.length()-len, code.length());
    	int newStr = Integer.parseInt(endStr) + 1;
    	// 10的len次方减1
		if (newStr > Math.pow(10, len) - 1) {
			newStr = 1;
		}
    	String newEndStr = "";
    	for(int i=0;i<len - (newStr+"").length();i++){
    		newEndStr += "0";
    	}
    	String newCode = firstStr + newEndStr + newStr;
    		return newCode;
    	}
	
	public static void main(String[] args) {
		System.out.println(getNewCodeByCode("001",3));
//		TestEntity c  = new TestEntity();
//		c.setId("123");
//      
//		Map<String, Object> map = convertObjToMap(c);
//		System.out.println(map.toString());
	}
	
}
