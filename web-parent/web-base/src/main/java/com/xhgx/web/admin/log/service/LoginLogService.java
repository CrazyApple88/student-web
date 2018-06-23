package com.xhgx.web.admin.log.service;

import com.xhgx.sdk.service.BaseService;

/**
 * @ClassName LoginLogService
 * @Description 登录日志表service接口
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public interface LoginLogService extends BaseService {

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	public void deleteBatch(String[] ids);

}
