package com.xhgx.sdk.container;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @ClassName SpringContextUtil
 * @Description 获取spring容器相关信息
 * @author zohan(inlw@sina.com) 
 * @date 2017-03-22 下午06:38:47
 * @vresion 1.0
 */
public class SpringContextUtil {
	private static ApplicationContext ctx = null;
	
	/**
	 * 
	* @Title: getBean
	* @date 2017-03-22  
	* @Description: 获取spring管理的bean
	* @param name
	* @return
	 */
	public static Object getBean(String name) {
		if (ctx == null) {
			ctx =getApplicationContext();
		}
		return ctx.getBean(name);
	}
	


	/**
	 * 获取上下文环境 获取spring的ApplicationContext
	 * @return
     */
	public static ApplicationContext getApplicationContext(){
		ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(WebContextUtil.getServletContext());
		return ctx;
	}
	
}
