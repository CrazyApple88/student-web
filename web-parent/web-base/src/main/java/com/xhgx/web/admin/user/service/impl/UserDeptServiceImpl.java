package com.xhgx.web.admin.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.user.dao.UserDeptDao;
import com.xhgx.web.admin.user.entity.UserDeptEntity;
import com.xhgx.web.admin.user.service.UserDeptService;

/**
 * @ClassName UserDeptServiceImpl
 * @Description 用户表serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("userDeptService")
public class UserDeptServiceImpl extends BatisBaseServiceImpl implements
		UserDeptService {

	@Override
	public GenericDao<UserDeptEntity, String> getGenericDao() {
		return userDeptDao;
	}

	@Autowired
	public UserDeptDao userDeptDao;

	/**
	 * 用户部门赋权限
	 * 
	 * @param userId
	 * @param deptIds
	 */
	public void updateUserDept(String userId, String[] deptIds) {
		if (deptIds == null) {
			// 修改用户部门配置，1》根据用户ID删除之前的关系，
			userDeptDao.deleteUserDeptByUserId(userId);
		} else {
			List<UserDeptEntity> userDeptList = new ArrayList<UserDeptEntity>();
			for (String deptId : deptIds) {
				if (StringUtils.isBlank(deptId)) {
					continue;
				}
				UserDeptEntity userDept = new UserDeptEntity();
				userDept.setUserId(userId);
				userDept.setDeptId(deptId);
				userDeptList.add(userDept);
			}
			// 修改用户部门配置，1》根据用户ID删除之前的关系，
			userDeptDao.deleteUserDeptByUserId(userId);
			// 2》重新创建用户部门关系
			// userDeptDao.saveUserDeptBatch(userDeptList);//oracle不支持批量插入
			for (UserDeptEntity userDeptEntity : userDeptList) {
				userDeptDao.save(userDeptEntity);
			}
		}
	}

	/**
	 * 根据部门ID删除用户部门中间表关联的数据
	 * 
	 * @param deptId
	 */
	public void deleteUserDeptByDeptId(String deptId) {
		userDeptDao.deleteUserDeptByDeptId(deptId);
	}

	/**
	 * 根据用户ID获得部门ID
	 * 
	 * @param userId
	 */
	public String findDeptIdByUserId(String userId) {
		return userDeptDao.findDeptIdByUserId(userId);
	}

}
