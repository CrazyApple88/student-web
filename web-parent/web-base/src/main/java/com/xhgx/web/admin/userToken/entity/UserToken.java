package com.xhgx.web.admin.userToken.entity;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName UserToken
 * @Description tb_sys_user_token实体信息
 * @author <font color='blue'>libo</font>
 * @date 2017-06-26 18:06:06
 * @version 1.0
 */
@AutoUUID
public class UserToken extends BaseEntity<String> {

	/** serialVersionUID */

	private static final long serialVersionUID = 4841799362659062357L;

	/**id*/
	private java.lang.String id;
	/**user_id*/
	private java.lang.String userId;
	/**token*/
	private java.lang.String token;
	/**active_time*/
	private java.util.Date activeTime;
	/**last_login_time*/
	private java.util.Date lastLoginTime;
	/**device_id*/
	private java.lang.String deviceId;
	/**app*/
	private java.lang.String app;
	/**remark*/
	private java.lang.String remark;

	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * <b>方法名</b>：getId<br>
	 * <b>功能</b>：取得id<br>
	 * 
	 * @author <font color='blue'>libo</font>
	 * @date 2017-06-26 18:06:06
	 * @return
	 */
	public java.lang.String getId() {
		return this.id;
	}

	public java.lang.String getUserId() {
		return this.userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public java.lang.String getToken() {
		return this.token;
	}

	public void setToken(java.lang.String token) {
		this.token = token;
	}

	public java.util.Date getActiveTime() {
		return this.activeTime;
	}

	public void setActiveTime(java.util.Date activeTime) {
		this.activeTime = activeTime;
	}

	public java.util.Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public java.lang.String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(java.lang.String deviceId) {
		this.deviceId = deviceId;
	}

	public java.lang.String getApp() {
		return this.app;
	}

	public void setApp(java.lang.String app) {
		this.app = app;
	}

	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

}
