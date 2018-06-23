package com.xhgx.web.admin.role.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.dataRecover.service.DataRecoverService;
import com.xhgx.web.admin.menu.dao.MenuCenterDao;
import com.xhgx.web.admin.role.dao.RoleDao;
import com.xhgx.web.admin.role.entity.RoleEntity;
import com.xhgx.web.admin.role.service.RoleService;
import com.xhgx.web.admin.user.dao.UserRoleDao;

/**
 * @ClassName RoleServiceImpl
 * @Description 角色表serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("roleService")
public class RoleServiceImpl extends BatisBaseServiceImpl implements
		RoleService {

	@Override
	public GenericDao<RoleEntity, String> getGenericDao() {
		return roleDao;
	}

	@Autowired
	public RoleDao roleDao;

	@Autowired
	public UserRoleDao userRoleDao;

	@Autowired
	public MenuCenterDao menuCenterDao;

	@Autowired
	private DataRecoverService dataRecoverService;

	/**
	 * 删除角色，需要删除角色表，用户角色中间表，角色菜单权限中间表
	 * 
	 * @param role
	 */
	public void deleteByRoleId(RoleEntity role) {
		List<RoleEntity> listDel = new ArrayList<RoleEntity>();
		role = roleDao.get(role);
		listDel.add(role);
		// 第一步：删除角色表中角色信息tb_sys_role
		roleDao.delete(role);
		// 第二步：删除用户角色表中的信息tb_sys_user_role
		userRoleDao.delUserRoleByRoleId(role.getId());
		// 第三部：删除角色和菜单权限中间表信息tb_sys_menu_center
		Map<String, String> paramsMap = new HashMap<String, String>();
		// 中间关联表关联的ID
		paramsMap.put("relationId", role.getId());
		// 关联类型：1用户，2角色，3部门
		paramsMap.put("relationType", "2");
		// paramsMap.put("authType",
		// menuCenter.getAuthType());
		// 1访问权限，2授权权限//这个访问和授权权限都删除
		menuCenterDao.deleteMenuCentersByMenuCenter(paramsMap);

		// //删除数据做删除的恢复机制处理
		// String recoverId =
		// dataRecoverService.delToRecoverDB(listDel,"角色数据库保存","admin00");
		// //恢复机制是查询数据
		// List<RoleEntity> listRecover = (List<RoleEntity>)
		// dataRecoverService.recoverByIdDB(recoverId);
		// for (RoleEntity roleEntity : listRecover) {
		// System.out.println(roleEntity.getId()+"--"+roleEntity.getRoleName());
		// }

		// //删除数据做删除的恢复机制处理
		// String recoverId2 =
		// dataRecoverService.delToRecoverFile(listDel,"RoleEntity","角色文件保存","admin11");
		// //恢复机制是查询数据
		// List<RoleEntity> listRecover2 = (List<RoleEntity>)
		// dataRecoverService.recoverByIdFile(recoverId2,"RoleEntity");
		// for (RoleEntity roleEntity : listRecover2) {
		// System.out.println(roleEntity.getId()+"--"+roleEntity.getRoleName());
		// }
		// System.out.println("成功！！");
	}
}
