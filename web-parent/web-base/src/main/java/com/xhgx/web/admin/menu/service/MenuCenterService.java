package com.xhgx.web.admin.menu.service;

import java.util.List;
import java.util.Map;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.web.admin.menu.entity.MenuCenterEntity;

/**
 * @ClassName MenuCenterService
 * @Description 菜单权限表service接口
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public interface MenuCenterService extends BaseService {

	/**
	 * 批量保存用户/角色/部门 和权限菜单的中间关系
	 * 
	 * @param list
	 * @param paramsMap
	 */
	public void updateMenuCenters(List<MenuCenterEntity> list,
			Map<String, String> paramsMap);

}
