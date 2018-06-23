package com.xhgx.web.admin.dept.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.cache.SimpleCacheHelper;
import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.dept.dao.DeptDao;
import com.xhgx.web.admin.dept.entity.DeptEntity;
import com.xhgx.web.admin.dept.service.DeptService;
import com.xhgx.web.admin.menu.dao.MenuCenterDao;
import com.xhgx.web.admin.user.dao.UserDeptDao;

/**
 * @ClassName DeptServiceImpl
 * @Description 部门表serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("deptService")
public class DeptServiceImpl extends BatisBaseServiceImpl implements
		DeptService {

	static Logger log = LoggerFactory.getLogger(DeptServiceImpl.class);

	private final String DEPT_MAP = "dept.map";

	@Override
	public GenericDao<DeptEntity, String> getGenericDao() {
		return deptDao;
	}

	@Autowired
	public DeptDao deptDao;

	@Autowired
	public MenuCenterDao menuCenterDao;

	@Autowired
	public UserDeptDao userDeptDao;

	@Override
	public List<DeptEntity> queryAllObj() {
		return deptDao.queryAllObj();
	}

	@Override
	public int queryCount(DeptEntity entity) {
		return deptDao.queryCount(entity);
	}

	public List<HashMap> queryDeptTree(Map<String, Object> param) {
		return deptDao.queryMenuTree(param);
	}

	public void save(DeptEntity entity) {
		deptDao.save(entity);
		// 把对象放入缓存
		putCache(entity);
	}

	public void update(DeptEntity entity) {
		deptDao.update(entity);
		// 把对象放入缓存
		putCache(entity);
	}

	/**根据部门ID删除部门，1、删除部门表信息，2、删除部门用户中间表信息，3、删除部门菜单中间表信息*/
	public void deleteByDeptId(DeptEntity entity) {
		// 1、删除部门表信息，
		deptDao.delete(entity);
		// 2、删除部门用户中间表信息，
		userDeptDao.deleteUserDeptByDeptId(entity.getId());
		// 3、删除部门菜单中间表信息
		Map<String, String> paramsMap = new HashMap<String, String>();
		// 中间关联表关联的ID
		paramsMap.put("relationId", entity.getId());
		// 关联类型：1用户，2角色，3部门
		paramsMap.put("relationType", "3");
		// paramsMap.put("authType",
		// menuCenter.getAuthType());//1访问权限，2授权权限//这个访问和授权权限都删除
		menuCenterDao.deleteMenuCentersByMenuCenter(paramsMap);

		delCache(entity);
	}

	/**
	 * 根据父级ID得到父级ID的信息
	 */
	public Map<String, Object> queryDeptParentId(Map<String, String> paramsMap) {
		return deptDao.queryDeptParentId(paramsMap);
	}

	public List<DeptEntity> queryList(Map<String, Object> paramsMap) {
		return deptDao.queryList(paramsMap);
	}

	@Override
	public void reloadCache() {
		initCache();
	}

	@Override
	public void initCache() {
		log.info("开始初始化部门信息数据");
		List<DeptEntity> list = deptDao.queryList(null);
		for (DeptEntity entity : list) {
			putCache(entity);
		}
		log.info("初始化部门信息数据完毕，共缓存" + list.size() + "条数据");
	}

	/**
	 * 把对象放入缓存
	 *
	 * @param entity
	 */
	private void putCache(DeptEntity entity) {
		Map<String, DeptEntity> map = (Map<String, DeptEntity>) SimpleCacheHelper
				.get(DEPT_MAP);
		if (map == null) {
			map = new ConcurrentHashMap<String, DeptEntity>();
			SimpleCacheHelper.put(DEPT_MAP, map);
		}
		map.put(entity.getId(), entity);
	}

	/**
	 * 删除缓存中某一个对象
	 *
	 * @param entity
	 */
	private void delCache(DeptEntity entity) {
		Map<String, DeptEntity> map = (Map<String, DeptEntity>) SimpleCacheHelper
				.get(DEPT_MAP);
		if (map == null) {
			map = new ConcurrentHashMap<String, DeptEntity>();
			SimpleCacheHelper.put(DEPT_MAP, map);
		}
		map.remove(entity.getId());
	}
	/**
	 * 根据 pid、sort和target_sort查询拖拽时所有受影响的部门信息
	 * @param paramsMap
	 * @return
	 */
	@Override
	public List<DeptEntity> getDeptListBySort(Map<String, Object> paramsMap) {
		return deptDao.getDeptListBySort(paramsMap);
	}

}
