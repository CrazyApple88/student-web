package com.xhgx.web.admin.cache.service;

import java.util.Map;

import javax.servlet.ServletContextEvent;

import com.xhgx.sdk.entity.Page;
import com.xhgx.sdk.service.BaseService;

/**
 * @ClassName ICacheService
 * @Description 内存信息service
 * @author ZhangJin
 * @date 2017年6月20日
 * @vresion 1.0
 */
public interface ICacheService extends BaseService {

	public void findPage(Map param, Page page, String orderBy,
			ServletContextEvent contextEvent);

}
