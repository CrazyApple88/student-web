package com.xhgx.web.admin.dept.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.web.admin.dept.entity.DeptEntity;
import com.xhgx.web.admin.menu.entity.MenuEntity;

/**
 * @ClassName DeptDao
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public interface DeptDao extends GenericDao<DeptEntity, String> {

	/**
	 * 按条件查询不分页列表
	 * 
	 * @return List
	 */
	public List<DeptEntity> queryAllObj();

	/**
	 * 按条件查询记录个数
	 * 
	 * @param entity
	 * @return int
	 */
	int queryCount(DeptEntity entity);

	/**
	 * @param param
	 * @return List
	 */
	List<HashMap> queryMenuTree(Map<String, Object> param);

	public Map<String, Object> queryDeptParentId(Map<String, String> paramsMap);

	/**
	 * 查出父ID查询最大的code,顶级菜单专用
	 * 
	 * @param paramsMap
	 * @return Map
	 */
	public Map<String, Object> queryMaxDeptCodeParentId(
			Map<String, String> paramsMap);
	/**
	 * 根据 pid、sort和target_sort查询拖拽时所有受影响的部门信息
	 * @param paramsMap
	 * @return
	 */
	public List<DeptEntity> getDeptListBySort(Map<String, Object> paramsMap);
}
