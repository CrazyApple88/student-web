package com.xhgx.web.admin.role.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName RoleEntity
 * @Description 角色表实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@AutoUUID
public class RoleEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	private String id;
	/**企业ID*/
	private String compId;
	/**角色名称*/
	@NotNull
	@Length(min = 1, max = 64, message = "角色名称长度必须在1-64之间")
	private String roleName;
	/**详细说明*/
	@Length(min = 0, max = 255, message = "详细说明长度不能超过255")
	private String intro;
	/**是否分配：1可分配，2不能分配',*/
	@NotNull
	private int useable;
	/**创建者*/
	private String createBy;
	/**创建时间*/
	private Date createDate;
	/**存放公司名称，（临时字段）*/
	private String compName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getUseable() {
		return useable;
	}

	public void setUseable(int useable) {
		this.useable = useable;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

}
