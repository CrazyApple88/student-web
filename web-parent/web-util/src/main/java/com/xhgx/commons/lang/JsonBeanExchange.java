package com.xhgx.commons.lang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @ClassName JsonBeanExchange
 * @Description Json和Bean的互换
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class JsonBeanExchange {
	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	
	public static <T> String listTJson(List<T> list){  
		String json = gson.toJson(list);
		return json;
	}
	
	
	public static <T> List<T> jsonToList(String json, T entity){  
		List<T> list = new ArrayList<T>();
		JSONArray jsonarray = JSONArray.fromObject(json);  
        System.out.println(jsonarray);  
        List listJson = (List)JSONArray.toCollection(jsonarray, entity.getClass());  
        Iterator it = listJson.iterator();  
        while(it.hasNext()){  
        	T p = (T)it.next();  
        	list.add(p);
        }  
        return list;
        
  }  
	
	public static <T> List<T> jsonToList1(String json, T entity){  
		List<T> list = new ArrayList<T>();
        JSONArray jsonarray = JSONArray.fromObject(json);  
        System.out.println(jsonarray);  
        List listJson = (List)JSONArray.toList(jsonarray, entity.getClass());  
        Iterator it = listJson.iterator();  
        while(it.hasNext()){  
        	T p = (T)it.next();  
        	list.add(p);
        }  
        return list;
    }  
      
//    public void jsonToList2(String json, T entity){  
//        JSONArray jsonarray = JSONArray.fromObject(json);  
//        System.out.println(jsonarray);  
//        System.out.println("------------");  
//        List list = (List)JSONArray.toList(jsonarray, new RoleEntity(), new JsonConfig());  
//        Iterator it = list.iterator();  
//        while(it.hasNext()){  
//        	RoleEntity p = (RoleEntity)it.next();  
//            System.out.println(p.getRoleName());  
//        }  
//          
//    }  
	
	public static Object getclass(String className)//className是类名
	{
		Object obj = null;
		try {
			obj = Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} //以String类型的className实例化类
		return obj;
	}
	
}
