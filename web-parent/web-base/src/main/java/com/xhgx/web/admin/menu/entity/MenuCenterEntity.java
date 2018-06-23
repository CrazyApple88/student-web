package com.xhgx.web.admin.menu.entity;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName MenuCenterEntity
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
@AutoUUID
public class MenuCenterEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	private String id;
	/**用户ID*/
	private String menuId; 
	/**关联表ID*/
	private String relationId;  
	/**关联表类型：1用户，2角色，3部门*/
	private String relationType;  
	/**权限类型：1访问类型，2授权类型*/
	private String authType;  

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

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

}
