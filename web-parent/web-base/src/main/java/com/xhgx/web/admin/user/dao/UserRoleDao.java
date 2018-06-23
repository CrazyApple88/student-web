package com.xhgx.web.admin.user.dao;

import java.util.List;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.web.admin.user.entity.UserRoleEntity;

/**
 * @ClassName UserRoleDao
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */

public interface UserRoleDao extends GenericDao<UserRoleEntity, String> {

	public boolean updateUserRoleAdd(UserRoleEntity userRole);

	public void delUserRoleByUserId(String userId);

	public void delUserRoleByRoleId(String roleId);

	public void saveUserRoleBatch(List<UserRoleEntity> list);
}
