package com.xhgx.web.admin.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.web.admin.menu.entity.MenuEntity;
import com.xhgx.web.admin.user.entity.UserEntity;

/**
 * @ClassName UserDao
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */

public interface UserDao extends GenericDao<UserEntity, String> {

	public List<HashMap> getRolesLeftJoinUserChecked(
			Map<String, String> paramsMap);

	public List<HashMap> getDeptsLeftJoinUserChecked(
			Map<String, String> paramsMap);
	/**
	 * 查询该企业下所有不隶属于任何部门的用户
	 */
	public List<HashMap> getUsersByCompNotDept(
			Map<String, String> paramsMap);
	/**
	 * 查询该企业下所有隶属于部门的用户及对应的部门信息
	 */
	public List<HashMap> getUserByCompInDept(
			Map<String, String> paramsMap);
	public List<UserEntity> queryByUserNameAndComp(Map<String, Object> param);

	public void updatePasswordById(UserEntity user);

	public List<MenuEntity> getMenuByUserId(Map<String, String> paramsMap);

	/**
	 * 按条件查询不分页列表
	 */
	public List<UserEntity> queryAllObj(UserEntity entity);

}
