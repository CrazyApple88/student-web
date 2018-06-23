package com.xhgx.web.admin.role.entity;

import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName 
 * @Description 角色菜单中间表实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public class RoleMenuEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	private String id;
	/**角色ID*/
	private String roleId; 
	/**用户ID*/
	private String menuId; 
	/**类型：1菜单，2权限；*/
	private int powerType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int getPowerType() {
		return powerType;
	}

	public void setPowerType(int powerType) {
		this.powerType = powerType;
	}

}
