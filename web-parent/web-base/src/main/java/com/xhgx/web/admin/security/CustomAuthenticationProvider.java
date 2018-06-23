package com.xhgx.web.admin.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.xhgx.web.admin.verify.VerifyCodeHelper;

/**
 * @ClassName CustomAuthenticationProvider
 * @Description 自定义验证
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 18:33
 * @vresion v1.0
 */
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		// 启用验证码
		if (VerifyCodeHelper.getInstance().isEnable()) {
			VerifyWebAuthenticationDetails details = (VerifyWebAuthenticationDetails) authentication
					.getDetails();
			if (!VerifyCodeHelper.getInstance().getService()
					.valdateCode(details.getCode())) {
				throw new VerifyException("验证码错误");
			}
		}
		return super.authenticate(authentication);
	}
}
