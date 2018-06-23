package com.xhgx.web.admin.security.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName LoginService
 * @Description 用户配合spring secrutiy 的登录功能
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 15:35
 * @vresion 1.0
 */
public interface LoginService extends UserDetailsService {

	/**
	 * 用户退出， 执行相应操作：例如记录数据库
	 * 
	 * @param user
	 */
	void logout(OnlineUser user);
	/**
	 * 获取web配置文件中cas的一些参数，例如casServerLoginUrl
	 * <p>Title: gerString</p>  
	 * <p>Description: </p> 
	 * <p>Author: swx</p>
	 * @param request
	 * @param param
	 * @return
	 */
	String gerString(HttpServletRequest request,String param);
	/**
	 * 获取cas服务端登录用户名称
	 * <p>Title: getUserNameByCasServer</p>  
	 * <p>Description: </p> 
	 * <p>Author: swx</p>
	 * @return
	 */
	String getUserNameByCasServer();

}
