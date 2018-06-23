package com.xhgx.web.admin.menu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.menu.dao.MenuCenterDao;
import com.xhgx.web.admin.menu.entity.MenuCenterEntity;
import com.xhgx.web.admin.menu.service.MenuCenterService;

/**
 * @ClassName MenuCenterServiceImpl
 * @Description 菜单权限表serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("menuCenterService")
public class MenuCenterServiceImpl extends BatisBaseServiceImpl implements
		MenuCenterService {

	@Override
	public GenericDao<MenuCenterEntity, String> getGenericDao() {
		return menuCenterDao;
	}

	@Autowired
	public MenuCenterDao menuCenterDao;

	/**
	 * 批量保存用户/角色/部门 和权限菜单的中间关系
	 * 
	 * @param list
	 * @param paramsMap
	 */
	public void updateMenuCenters(List<MenuCenterEntity> list,
			Map<String, String> paramsMap) {
		// 先删除原先的关联关系
		menuCenterDao.deleteMenuCentersByMenuCenter(paramsMap);
		// 保存现有的关系
		// menuCenterDao.saveMenuCenterBatch(list);//oracle不支持这种写法，尴尬！！
		for (MenuCenterEntity menuCenter : list) {
			menuCenterDao.save(menuCenter);
		}
	}

}
