package com.xhgx.web.admin.user.dao;

import java.util.List;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.web.admin.user.entity.UserDeptEntity;

/**
 * @ClassName UserDeptDao
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */

public interface UserDeptDao extends GenericDao<UserDeptEntity, String> {

	public boolean updateUserDeptAdd(UserDeptEntity userDept);

	public void deleteUserDeptByUserId(String userId);

	public void deleteUserDeptByDeptId(String deptId);

	public void saveUserDeptBatch(List<UserDeptEntity> list);

	public String findDeptIdByUserId(String userId);
}
