package com.xhgx.web.admin.user.service;

import com.xhgx.sdk.service.BaseService;

/**
 * @ClassName UserRoleService
 * @Description 用户表service接口
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public interface UserRoleService extends BaseService {

	/**
	 * 用户角色赋权限 重新绑定关系
	 * 
	 * @param userId
	 * @param roleIds
	 */
	public void updateUserRole(String userId, String[] roleIds);
	
	/**
	 * 角色分配用户赋权限 重新绑定关系
	 * 
	 * @param userId
	 * @param roleIds
	 */
	public void updateRoleUser(String[] userId, String roleId);

	/**
	 * 根据角色ID删除用户角色中间表关联的数据
	 * 
	 * @param roleId
	 */
	public void delUserRoleByRoleId(String roleId);

}
