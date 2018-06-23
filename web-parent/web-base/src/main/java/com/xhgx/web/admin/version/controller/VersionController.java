package com.xhgx.web.admin.version.controller;

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
import com.xhgx.web.admin.version.entity.Version;
import com.xhgx.web.admin.version.service.VersionService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;

/**
 * @ClassName VersionController
 * @Description tb_sys_version 控制层
 * @author <font color='blue'>zhangjin</font>
 * @date 2017-06-12 15:22:03
 * @version 1.0
 * 
 */
@Controller("versionController")
@RequestMapping("/admin/version")
@Scope("prototype")
public class VersionController extends AbstractBaseController {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Version.class);

	/**Service对象，可以调用其中的业务逻辑方法 */
	@Autowired
	@Qualifier("versionService")
	private VersionService versionService;

	/**
	 * 统一页面路径
	 * 
	 * @return String
	 */
	public String viewPrefix() {
		return "admin/version/version_";
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
		versionService.findPage(param, page, orderBy);
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
	public void save(@Valid Version entity, BindingResult bindingResult) {

		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		versionService.save(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 进入新增/编辑页面
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(Version entity) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (Version) versionService.get(entity);
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
	public void update(@Valid Version entity, BindingResult bindingResult) {
		// 这里是手动验证，错误信息用 errorJson 返回，这里是ajax请求
		if (!validate(bindingResult)){
			return;
		}
		versionService.update(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 删除信息操作
	 * 
	 * @param entity
	 */
	@RequestMapping("/delete")
	public void delete(Version entity) {
		versionService.delete(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 进入查看页面
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("/seePage")
	public String seePage(Version entity) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (Version) versionService.get(entity);
		}
		request.setAttribute("entity", entity);
		return this.viewPrefix() + "see";
	}
}
