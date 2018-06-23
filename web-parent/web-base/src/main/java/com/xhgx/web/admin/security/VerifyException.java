package com.xhgx.web.admin.security;

import org.springframework.security.authentication.AccountStatusException;

/**
 * @ClassName VerifyException
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 18:53
 * @vresion 1.0
 */
public class VerifyException extends AccountStatusException {

	public VerifyException(String msg) {
		super(msg);
	}

	public VerifyException(String msg, Throwable t) {
		super(msg, t);
	}
}
