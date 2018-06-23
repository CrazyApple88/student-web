package com.xhgx.web.admin.menu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;
import com.xhgx.web.admin.menu.entity.MenuEntity;

/**
 * @ClassName MenuService
 * @Description 菜单权限表service接口
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public interface MenuService extends BaseService, CacheService {

	/**
	 * 通过key 获取配置的值信息
	 * 
	 * @param key
	 * @return String
	 */
	public String getValue(String key);

	/**
	 * 通过关联信息获取菜单权限
	 * 
	 * @param paramsMap
	 * @return List
	 */
	public List<HashMap> getMenusLeftJoinChecked(Map<String, String> paramsMap);

	/**
	 * 根据企业ID查询菜单树型结构
	 * 
	 * @param paramsMap
	 * @return List
	 */
	public List<HashMap> queryMenuTreeByParams(Map<String, String> paramsMap);

	/**
	 * 根据父菜单Id查找父菜单信息以及父菜单下最大的menuCode
	 * 
	 * @param paramsMap
	 * @return Map
	 */
	public Map<String, Object> queryMenuParentId(Map<String, String> paramsMap);

	/**
	 * 获取菜单权限
	 * 
	 * @param param
	 * @return List
	 */
	public List<MenuEntity> getMenuList(Map<String, String> param);

	/**
	 * 删除菜单ID，删除菜单表tb_sys_menu中菜单信息，删除菜单中间表tb_sys_menu_center信息
	 * 
	 * @param menu
	 */
	public void deleteByMenuId(MenuEntity menu);
/*
 * 	菜单图标功能修改，不需要重写save和update方法
	public void save(MenuEntity menu);

	public void update(MenuEntity menu);*/
	
	/**
	 * 根据 pid、sort和target_sort查询拖拽时所有受影响的菜单信息
	 * @param paramsMap
	 * @return
	 */
	public List<MenuEntity> getMenuListBySort(Map<String, Object> paramsMap);

}
