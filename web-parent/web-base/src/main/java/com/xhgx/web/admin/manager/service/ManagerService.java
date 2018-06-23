package com.xhgx.web.admin.manager.service;
import java.util.Map;

import com.xhgx.web.admin.manager.entity.Manager;
import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;

 /**
 * @ClassName: ManagerService
 * @Description: tb_sys_manager 业务逻辑层接口
 * @author: <font color='blue'>swx</font> 
 * @date:  2018-05-17 13:51:43
 * @version: 1.0
 * 
 */
 public interface ManagerService extends BaseService,CacheService{
	 
	 /**
	 * 通过id快速加载对象
	 * 
	 * @param managerId
	 * @return
	 */
	 public Manager getManager(String managerId);
}