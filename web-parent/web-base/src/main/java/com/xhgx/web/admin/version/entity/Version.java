package com.xhgx.web.admin.version.entity;

import javax.validation.constraints.NotNull;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName Version
 * @Description tb_sys_version实体信息
 * @author <font color='blue'>zhangjin</font>
 * @date 2017-06-12 15:22:03
 * @version 1.0
 * 
 */
@AutoUUID
public class Version extends BaseEntity<String> {

	/** serialVersionUID */

	private static final long serialVersionUID = 4841799362659062357L;
	/**id*/
	private java.lang.String id;

	/**name*/
	@NotNull
	private java.lang.String name;

	/**code*/
	@NotNull
	private java.lang.String code;

	/**update_info*/
	private java.lang.String updateInfo;

	/**update_by*/
	private java.lang.String updateBy;

	/**update_date*/
	private java.util.Date updateDate;

	/**remark*/
	private java.lang.String remark;

	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * <b>方法名</b>：getId<br>
	 * <b>功能</b>：取得id<br>
	 * 
	 * @author <font color='blue'>zhangjin</font>
	 * @date 2017-06-12 15:22:03
	 * @return
	 */
	public java.lang.String getId() {
		return this.id;
	}

	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getCode() {
		return this.code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getUpdateInfo() {
		return this.updateInfo;
	}

	public void setUpdateInfo(java.lang.String updateInfo) {
		this.updateInfo = updateInfo;
	}

	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}

	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

}
