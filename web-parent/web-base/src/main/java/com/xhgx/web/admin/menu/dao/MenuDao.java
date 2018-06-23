package com.xhgx.web.admin.menu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.web.admin.menu.entity.MenuEntity;

/**
 * @ClassName MenuDao
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public interface MenuDao extends GenericDao<MenuEntity, String> {

	public List<HashMap> getMenusLeftJoinChecked(Map<String, String> paramsMap);

	public List<HashMap> queryMenuTreeByParams(Map<String, String> paramsMap);

	public Map<String, Object> queryMenuParentId(Map<String, String> paramsMap);

	public List<MenuEntity> getMenuList(Map<String, String> paramsMap);
	
	/**
	 * 根据 pid、sort和target_sort查询拖拽时所有受影响的菜单信息
	 * @param paramsMap
	 * @return
	 */
	public List<MenuEntity> getMenuListBySort(Map<String, Object> paramsMap);
}
