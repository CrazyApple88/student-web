package com.xhgx.web.admin.role.service;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.web.admin.role.entity.RoleEntity;

/**
 * @ClassName RoleService
 * @Description 角色表service接口
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public interface RoleService extends BaseService {

	/**
	 * 删除角色，需要删除角色表，用户角色中间表，角色菜单权限中间表
	 * 
	 * @param role
	 */
	public void deleteByRoleId(RoleEntity role);

}
