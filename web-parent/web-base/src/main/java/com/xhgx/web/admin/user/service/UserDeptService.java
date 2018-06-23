package com.xhgx.web.admin.user.service;

import com.xhgx.sdk.service.BaseService;

/**
 * @ClassName UserDeptService
 * @Description 用户表service接口
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public interface UserDeptService extends BaseService {

	/**
	 * 用户部门赋权限 重新绑定关系
	 * 
	 * @param userId
	 * @param deptIds
	 */
	public void updateUserDept(String userId, String[] deptIds);

	/**
	 * 根据部门ID删除用户部门中间表关联的数据
	 * 
	 * @param deptId
	 */
	public void deleteUserDeptByDeptId(String deptId);

	/**
	 * 根据用户ID获得部门ID
	 * 
	 * @param userId
	 * @return String
	 */
	public String findDeptIdByUserId(String userId);

}
