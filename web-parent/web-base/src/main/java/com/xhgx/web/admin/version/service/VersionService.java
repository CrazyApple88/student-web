package com.xhgx.web.admin.version.service;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;
import com.xhgx.web.admin.version.entity.Version;

/**
 * @ClassName VersionService
 * @Description tb_sys_version 业务逻辑层接口
 * @author <font color='blue'>zhangjin</font>
 * @date 2017-06-12 15:22:03
 * @version 1.0
 * 
 */
public interface VersionService extends BaseService, CacheService {

	/**
	 * 通过id快速加载对象
	 * 
	 * @param versionId
	 * @return Version
	 */
	public Version getVersion(String versionId);

}