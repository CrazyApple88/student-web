package com.xhgx.web.app.appLogin.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xhgx.commons.lang.MD5Utils;
import com.xhgx.sdk.cache.SimpleCacheHelper;
import com.xhgx.sdk.token.TokenUtils;
import com.xhgx.web.admin.menu.entity.MenuEntity;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.admin.user.service.UserService;
import com.xhgx.web.admin.userToken.entity.UserToken;
import com.xhgx.web.admin.userToken.service.UserTokenService;
import com.xhgx.web.app.appLogin.service.AppLoginService;
import com.xhgx.web.base.controller.AbstractAppController;

/**
 * @ClassName AppLoginController
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 15:28
 * @vresion 1.0
 */
@Controller
@RequestMapping("/app")
@Scope("prototype")
public class AppLoginController extends AbstractAppController {

	/**最后登录异常Session名称*/
	@Autowired
	@Qualifier("appLoginService")
	private AppLoginService appLoginService;

	@Autowired
	public UserService userService;

	@Autowired
	private UserTokenService userTokenService;

	@Autowired
	private TokenUtils tokenUtils;

	/**
	 * app端登录
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/login")
	public void login(UserEntity user, BindingResult bindingResult) {

		UserEntity queryUser = new UserEntity();
		queryUser.setUserName(user.getLoginName());

		UserEntity loginUser = (UserEntity) userService.get(queryUser);

		// 用户名不存在
		if (loginUser == null) {
			successJson("用户名或密码不正确！");
			return;
		}

		String salt = loginUser.getSalt();
		queryUser.setPassword(MD5Utils.getMD5String(user.getPassword() + salt));

		// 密码不正确
		if (null != loginUser
				&& !loginUser.getPassword().equals(queryUser.getPassword())) {
			successJson("用户名或密码不正确！");
			return;
		}

		// 生成token
		String token = TokenUtils.generateToken(loginUser.getId());

		// 持久化
		UserToken userToken = new UserToken();
		userToken.setUserId(loginUser.getId());
		userToken.setToken(token);
		// userToken.setActiveTime();
		userToken.setLastLoginTime(new Date());
		userTokenService.save(userToken);

		// SimpleCacheHelper缓存
		Map<String, Object> appUserInfo = appLoginService
				.loadAppUserInfo(loginUser.getLoginName());
		SimpleCacheHelper.put(token, appUserInfo);

		successJson(token);
	}

	/**
	 * 根据token查询用户菜单列表
	 * 
	 * @param menu
	 * @param bindingResult
	 */
	@RequestMapping("/getMenusByToken")
	public void getMenusByToken(String token, BindingResult bindingResult) {
		Map<String, Object> appUserInfo = (Map<String, Object>) SimpleCacheHelper
				.get(token);
		// 验证
		if (appUserInfo == null) {
			successJson("非法请求！请重新登录！");
			return;
		}
		List<MenuEntity> list = (List<MenuEntity>) appUserInfo.get("listMenu");
		successJson(list);
	}

	/**
	 * 根据token查询用户详细信息
	 * 
	 * @param menu
	 * @param bindingResult
	 */
	@RequestMapping("/getUserByToken")
	public void getUserByToken(String token, BindingResult bindingResult) {
		Map<String, Object> appUserInfo = (Map<String, Object>) SimpleCacheHelper
				.get(token);
		// 验证
		if (appUserInfo == null) {
			successJson("非法请求！请重新登录！");
			return;
		}
		UserEntity user = (UserEntity) appUserInfo.get("user");
		successJson(user);
	}

	/**
	 * 根据token查询用户按钮列表
	 * 
	 * @param menu
	 * @param bindingResult
	 */
	@RequestMapping("/getMenuMapsByToken")
	public void getMenuMapsByToken(String token, BindingResult bindingResult) {
		Map<String, Object> appUserInfo = (Map<String, Object>) SimpleCacheHelper
				.get(token);
		// 验证
		if (appUserInfo == null) {
			successJson("非法请求！请重新登录！");
			return;
		}
		Map<String, MenuEntity> map = (Map<String, MenuEntity>) appUserInfo
				.get("listMenuMap");
		successJson(map);
	}

	/**
	 * 用户退出操作
	 */
	@RequestMapping(value = "/logout")
	public void logout(String token, BindingResult bindingResult) {
		Map<String, Object> appUserInfo = (Map<String, Object>) SimpleCacheHelper
				.get(token);
		// 验证
		if (appUserInfo == null) {
			successJson("非法请求！请重新登录！");
		}

		String username = (String) appUserInfo.get("userName");
		UserEntity user = new UserEntity();
		user.setUserName(username);
		appLoginService.logout(user);
		SimpleCacheHelper.remove(token);
		successJson("登出成功");
	}

}
