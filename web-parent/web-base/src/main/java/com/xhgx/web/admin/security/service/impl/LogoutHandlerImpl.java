package com.xhgx.web.admin.security.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.xhgx.web.admin.security.service.LoginService;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName LogoutHandlerImpl
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-12 15:47
 * @vresion v1.0
 */
@Component("logoutHandlerImpl")
public class LogoutHandlerImpl implements LogoutHandler {
	@Autowired
	@Qualifier("loginService")
	LoginService loginService;

	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		if (authentication == null){
			return;
		}
		OnlineUser user = (OnlineUser) authentication.getPrincipal();
		if (user != null) {
			// TODO 业务量上去后考虑异步
			loginService.logout(user);
		}
	}
}
