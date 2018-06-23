package com.xhgx.web.admin.user.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName UserEntity
 * @Description 用户表实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@AutoUUID
public class UserEntity extends OnlineUser {

	private static final long serialVersionUID = 1L;
	private String id;
	/**企业ID*/
	private String compId;
	/**登录名*/
	@Pattern(regexp = "^\\w{1,32}$", message = "用户名必须由字母数字下划线组成")
	private String userName;
	/**登录密码MD5*/
	private String password;
	/**盐（密码安全性字段）*/
	private String salt;
	/**真实姓名*/
	@Length(min = 0, max = 32, message = "真实姓名长度不能超过32")
	private String realName;
	/**身份证*/
	/**@Pattern(regexp = "^[0-9xX]{15,18}$", message = "身份证必须是15-18位数字或xX组成")*/
	@Length(min = 0, max = 32, message = "身份证长度不能超过32")
	private String idCard;
	/**工号*/
	@Length(min = 0, max = 32, message = "工号长度不能超过32")
	private String empNo;
	/**email*/
	@Length(min = 0, max = 64, message = "email长度不能超过64")
	// @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",
	/**message = "email格式不对")*/
	private String email;
	/**电话*/
	@Length(min = 0, max = 32, message = "电话长度不能超过32")
	private String phone;
	/**手机*/
	@Length(min = 0, max = 32, message = "手机长度不能超过32")
	private String mobile;
	/**联系地址*/
	@Length(min = 0, max = 255, message = "联系地址长度不能超过255")
	private String address;
	/**用户类型*/
	private String userType;
	/**用户头像sys.fileId*/
	private String userPhoto;
	/**是否可登录：1 都正常 2:都不能登录登录，3网站能登录APP不能，4APP能登陆网站不能登录*/
	@NotNull
	private int loginStatus;
	/**删除标记 1正常 2已删除*/
	private int isDel;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public int getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
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

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String getLoginName() {
		return this.getUserName();
	}

	@Override
	public String getName() {
		return this.getRealName();
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
}
