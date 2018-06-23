package com.xhgx.web.base.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName RequestSearch
 * @Description 查询条件转换
 * @author libo
 * @date 2017年4月26日
 * @vresion 1.0
 */
public class RequestSearch {


	/**
	 * 将request中特定的查询字段转换为所需字段
	 * @param request
	 * @return
	 */
	public static Map<String,Object>  getSearch(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		Enumeration e = request.getParameterNames();
		while( e.hasMoreElements())   {   
		    String elementName=(String)e.nextElement();   
		  // 过滤带有查询标识的字段
		    if(elementName.startsWith("search.")){
			    String value=request.getParameter(elementName);
			    if(value != null && !"".equals(value)){
			    	// 去掉首尾空格
			    	value = value.trim();
			    }
			  // 去掉查询标识放到key中
			    elementName = elementName.replaceFirst("search.","");
			    map.put(elementName, value);
		    }
		}  
		return map;
	}
}
