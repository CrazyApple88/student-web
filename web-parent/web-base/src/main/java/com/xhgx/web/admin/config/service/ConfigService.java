package com.xhgx.web.admin.config.service;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;
import com.xhgx.web.admin.config.entity.ConfigEntity;

/**
 * @ClassName ConfigService
 * @Description 配置表service接口
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public interface ConfigService extends BaseService, CacheService {

	/**
	 * 通过key 获取配置的值信息
	 * 
	 * @param key
	 * @return String
	 */
	public String getValue(String key);

	void delete(ConfigEntity entity);
}
