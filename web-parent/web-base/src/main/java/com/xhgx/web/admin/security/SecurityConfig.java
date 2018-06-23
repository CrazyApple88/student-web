package com.xhgx.web.admin.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.xhgx.web.admin.menu.entity.MenuEntity;
import com.xhgx.web.admin.menu.service.MenuService;
import com.xhgx.web.admin.user.entity.UserEntity;

/**
 * 
 * @ClassName SecurityConfig
 * @Description 系统安全配置 用于权限管理
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 15:21
 * @vresion 1.0
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("loginService")
	private UserDetailsService loginService;
	@Autowired
	@Qualifier("logoutHandlerImpl")
	private LogoutHandler logoutHandler;
	@Autowired
	@Qualifier("menuService")
	private MenuService menuService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(loginService);

		DaoAuthenticationProvider ap = new CustomAuthenticationProvider();
		ap.setUserDetailsService(loginService);

		ap.setSaltSource(new SaltSource() {
			@Override
			public Object getSalt(UserDetails user) {
				if (user instanceof UserEntity) {
					UserEntity u = (UserEntity) user;
					return u.getSalt();
				}
				return null;
			}
		});
		ap.setPasswordEncoder(new Md5SaltPasswordEncoder());
		auth.authenticationProvider(ap);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// menuService = (IMenuService)
		// SpringContextUtil.getBean("menuService");
		// TODO 这里初始化 需要 验证的地址和，排除不需要验证的地址
		/*
		 * List<Menu> list = menuService.getMenuList(); for (Menu menu : list) {
		 * if (StringUtil.isNotEmpty(menu.getPattern()) &&
		 * StringUtils.isNotEmpty(menu.getPermissionName())) {
		 * http.authorizeRequests().antMatchers(menu.getPattern())
		 * .hasRole(menu.getPermissionName()).and(); } }
		 */
		Map<String, String> param = new HashMap<String, String>();
		// 查询菜单列表
		List<MenuEntity> list = menuService.getMenuList(param);
		for (MenuEntity menu : list) {
			if (StringUtils.isNotEmpty(menu.getUrl())
					&& StringUtils.isNotEmpty(menu.getAuthTab())) {
				http.authorizeRequests().antMatchers(menu.getUrl())
						.hasRole(menu.getAuthTab()).and();
			}
		}

		http.authorizeRequests()
				.antMatchers("/assets/**").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginProcessingUrl("/security/check")
				.loginPage("/security/login")
				.defaultSuccessUrl("/security/index")
				.authenticationDetailsSource(
						new CustomAuthenticationDetailsSource())
				.usernameParameter("userName").passwordParameter("password")
				.permitAll().and().csrf().disable().httpBasic().and().headers()
				.disable();
		http.logout().logoutUrl("/security/logout")
				.logoutSuccessUrl("/security/login")
				.addLogoutHandler(logoutHandler);
		http.rememberMe().key("zohan");
	}

}
