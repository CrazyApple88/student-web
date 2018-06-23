package com.xhgx.web.admin.security;

import org.springframework.security.authentication.encoding.BaseDigestPasswordEncoder;

import com.xhgx.commons.lang.MD5Utils;

/**
 * @ClassName Md5SaltPasswordEncoder
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-04 17:32
 * @vresion 1.0
 */
public class Md5SaltPasswordEncoder extends BaseDigestPasswordEncoder {

	@Override
	public String encodePassword(String rawPass, Object salt) {
		return MD5Utils.getMD5String(String.valueOf(rawPass
				+ String.valueOf(salt)));
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		return encPass.equals(MD5Utils.getMD5String(String.valueOf(rawPass
				+ String.valueOf(salt))));
	}
}
