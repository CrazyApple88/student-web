package com.xhgx.web.admin.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * @ClassName CustomAuthenticationDetailsSource
 * @Description 自定义验证
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 18:31
 * @vresion 1.0
 */
public class CustomAuthenticationDetailsSource
		implements
		AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

	@Override
	public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
		return new VerifyWebAuthenticationDetails(context);
	}

}
