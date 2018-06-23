package com.xhgx.web.admin.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.xhgx.commons.lang.MD5Utils;

/**
 * @ClassName Md5PasswordEncoder
 * @Description md5 的匹配算法
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 15:41
 * @vresion 1.0
 */
public class Md5PasswordEncoder implements PasswordEncoder {

	public String encode(CharSequence rawPassword) {
		return MD5Utils.getMD5String(String.valueOf(rawPassword));
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(MD5Utils.getMD5String(String
				.valueOf(rawPassword)));
	}

}
