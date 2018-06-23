package com.xhgx.web.app.appLogin.service;

import java.util.Map;

import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName AppLoginService
 * @Description 
 * @author ZhangJin
 * @date 2017年6月27日
 * @vresion 1.0
 */
public interface AppLoginService {

	/**
	 * 用户退出， 执行相应操作：例如记录数据库
	 * 
	 * @param user
	 */
	void logout(OnlineUser user);

	/**
	 * 用户登录
	 * 
	 * @param username
	 */
	public Map<String, Object> loadAppUserInfo(String username);

}
