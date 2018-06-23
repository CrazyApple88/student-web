package com.xhgx.web.admin.security.service.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.xhgx.web.admin.security.service.LoginService;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName LogoutSuccessHandlerImpl
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-12 8:48
 * @vresion 1.0
 */

@Component("logoutSuccessHandlerImpl")
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

	@Autowired
	@Qualifier("loginService")
	LoginService loginService;

	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
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
