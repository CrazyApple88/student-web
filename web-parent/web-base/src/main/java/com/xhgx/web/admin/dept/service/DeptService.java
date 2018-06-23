package com.xhgx.web.admin.dept.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;
import com.xhgx.web.admin.dept.entity.DeptEntity;

/**
 * @ClassName DeptService
 * @Description 部门service接口
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public interface DeptService extends BaseService, CacheService {

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

	public void save(DeptEntity entity);

	public void update(DeptEntity entity);

	List<HashMap> queryDeptTree(Map<String, Object> param);

	void deleteByDeptId(DeptEntity entity);

	Map<String, Object> queryDeptParentId(Map<String, String> paramsMap);

	public List<DeptEntity> queryList(Map<String, Object> paramsMap);
	
	/**
	 * 根据 pid、sort和target_sort查询拖拽时所有受影响的部门信息
	 * @param paramsMap
	 * @return
	 */
	public List<DeptEntity> getDeptListBySort(Map<String, Object> paramsMap);

}
