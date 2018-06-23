package com.xhgx.web.admin.user.entity;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName UserRoleEntity
 * @Description 用户角色中间表实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@AutoUUID
public class UserRoleEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	private String id;
	/**用户ID*/
	private String userId;
	/**角色ID*/
	private String roleId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
