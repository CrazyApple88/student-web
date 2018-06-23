package com.xhgx.web.admin.verify.controller;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xhgx.web.admin.verify.VerifyCodeHelper;
import com.xhgx.web.admin.verify.service.AbstractVerifyCodeService;
import com.xhgx.web.base.controller.AbstractBaseController;

/**
 * @ClassName VerifyController
 * @Description 验证码验证
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 17:30
 * @vresion 1.0
 */
@Controller
@RequestMapping("/verify")
@Scope("prototype")
public class VerifyController extends AbstractBaseController {

	/**
	 * @Title: 获取U验证码的图片信息
	 * @date 2017-5-3 17:48:15
	 * @Description: 验证码
	 */
	@RequestMapping("code")
	public void verifyImg() {
		VerifyCodeHelper vs = VerifyCodeHelper.getInstance();

		Object o = vs.getService().createImage();
		this.getRequest().getSession()
				.setAttribute(AbstractVerifyCodeService.VERIFY_CODE, o);
		try {
			ImageIO.write(vs.getService().getImage(), "JPEG", this
					.getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @Title: 验证用户输入的验证码是否正确
	 * @date 2017-5-3 17:49:49
	 * @Description: 验证验证码
	 */
	public void valdateCode() {
		VerifyCodeHelper vs = VerifyCodeHelper.getInstance();
		String code = this.getRequest().getParameter("verifyCode");
		boolean v = vs.getService().valdateCode(code);
		if (v) {
			this.renderJson(getMessage("system.management.text.verification.successfully","验证成功"));
		} else {
			this.renderJson(getMessage("system.management.text.verification.failed","验证失败"));
		}
	}

}
