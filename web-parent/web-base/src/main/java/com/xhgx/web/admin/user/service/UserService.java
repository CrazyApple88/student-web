package com.xhgx.web.admin.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.web.admin.menu.entity.MenuEntity;
import com.xhgx.web.admin.user.entity.UserEntity;

/**
 * @ClassName UserService
 * @Description 用户表service接口
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public interface UserService extends BaseService {

	/**
	 * 根据ID的数组删除多条用户数据
	 * 
	 * @param ids
	 */
	public void deleteBatch(String[] ids);

	/**
	 * 新增保存用户信息
	 * 
	 * @param user
	 */
	public void save(UserEntity user);

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 */
	public void update(UserEntity user);

	/**
	 * 根据用户ID修改密码
	 * 
	 * @param user
	 */
	public void updatePasswordById(UserEntity user);

	/**
	 * 根据用户名和企业获取用户信息
	 * 
	 * @param userName
	 * @param comp
	 * @return UserEntity
	 */
	public UserEntity queryByUserNameAndComp(String userName, String comp);

	/**
	 * 获取企业下的所有符合条件的角色， 同时根据用户所选的角色，进行LEFT JOIN 查询出哪个是用户已经选择的角色
	 * 
	 * @param paramsMap
	 * @return List
	 */
	public List<HashMap> getRolesLeftJoinUserChecked(
			Map<String, String> paramsMap);

	/**
	 * 获取企业下的所有符合条件的部门， 同时根据用户所选的部门，进行LEFT JOIN 查询出哪个是用户已经选择的部门
	 * 
	 * @param paramsMap
	 * @return List
	 */
	public List<HashMap> getDeptsLeftJoinUserChecked(
			Map<String, String> paramsMap);

	/**
	 * 根据用户Id查询用户权限
	 * 
	 * @param paramsMap
	 * @return List
	 */
	public List<MenuEntity> loadUserAuthorities(Map<String, String> paramsMap);

	/**
	 * 根据查询条件查询多个用户
	 * 
	 * @param paramsMap
	 * @return List
	 */
	public List<UserEntity> queryList(Map<String, Object> paramsMap);

	/**
	 * 按条件查询不分页列表
	 * 
	 * @param entity
	 * @return List
	 */
	public List<UserEntity> queryAllObj(UserEntity entity);

	public void saveList(List<UserEntity> userList);
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
}
