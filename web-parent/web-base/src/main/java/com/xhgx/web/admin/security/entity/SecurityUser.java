package com.xhgx.web.admin.security.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.xhgx.web.admin.user.entity.UserEntity;

/**
 * @ClassName SecurityUser
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-04 17:01
 * @vresion 1.0
 */
public class SecurityUser extends UserEntity implements UserDetails {

	private UserEntity userEntity;

	/**用户权限*/
	private List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();

	public SecurityUser(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorityList;
	}

	@Override
	public String getUsername() {
		return userEntity.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// 删除标记,提示【您的账号已删除,无法登录!】 1正常 2已删除
		return userEntity.getIsDel() == 2 ? false : true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 是否可登录,提示【您的账号已被禁用,无法登录!】 1 都正常 2:都不能登录登录，3网站能登录APP不能，4APP能登陆网站不能登录
		return userEntity.getLoginStatus() == 1 ? true : false;
	}

	public String getAddress() {
		return userEntity.getAddress();
	}

	public String getCompId() {
		return userEntity.getCompId();
	}

	public String getCreateBy() {
		return userEntity.getCreateBy();
	}

	public Date getCreateDate() {
		return userEntity.getCreateDate();
	}

	public String getEmail() {
		return userEntity.getEmail();
	}

	public String getEmpNo() {
		return userEntity.getEmpNo();
	}

	public String getId() {
		return userEntity.getId();
	}

	public String getIdCard() {
		return userEntity.getIdCard();
	}

	public int getIsDel() {
		return userEntity.getIsDel();
	}

	public int getLoginStatus() {
		return userEntity.getLoginStatus();
	}

	public String getMobile() {
		return userEntity.getMobile();
	}

	public String getPassword() {
		return userEntity.getPassword();
	}

	public String getPhone() {
		return userEntity.getPhone();
	}

	public String getRealName() {
		return userEntity.getRealName();
	}

	public String getSalt() {
		return userEntity.getSalt();
	}

	public String getUserName() {
		return userEntity.getUserName();
	}

	public String getUserPhoto() {
		return userEntity.getUserPhoto();
	}

	public String getUserType() {
		return userEntity.getUserType();
	}

	public void setGrantedAuthorityList(
			List<GrantedAuthority> grantedAuthorityList) {
		this.grantedAuthorityList = grantedAuthorityList;
	}
}
