package com.xhgx.web.admin.verify;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.xhgx.web.admin.config.service.ConfigHelper;
import com.xhgx.web.admin.verify.service.AbstractVerifyCodeService;
import com.xhgx.web.admin.verify.service.impl.NumberCodeService;

/**
 * @ClassName VerifyCodeHelper
 * @Description 验证码帮助类
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 17:13
 * @vresion 1.0
 */
public class VerifyCodeHelper {

	private static String serviceName = "";

	/**
	 * 验证码服务类
	 */
	private static AbstractVerifyCodeService defaultCodeService = new NumberCodeService();;

	/**多个服务的集合*/
	private static Map<String, AbstractVerifyCodeService> codeServices = new HashMap<String, AbstractVerifyCodeService>();

	private static VerifyCodeHelper verifyCodeHelper;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	private VerifyCodeHelper() {
		super();
	}

	/**
	 * @return VerifyCodeHelper
	 * @Title: getInstance
	 * @date 2017-5-3 17:32:32
	 * @Description: 单例
	 */
	public static VerifyCodeHelper getInstance() {
		if (verifyCodeHelper == null) {
			verifyCodeHelper = new VerifyCodeHelper();
		}
		return verifyCodeHelper;
	}

	/**
	 * 是否启用验证码
	 *
	 * @return boolean
	 */
	public boolean isEnable() {
		return Boolean.parseBoolean(ConfigHelper.getInstance().get(
				AbstractVerifyCodeService.VERIFY_ENABLE));
	}

	/**
	 * @return AbstractVerifyCodeService
	 * @Title: getService
	 * @date 2017-5-3 17:32:32
	 * @Description:获取验证码服务类
	 */
	public AbstractVerifyCodeService getService() {
		String service = ConfigHelper.getInstance().get(
				AbstractVerifyCodeService.VERIFY_CLASS);
		if (StringUtils.isBlank(service)) {
			return defaultCodeService;
		}
		return getService(service);
	}

	/**
	 * 获取生成验证码的具体对象
	 * 
	 * @param serviceName
	 * @return AbstractVerifyCodeService
	 */
	public AbstractVerifyCodeService getService(String serviceName) {
		if (StringUtils.isNotBlank(serviceName)) {
			AbstractVerifyCodeService codeService = codeServices
					.get(serviceName);
			if (codeService != null){
				return codeService;
			}
			try {
				codeService = (AbstractVerifyCodeService) Class.forName(
						serviceName).newInstance();
			} catch (InstantiationException e) {
				codeService = new NumberCodeService();
			} catch (IllegalAccessException e) {
				codeService = new NumberCodeService();
			} catch (ClassNotFoundException e) {
				codeService = new NumberCodeService();
			}
			codeServices.put(serviceName, codeService);
			return codeService;
		} else {
			throw new IllegalArgumentException("验证码实现类配置错误！");
		}
	}

}
