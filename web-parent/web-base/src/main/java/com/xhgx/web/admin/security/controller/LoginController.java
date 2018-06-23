package com.xhgx.web.admin.security.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xhgx.web.admin.manager.service.ManagerService;
import com.xhgx.web.admin.menu.entity.MenuEntity;
import com.xhgx.web.admin.message.service.MessageService;
import com.xhgx.web.admin.security.VerifyException;
import com.xhgx.web.admin.security.service.LoginService;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName LoginController
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 15:28
 * @vresion 1.0
 */
@Controller
@RequestMapping("/security")
@Scope("prototype")
public class LoginController extends AbstractBaseController {
	/**Spring*/
	public static final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";
	/**security*/																						
    /**最后登录异常Session名称*/
	@Autowired
	@Qualifier("loginService")
	private LoginService loginService;

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ManagerService managerService;
	/**
	 * 登录后要跳转的页面， 可以根据用户不同的类型，跳转到不同的页面
	 *
	 * @return String
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		OnlineUser user = (OnlineUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		this.getRequest().getSession()
				.setAttribute(OnlineUser.ONLINE_USER_TAG, user);
		//获取当前用户所有未读消息的总数
		int count=0;

		//TODO 这里屏蔽掉，以后采用异步的方式获取
		//count=messageService.countUnMsgByUserId(user.getId());
		// TODO 这里要根据不同的用户类型、或者用户的自定义的首页信息进入
		request.setAttribute("count", count);
		return "system/index";
	}

	/**
	 * 进入默认首页
	 * 
	 * @return String
	 */
	@RequestMapping("/getDefaultpage")
	public String getDefaultpage() {
		return "system/default";
	}

	/**
	 * 登录失败，跳转到登录页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "login")
	public String login(HttpServletRequest request) {
		String tip = null;
		Exception springSecurityLastException = (Exception) request
				.getSession().getAttribute(SPRING_SECURITY_LAST_EXCEPTION);
		if (springSecurityLastException != null) {
			if (springSecurityLastException instanceof BadCredentialsException) {		
				tip = getMessage("common.text.username.wrong","您的用户名或密码错误!");
			} else if (springSecurityLastException instanceof DisabledException) {
				tip = getMessage("common.text.disabled.unable.login","您的账号已被禁用,无法登录!");
			} else if (springSecurityLastException instanceof LockedException) {
				tip = getMessage("common.text.locked.unable.login","您的账号已被锁定,无法登录!");
			} else if (springSecurityLastException instanceof AccountExpiredException) {
				tip = getMessage("common.text.deleted.by.administrator.unable.login","您的账号已被管理员删除,无法登录!");
			} else if (springSecurityLastException instanceof VerifyException) {
				tip = springSecurityLastException.getMessage();
			} else {
				springSecurityLastException.printStackTrace();
				tip = getMessage("common.text.unknown.error.unable.login","出现未知错误,无法登录!");
			}
			
			if("验证码错误".equals(tip)){
				tip= getMessage("common.text.verification.code.error","验证码错误");
			}
			request.getSession()
					.removeAttribute(SPRING_SECURITY_LAST_EXCEPTION);
			this.getRequest().getSession().setAttribute("tip", tip);
		}
		return "admin/security/login";
	}

	@RequestMapping(value = "check")
	public String check(HttpServletRequest request) {
		return "admin/security/login";
	}

	@RequestMapping(value = "errorPage")
	public String errorPage(HttpServletRequest request) {
		return "system/error-500";
	}
	/**
	 * 根据用户ID查询用户菜单列表
	 * 
	 * @param userNow
	 * @param bindingResult
	 */
	@RequestMapping("/getMenusByUserId")
	public void getMenusByUserId(UserEntity userNow, BindingResult bindingResult) {
		OnlineUser user = this.getCurrentUser();
		List<MenuEntity> list = (List<MenuEntity>) user.getExt("listMenu");
		
		List<MenuEntity>  listCopy = new ArrayList<MenuEntity>(list.size());
		for(MenuEntity entity:list){
			MenuEntity copy = new MenuEntity();
			BeanUtils.copyProperties(entity, copy); 
			copy.setMenuName(this.getMessage(entity.getLanguageCode(), entity.getMenuName()));
			//根据menu_type的值设置菜单的类型为top,menu,link
			if("0".equals(copy.getMenuType())){
				copy.setType("top");
			}else if("1".equals(copy.getMenuType())){
				copy.setType("menu");
			}else if("2".equals(copy.getMenuType())){
				copy.setType("menu");
			}else if("3".equals(copy.getMenuType())){
				copy.setType("link");
			}
			listCopy.add(copy);
		}		
		successJson(listCopy);
	}

	/**
	 * 用户推出操作
	 */
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request) {
		OnlineUser user = this.getCurrentUser();
		if (user != null) {
			loginService.logout(user);
			// 清除session用户信息
			this.getRequest().getSession()
					.removeAttribute(OnlineUser.ONLINE_USER_TAG);
		}
		this.getRequest().getSession().invalidate();
		String requestType = request.getHeader("X-Requested-With");
		if (StringUtils.equalsIgnoreCase("XMLHttpRequest", requestType)
				|| isMoblie()) {
			this.successJson(getMessage("system.management.text.log.out.successfully","登出成功"));
			return null;
		} else {
			return "redirect:/login ";
		}

	}

	@RequestMapping("menuTemplate")
	public String menuTemplate(String type) {
		String url = null;
		if("allShowMenu".equals(type)){
			url = "system/template_allShowMenu";
		}else if("divideMenu".equals(type)){
			url = "system/template_divideMenu";
		}else if("showStairMenu".equals(type)){
			url = "system/template_showStairMenu";
		}else{
			url = "system/template_flexMenu";
		}
		return url;
	}
}
