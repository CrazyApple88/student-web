package com.xhgx.commons.properties;

import java.util.Enumeration;
import java.util.Properties;

import javax.annotation.PostConstruct;

/**
 * @ClassName ProUtils
 * @Description 读取配置文件工具类
 * @author ZhangJin
 * @date 2017年6月21日
 */
public class ProUtils {

	private static Properties p = new Properties();

	@PostConstruct
	public static Properties loadProp() {
		try {
			p.load(ProUtils.class.getClassLoader().getResourceAsStream(
					"config.properties"));
		} catch (Exception e) {
			throw new RuntimeException("配置文件加载失败，请检查文件类型、路径、名称是否正确！");
		}
		return p;
	}

	/**
	 * 获取当前p
	 */
	public static Properties getProp() {
		return p;
	}

	/**
	 * 读取properties配置文件信息，配置文件需要放在classpath根目录下。
	 */
	public static Properties getProperties(String resource) {
		Properties p = new Properties();
		try {
			p.load(ProUtils.class.getClassLoader()
					.getResourceAsStream(resource));
		} catch (Exception e) {
			throw new RuntimeException("配置文件加载失败，请检查文件类型、路径、名称是否正确！");
		}
		return p;
	}

	/**
	 * 可追加读取多个properties配置文件信息，封装为Map，如果存进去的Key相同则会被覆盖，配置文件需要放在classpath根目录下。
	 */
	public static Properties getGlobleProperties(String resource) {
		Properties pro = getProperties(resource);
		for (Enumeration<?> e = pro.propertyNames(); e.hasMoreElements();) {
			// 遍历所有元素
			String s = (String) e.nextElement();
			p.setProperty(s, pro.getProperty(s));
		}
		return p;
	}

	/**
	 * 根据key得到value的值
	 */
	public static String getValue(String key) {
		return p.getProperty(key);
	}
}