package com.xhgx.web.admin.userToken.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.userToken.entity.UserToken;
import com.xhgx.web.admin.userToken.service.UserTokenService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;

/**
 * @ClassName UserTokenController
 * @Description tb_sys_user_token 控制层
 * @author <font color='blue'>libo</font>
 * @date 2017-06-26 18:06:06
 * @version 1.0
 */
@Controller("userTokenController")
@RequestMapping("/admin/userToken")
@Scope("prototype")
public class UserTokenController extends AbstractBaseController {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(UserToken.class);

	/**Service对象，可以调用其中的业务逻辑方法 */
	@Autowired
	@Qualifier("userTokenService")
	private UserTokenService userTokenService;

	/**
	 * 统一页面路径
	 * 
	 * @return String
	 */
	public String viewPrefix() {
		return "admin/userToken/user_token_";
	}

	/**
	 * 进入分页查询页面
	 * 
	 * @return String
	 */
	@RequestMapping("/queryPage")
	public String queryPage() {
		return this.viewPrefix() + "query";
	}

	/**
	 * 分页查询功能
	 * 
	 * @param page
	 * @param orderBy
	 */
	@RequestMapping("/findPage")
	public void findPage(Page page, String orderBy) {
		Map<String, Object> param = RequestSearch.getSearch(request);
		userTokenService.findPage(param, page, orderBy);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(page);
	}

	/**
	 * 信息保存
	 * 
	 * @param entity
	 * @param bindingResult
	 */
	@RequestMapping("/save")
	public void save(@Valid UserToken entity, BindingResult bindingResult) {

		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}

		userTokenService.save(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 进入新增/编辑页面
	 * 
	 * @param entity
	 * @return String
	 */
	@RequestMapping("/editPage")
	public String editPage(UserToken entity) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (UserToken) userTokenService.get(entity);
		}
		request.setAttribute("entity", entity);
		return this.viewPrefix() + "edit";
	}

	/**
	 * 修改用户信息
	 * 
	 * @param entity
	 * @param bindingResult
	 */
	@RequestMapping("/update")
	public void update(@Valid UserToken entity, BindingResult bindingResult) {
		// 这里是手动验证，错误信息用 errorJson 返回，这里是ajax请求
		if (!validate(bindingResult)){
			return;
		}
		userTokenService.update(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 删除信息操作
	 * 
	 * @param entity
	 */
	@RequestMapping("/delete")
	public void delete(UserToken entity) {
		userTokenService.delete(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/deleteBatch")
	public void deleteBatch(String[] ids) {
		userTokenService.deleteBatch(ids);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(ids);
	}

}
