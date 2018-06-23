package com.xhgx.web.admin.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * @ClassName VerifyWebAuthenticationDetails
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 18:29
 * @vresion 1.0
 */
public class VerifyWebAuthenticationDetails extends WebAuthenticationDetails {

	/**存储验证码*/
	private final String code;

	public VerifyWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		code = request.getParameter("code");
	}

	public String getCode() {
		return code;
	}
}
