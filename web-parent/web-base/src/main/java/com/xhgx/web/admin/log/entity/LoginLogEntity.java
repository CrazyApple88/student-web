package com.xhgx.web.admin.log.entity;

import java.util.Date;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName 
 * @Description 登录日志表实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@AutoUUID
public class LoginLogEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	private String id;
	/**用户名称*/
	private String userName;  
	/**登陆IP*/
	private String loginIp; 
	/**登陆时间*/
	private Date loginDate;  
	/**登录类型：1，登入系统，2退出系统，3其他*/
	private String loginType;  
	/**状态：1成功，2失败，3密码错误，4用户不可用，5其他*/
	private String loginStatus;  
	/**设备类型*/
	private String equipment; 
	/**IP所在地址*/
	private String ipAddr;  

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

}
