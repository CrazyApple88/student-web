package com.xhgx.web.admin.security.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xhgx.commons.lang.NetWorkUtils;
import com.xhgx.web.admin.config.service.ConfigHelper;
import com.xhgx.web.admin.dept.entity.DeptEntity;
import com.xhgx.web.admin.dept.service.DeptService;
import com.xhgx.web.admin.log.entity.LoginLogEntity;
import com.xhgx.web.admin.log.service.LoginLogService;
import com.xhgx.web.admin.menu.entity.MenuEntity;
import com.xhgx.web.admin.security.entity.SecurityUser;
import com.xhgx.web.admin.security.service.LoginService;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.admin.user.service.UserService;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName LoginServiceImpl
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 15:38
 * @vresion 1.0
 */
@Component("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	@Qualifier("userService")
	UserService userService;

	@Autowired
	@Qualifier("loginLogService")
	LoginLogService loginLogService;

	@Autowired
	private DeptService deptService;

	/**
	 * <p>用户访问应用资源之前,将会调用此方法获取用户的登录信息及对应的权限范围(ROLE_USER,ROLE_ADMIN,ROLE_LOGIN)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		UserEntity user = userService.queryByUserNameAndComp(userName, null);
		if (user == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		LoginLogEntity entity = new LoginLogEntity();
		entity.setLoginDate(new Date());
		entity.setUserName(user.getUserName());
		entity.setLoginType("1");
		entity.setLoginStatus("1");
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		entity.setLoginIp(NetWorkUtils.getRemortIP(servletRequestAttributes
				.getRequest()));
		// TODO 暂时可以在这里处理，量上来后，考虑异步处理
		loginLogService.save(entity);
		SecurityUser securityUser = new SecurityUser(user);
		// 根据用户Id查询用户权限
		Map<String, String> paramsMap = new HashMap<String, String>();
		// 超级个管理员账号
		String superAdmin = ConfigHelper.getInstance().get("super.administrator");
		if (superAdmin.equals(userName)) {
			paramsMap.put("userId", "");
		} else {
			// 登录用户的ID
			paramsMap.put("userId", user.getId());
			// 访问菜单的权限
			paramsMap.put("authType", "1");
		}
		// 查询所有的包括所有菜单，按钮权限
		List<MenuEntity> listMenuAll = userService.loadUserAuthorities(paramsMap);
		// 匹配当前用户所拥有的权限
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		Map<String, MenuEntity> map = new HashMap<String, MenuEntity>();
		List<MenuEntity> listMenu = new ArrayList<MenuEntity>();
		for (MenuEntity menu : listMenuAll) {
			if ("99".equals(menu.getMenuType())) {
				map.put(menu.getAuthTab(), menu);
				continue;
			} else {
				listMenu.add(menu);
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
						"ROLE_" + menu.getAuthTab());
				auths.add(grantedAuthority);
			}

		}

		Map<String, Object> paramsDept = new HashMap<String, Object>();
		paramsDept.put("byUserId", user.getId());
		List<DeptEntity> listDept = deptService.queryList(paramsDept);
		securityUser.setListDept(listDept);
		securityUser.setGrantedAuthorityList(auths);
		// 将当前用户所拥有的菜单放入session的map中
		securityUser.addExt("listMenu", listMenu);
		// 将当前用户所拥有的菜单放入session的map中
		securityUser.addExt("listMenuMap", map);
		// TODO 这里需要初始化用户的权限信息
		return securityUser;
	}

	public void logout(OnlineUser user) {
		UserEntity us = (UserEntity) user;
		LoginLogEntity entity = new LoginLogEntity();
		entity.setLoginDate(new Date());
		entity.setUserName(us.getUserName());
		entity.setLoginType("2");
		entity.setLoginStatus("1");
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		entity.setLoginIp(NetWorkUtils.getRemortIP(servletRequestAttributes
				.getRequest()));
		// TODO 暂时可以在这里处理，量上来后，考虑异步处理
		loginLogService.save(entity);

	}

	@Override
	public String gerString(HttpServletRequest request, String param) {
		return request.getServletContext().getInitParameter(param);
	}
	
	@Override
	public String getUserNameByCasServer() {
		String username = "";
		Assertion assertion = AssertionHolder.getAssertion();
		if (assertion != null) {
			AttributePrincipal attributePrincipal = assertion.getPrincipal();
			if (attributePrincipal != null) {
				username = attributePrincipal.getName();
			}
		}
		return username;
	}	

}
