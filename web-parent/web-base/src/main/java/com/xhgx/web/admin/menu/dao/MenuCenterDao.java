package com.xhgx.web.admin.menu.dao;

import java.util.List;
import java.util.Map;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.web.admin.menu.entity.MenuCenterEntity;

/**
 * @ClassName MenuCenterDao
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */

public interface MenuCenterDao extends GenericDao<MenuCenterEntity, String> {

	/**
	 * 根据关联表ID、关联表类型(1用户，2角色，3部门)、权限类型(1访问类型，2授权类型)进行删除
	 * 
	 * @param paramsMap
	 */
	public void deleteMenuCentersByMenuCenter(Map<String, String> paramsMap);

	/**
	 * 根据菜单ID删除信息
	 * 
	 * @param paramsMap
	 */
	public void deleteMenuCentersByMenuId(Map<String, String> paramsMap);

	/**
	 * 批量保存用户/角色/部门 和权限菜单的中间关系
	 * 
	 * @param list
	 */
	public void saveMenuCenterBatch(List<MenuCenterEntity> list);
}
