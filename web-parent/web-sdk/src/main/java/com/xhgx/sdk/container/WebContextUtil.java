package com.xhgx.sdk.container;

import javax.servlet.ServletContext;

/**
 * @ClassName StrutsContextUtil
 * @Description 获取struts容器相关信息
 * @author zohan(inlw@sina.com)
 * @date 2017-03-22 下午06:40:40
 * @vresion 1.0
 */
public class WebContextUtil {

	private static ServletContext sc = null;

	/**
	 * @Title: getRealPath
	 * @date 2017-03-22
	 * @Description: 获取部署的WebApp相应资源实际路径
	 * @param resource
	 * @return
	 */
	public static String getRealPath(String resource) {
		return getServletContext().getRealPath(resource);
	}

	/**
	 * @Title: getServletContext
	 * @date 2017-03-22
	 * @Description: 获取全局上下文ServletContext
	 * @return
	 */
	public static ServletContext getServletContext() {
		return sc;
	}

	/**
	 * @Title: getContextPath
	 * @date 2017-03-22
	 * @Description: 获取部署的Webapp上下文路径
	 * @return
	 */
	public static String getContextPath() {
		return getServletContext().getContextPath();
	}
	
	/**
	* @Title: setServletContext
	* @date 2017-03-22  
	* @Description: 设置全局上下文，在系统启时设置
	* @param sc
	 */
	public static void setServletContext(ServletContext sc){
		WebContextUtil.sc = sc;
	}
}
