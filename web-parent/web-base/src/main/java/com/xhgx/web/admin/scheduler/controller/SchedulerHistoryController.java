package com.xhgx.web.admin.scheduler.controller;

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
import com.xhgx.web.admin.scheduler.entity.SchedulerHistory;
import com.xhgx.web.admin.scheduler.service.ISchedulerHistoryService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;

/**
 * @ClassName SchedulerHistoryController
 * @Description 定时历史信息 控制层
 * @author <font color='blue'>zohan</font>
 * @date 2017-05-18 17:10:17
 * @version 1.0
 */
@Controller("schedulerHistoryController")
@RequestMapping("/admin/schedulerHistory")
@Scope("prototype")
public class SchedulerHistoryController extends AbstractBaseController {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(SchedulerHistory.class);

	/**Service对象，可以调用其中的业务逻辑方法 */
	@Autowired
	@Qualifier("schedulerHistoryService")
	private ISchedulerHistoryService schedulerHistoryService;

	/**
	 * 统一页面路径
	 * 
	 * @return String
	 */
	public String viewPrefix() {
		return "admin/schedulerHistory/schedulerHistory_";
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
		schedulerHistoryService.findPage(param, page, orderBy);
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
	public void save(@Valid SchedulerHistory entity, BindingResult bindingResult) {

		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		schedulerHistoryService.save(entity);
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
	public String editPage(SchedulerHistory entity) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (SchedulerHistory) schedulerHistoryService.get(entity);
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
	public void update(@Valid SchedulerHistory entity,
			BindingResult bindingResult) {
		// 这里是手动验证，错误信息用 errorJson 返回，这里是ajax请求
		if (!validate(bindingResult)){
			return;
		}
		schedulerHistoryService.update(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 删除信息操作
	 * 
	 * @param entity
	 */
	@RequestMapping("/delete")
	public void delete(SchedulerHistory entity) {
		schedulerHistoryService.delete(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

}
