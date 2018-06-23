package com.xhgx.web.admin.config.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.xhgx.sdk.cache.SimpleCacheHelper;
import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.config.entity.ConfigEntity;
import com.xhgx.web.admin.config.service.ConfigService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName ConfigController
 * @Description 配置表Controller
 * @author 李彦伟
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Controller("configController")
@RequestMapping("/admin/config")
@Scope("prototype")
public class ConfigController extends AbstractBaseController {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(ConfigController.class);

	/**Service对象，可以调用其中的业务逻辑方法 */
	@Autowired
	@Qualifier("configService")
	private ConfigService configService;

	/**
	 * 统一页面路径
	 * 
	 * @return String
	 */
	public String viewPrefix() {
		return "admin/config/config_";
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
		if (StringUtils.isBlank(orderBy)) {
			orderBy = " create_date desc";
		}
		configService.findPage(param, page, orderBy);
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
	public void save(@Valid ConfigEntity entity, BindingResult bindingResult) {

		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		OnlineUser user = this.getCurrentUser();
		entity.setCreateBy(user.getLoginName());
		entity.setCreateDate(new Date());
		configService.save(entity);
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
	public String editPage(ConfigEntity entity) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (ConfigEntity) configService.get(entity);
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
	public void update(@Valid ConfigEntity entity, BindingResult bindingResult) {
		// 这里是手动验证，错误信息用 errorJson 返回，这里是ajax请求
		if (!validate(bindingResult)){
			return;
		}
		configService.update(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 删除信息操作
	 * 
	 * @param entity
	 */
	@RequestMapping("/delete")
	public void delete(ConfigEntity entity) {
		entity = (ConfigEntity) configService.get(entity);
		configService.delete(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 根据code验证属性key是否存在
	 * 
	 * @param config
	 * @param bindingResult
	 */
	@RequestMapping("/validateCode")
	public void validateCode(ConfigEntity config, BindingResult bindingResult) {
		System.out.println(getMessage("system.management.text.verify.attribute.key","验证属性key：") + config.getCode());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", config.getCode());
		List<ConfigEntity> list = configService.findList(param, "");
		if (list != null && list.size() > 0) {
			// 如果是修改，则和修改的ID进行比较，如果一样则提示不存在
			if (list.get(0).getId().equals(config.getId())) {
				// 不存在no
				successJson("n");
			} else {
				// 已存在yes
				successJson("y");
			}

		} else {
			// 不存在no
			successJson("n");
		}
	}

	@RequestMapping("/updateMenuTemplate")
	public void updateMenuTemplate(){
		String value = configService.getValue("menu.template");
		System.out.println(value);
		if(!"flexMenu".equals(value)){
			Map<String, ConfigEntity> map = (Map<String, ConfigEntity>) SimpleCacheHelper.get("config.map");
			ConfigEntity config = null;
			if (map != null) {
				config = map.get("menu.template");
			}
			config.setValue("flexMenu");
			configService.update(config);
			successJson(1);
		}
	}

}
