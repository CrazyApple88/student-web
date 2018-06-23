package com.xhgx.web.admin.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.user.dao.UserRoleDao;
import com.xhgx.web.admin.user.entity.UserRoleEntity;
import com.xhgx.web.admin.user.service.UserRoleService;

/**
 * @ClassName UserRoleServiceImpl
 * @Description 用户表serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends BatisBaseServiceImpl implements
		UserRoleService {

	@Override
	public GenericDao<UserRoleEntity, String> getGenericDao() {
		return userRoleDao;
	}

	@Autowired
	public UserRoleDao userRoleDao;

	/**
	 * 用户角色赋权限
	 * 
	 * @param userId
	 * @param roleIds
	 */
	public void updateUserRole(String userId, String[] roleIds) {
		if (roleIds == null) {
			// 修改用户角色配置，1》根据用户ID删除之前的关系，
			userRoleDao.delUserRoleByUserId(userId);
		} else {
			List<UserRoleEntity> userRoleList = new ArrayList<UserRoleEntity>();
			for (String roleId : roleIds) {
				if (StringUtils.isBlank(roleId)) {
					continue;
				}
				UserRoleEntity userRole = new UserRoleEntity();
				userRole.setUserId(userId);
				userRole.setRoleId(roleId);
				userRoleList.add(userRole);
			}
			// 修改用户角色配置，1》根据用户ID删除之前的关系，
			userRoleDao.delUserRoleByUserId(userId);
			// 2》重新创建用户角色关系
			// userRoleDao.saveUserRoleBatch(userRoleList);
			for (UserRoleEntity userRoleEntity : userRoleList) {
				userRoleDao.save(userRoleEntity);
			}
		}
	}
	/**
	 * 角色分配用户赋权限 重新绑定关系
	 * 
	 * @param userId
	 * @param roleIds
	 */
	public void updateRoleUser(String[] userIds, String roleId){
		if (userIds == null) {
			// 修改用户角色配置，1》根据角色ID删除之前的关系，
			userRoleDao.delUserRoleByRoleId(roleId);
		} else {
			// 修改用户角色配置，1》根据角色ID删除之前的关系，
			userRoleDao.delUserRoleByRoleId(roleId);

			// 2》重新创建用户角色关系
			// userRoleDao.saveUserRoleBatch(userRoleList);
			for (String userId : userIds) {
				if (StringUtils.isBlank(userId)) {
					continue;
				}
				UserRoleEntity userRole = new UserRoleEntity();
				userRole.setUserId(userId);
				userRole.setRoleId(roleId);
				userRoleDao.save(userRole);
			}
		}
	}

	/**
	 * 根据角色ID删除用户角色中间表关联的数据
	 * 
	 * @param roleId
	 */
	public void delUserRoleByRoleId(String roleId) {
		userRoleDao.delUserRoleByRoleId(roleId);
	}

}
