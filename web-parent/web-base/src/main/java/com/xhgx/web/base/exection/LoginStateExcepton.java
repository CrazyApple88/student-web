package com.xhgx.web.base.exection;
/**
 * @ClassName LoginStateExcepton
 * @Description 登录异常
 * @author zohan
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class LoginStateExcepton extends RuntimeException {


	public LoginStateExcepton() {
		super();
	}

	public LoginStateExcepton(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LoginStateExcepton(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginStateExcepton(String message) {
		super(message);
	}

	public LoginStateExcepton(Throwable cause) {
		super(cause);
	}
	
}
