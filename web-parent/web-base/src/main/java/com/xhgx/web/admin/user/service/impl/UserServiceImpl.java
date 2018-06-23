package com.xhgx.web.admin.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.menu.entity.MenuEntity;
import com.xhgx.web.admin.user.dao.UserDao;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.admin.user.service.UserService;

/**
 * @ClassName UserServiceImpl
 * @Description 用户表serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("userService")
public class UserServiceImpl extends BatisBaseServiceImpl implements
		UserService {

	@Override
	public GenericDao<UserEntity, String> getGenericDao() {
		return userDao;
	}

	@Autowired
	public UserDao userDao;

	public void deleteBatch(String[] ids) {
		userDao.deleteBatch(ids);
	}

	public void update(UserEntity user) {
		userDao.update(user);

	}

	public void save(UserEntity user) {
		userDao.save(user);

	}

	public void updatePasswordById(UserEntity user) {
		userDao.updatePasswordById(user);
	}

	/**
	 * 根据用户名和企业id获取用户信息
	 * 
	 * @param userName
	 * @param compId
	 * @return UserEntity
	 */
	public UserEntity queryByUserNameAndComp(String userName, String compId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userName", userName);
		param.put("compId", compId);
		List<UserEntity> list = userDao.queryByUserNameAndComp(param);
		if (list == null || list.size() <= 0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 获取企业下的所有符合条件的角色， 同时根据用户所选的角色，进行LEFT JOIN 查询出哪个是用户已经选择的角色
	 * 
	 * @param paramsMap
	 * @return List
	 */
	public List<HashMap> getRolesLeftJoinUserChecked(
			Map<String, String> paramsMap) {

		return userDao.getRolesLeftJoinUserChecked(paramsMap);

	}

	/**
	 * 获取企业下的所有符合条件的部门， 同时根据用户所选的部门，进行LEFT JOIN 查询出哪个是用户已经选择的部门
	 * 
	 * @param paramsMap
	 * @return List
	 */
	public List<HashMap> getDeptsLeftJoinUserChecked(
			Map<String, String> paramsMap) {

		return userDao.getDeptsLeftJoinUserChecked(paramsMap);

	}

	/**
	 * 根据用户Id查询用户权限
	 * 
	 * @param paramsMap
	 * @return List
	 */
	public List<MenuEntity> loadUserAuthorities(Map<String, String> paramsMap) {
		return userDao.getMenuByUserId(paramsMap);
	}

	public List<UserEntity> queryList(Map<String, Object> paramsMap) {
		return userDao.queryList(paramsMap);
	}

	@Override
	public List<UserEntity> queryAllObj(UserEntity entity) {
		return userDao.queryAllObj(entity);
	}

	public void saveList(List<UserEntity> userList) {
		for (UserEntity userEntity : userList) {
			userDao.save(userEntity);
		}
	}

	@Override
	public List<HashMap> getUsersByCompNotDept(Map<String, String> paramsMap) {
		return userDao.getUsersByCompNotDept(paramsMap);
	}

	@Override
	public List<HashMap> getUserByCompInDept(Map<String, String> paramsMap) {
		return userDao.getUserByCompInDept(paramsMap);
	}

}
