package com.xhgx.web.app.appLogin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xhgx.commons.lang.NetWorkUtils;
import com.xhgx.web.admin.config.service.ConfigHelper;
import com.xhgx.web.admin.log.entity.LoginLogEntity;
import com.xhgx.web.admin.log.service.LoginLogService;
import com.xhgx.web.admin.menu.entity.MenuEntity;
import com.xhgx.web.admin.security.entity.SecurityUser;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.admin.user.service.UserService;
import com.xhgx.web.app.appLogin.service.AppLoginService;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName AppLoginServiceImpl
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 15:38
 * @vresion 1.0
 */
@Component("appLoginService")
public class AppLoginServiceImpl implements AppLoginService {

	@Autowired
	@Qualifier("userService")
	UserService userService;

	@Autowired
	@Qualifier("loginLogService")
	LoginLogService loginLogService;

	/**
	 * <p>用户访问应用资源之前,将会调用此方法获取用户的登录信息及对应的权限范围(ROLE_USER,ROLE_ADMIN,ROLE_LOGIN)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	public Map<String, Object> loadAppUserInfo(String userName) {
		Map<String, Object> appUserInfo = new HashMap<String, Object>();
		UserEntity user = userService.queryByUserNameAndComp(userName, null);

		LoginLogEntity entity = new LoginLogEntity();
		entity.setLoginDate(new Date());
		entity.setUserName(user.getUserName());
		entity.setLoginType("1");
		entity.setLoginStatus("1");
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		entity.setLoginIp(NetWorkUtils.getRemortIP(servletRequestAttributes
				.getRequest()));
		// 暂时可以在这里处理，量上来后，考虑异步处理
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
		List<MenuEntity> listMenuAll = userService
				// 查询所有的包括所有菜单，按钮权限
				.loadUserAuthorities(paramsMap);
		// 匹配当前用户所拥有的权限
		Map<String, MenuEntity> map = new HashMap<String, MenuEntity>();
		List<MenuEntity> listMenu = new ArrayList<MenuEntity>();
		for (MenuEntity menu : listMenuAll) {
			if ("99".equals(menu.getMenuType())) {
				map.put(menu.getAuthTab(), menu);
				continue;
			} else {
				listMenu.add(menu);
			}

		}
		appUserInfo.put("user", user);
		// 将当前用户所拥有的菜单放入session的map中
		appUserInfo.put("listMenu", listMenu);
		// 将当前用户所拥有的菜单放入session的map中
		appUserInfo.put("listMenuMap", map);

		return appUserInfo;
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
		// 暂时可以在这里处理，量上来后，考虑异步处理
		loginLogService.save(entity);

	}

}
