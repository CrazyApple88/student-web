package com.xhgx.web.admin.config.service;

import com.xhgx.sdk.container.SpringContextUtil;
import com.xhgx.web.base.properties.CustomizedPropertyPlaceholderConfigurer;

/**
 * @ClassName ConfigHelper
 *@Description 配置项快速访问
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 17:44
 * @vresion v1.0
 */
public class ConfigHelper {

	private static ConfigHelper helper;
	ConfigService configService;

	private ConfigHelper() {
		super();
		configService = (ConfigService) SpringContextUtil
				.getBean("configService");
	}

	/**
	 * 单例模式
	 */
	public static ConfigHelper getInstance() {
		if (helper == null) {
			helper = new ConfigHelper();
		}
		return helper;
	}

	/**
	 * 获取指定key的值
	 * 配置文件中的配置信息将和数据库中表tb_sys_config进行合并，如果有相同key值将以tb_sys_config表为准，表可以页面修改实时更新配置(xml中取值只能从配置文件中获取)
	 * @param key
	 * @return String
	 */
	public String get(String key) {
		String value = configService.getValue(key);
		//如果数据库中不存在，则取配置文件中读取(全局map)
		if(value == null){
			value = CustomizedPropertyPlaceholderConfigurer.getContextProperty(key);
		}
		return value;
	}

}
