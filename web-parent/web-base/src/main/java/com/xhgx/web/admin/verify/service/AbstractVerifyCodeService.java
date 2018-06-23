package com.xhgx.web.admin.verify.service;

import java.awt.image.BufferedImage;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @ClassName AbstractVerifyCodeService
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 17:14
 * @vresion 1.0
 */
public abstract class AbstractVerifyCodeService {

	/**
	 * 标记验证码
	 */
	public static final String VERIFY_CODE = "verify.src.code";

	/**
	 * 验证码启用标志字符串
	 */
	public static final String VERIFY_ENABLE = "verify.enable";
	/**
	 * 验证码的class类
	 */
	public static final String VERIFY_CLASS = "verify.class";

	/**
	 * 验证码的图像
	 */
	protected BufferedImage image;

	/**
	 * @return Object
	 * @Title: createImage
	 * @date 2017-5-3 17:16:55
	 * @Description: 创建验证码图片, 并返回需要保存的原码
	 */
	public abstract Object createImage();

	/**
	 * @param o
	 *            需要验证的对象
	 * @return boolean
	 * @Title: valdateCode
	 * @date 2017-5-3 17:16:55
	 * @Description:验证o对象的值是否当前验证码
	 */
	public abstract boolean valdateCode(Object o);

	/**
	 * @return Object
	 * @Title: getSrcCode
	 * @date 2017-5-3 17:16:55
	 * @Description: 获取验证码的原值
	 */
	public Object getSrcCode() {

		Object code = RequestContextHolder.currentRequestAttributes()
				.getAttribute(VERIFY_CODE, RequestAttributes.SCOPE_SESSION);

		// Object code = ((ServletWebRequest)
		// RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute(VERIFY_CODE);
		if (code != null) {
			RequestContextHolder.currentRequestAttributes().removeAttribute(
					VERIFY_CODE, RequestAttributes.SCOPE_SESSION);
			// ((ServletWebRequest)
			// RequestContextHolder.getRequestAttributes()).getRequest().getSession().removeAttribute(VERIFY_CODE);
		}
		return code;
	}

	/**
	 * @return BufferedImage
	 */
	public BufferedImage getImage() {
		return image;
	}

}
