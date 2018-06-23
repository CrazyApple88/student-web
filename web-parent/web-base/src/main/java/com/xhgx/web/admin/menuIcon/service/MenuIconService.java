package com.xhgx.web.admin.menuIcon.service;
import com.xhgx.web.admin.menuIcon.entity.MenuIcon;
import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;

 /**
 * @ClassName MenuIconService
 * @Description tb_sys_menu_icon 业务逻辑层接口
 * @author <font color='blue'>ryh</font> 
 * @date 2018-01-18 17:19:32
 * @version 1.0
 */
 public interface MenuIconService extends BaseService,CacheService{
	 
	 /**
	 * 通过id快速加载对象
	 * 
	 * @param menuIconId
	 * @return
	 */
	 public MenuIcon getMenuIcon(String menuIconId);
	  
}