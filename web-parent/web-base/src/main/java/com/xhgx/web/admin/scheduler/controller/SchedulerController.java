package com.xhgx.web.admin.scheduler.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.scheduler.entity.Scheduler;
import com.xhgx.web.admin.scheduler.service.ISchedulerService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;

/**
 * @ClassName SchedulerController
 * @Description 定时器管理 控制层
 * @author <font color='blue'>zohan</font>
 * @date 2017-05-18 17:10:17
 * @version 1.0
 */
@Controller("schedulerController")
@RequestMapping("/admin/scheduler")
@Scope("prototype")
public class SchedulerController extends AbstractBaseController {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

	/**Service对象，可以调用其中的业务逻辑方法 */
	@Autowired
	@Qualifier("schedulerService")
	private ISchedulerService schedulerService;

	/**
	 * 统一页面路径
	 * 
	 * @return String
	 */
	public String viewPrefix() {
		return "admin/scheduler/scheduler_";
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
		schedulerService.findPage(param, page, orderBy);
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
	public void save(@Valid Scheduler entity, BindingResult bindingResult) {

		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		boolean result = schedulerService.add(entity);
		if (result) {
			// 业务逻辑操作成功，ajax 使用successJson 返回
			successJson(entity);
		} else {
			errorJson("操作失败");
		}

	}

	/**
	 * 进入新增/编辑页面
	 * 
	 * @param entity
	 * @return String
	 */
	@RequestMapping("/editPage")
	public String editPage(Scheduler entity) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (Scheduler) schedulerService.get(entity);
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
	public void update(@Valid Scheduler entity, BindingResult bindingResult) {
		// 这里是手动验证，错误信息用 errorJson 返回，这里是ajax请求
		if (!validate(bindingResult)){
			return;
		}
		boolean result = schedulerService.add(entity);
		if (result) {
			// 业务逻辑操作成功，ajax 使用successJson 返回
			successJson(entity);
		} else {
			errorJson("操作失败");
		}
	}

	/**
	 * 删除信息操作
	 * 
	 * @param entity
	 */
	@RequestMapping("/delete")
	public void delete(Scheduler entity) {
		schedulerService.delete(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 暂停/启动定时器
	 * 
	 * @param schedulerId
	 * @param state
	 */
	@RequestMapping("/updateState")
	public void updateState(String schedulerId, String state) {
		if ("on".equals(state)) {
			schedulerService.resume(schedulerId);
			successJson(state);
		} else if ("off".equals(state)) {
			schedulerService.pause(schedulerId);
			successJson(state);
		}
	}

	/**
	 * 验证corn是否合法
	 * 
	 * @param entity
	 * @param bindingResult
	 */
	@RequestMapping("/validateCorn")
	public void validateCode(Scheduler entity, BindingResult bindingResult) {
		String cronExpression = entity.getCorn();
		boolean flag = CronExpression.isValidExpression(cronExpression);
		if (flag) {
			// 合法
			successJson("true");
		} else {
			// 不合法
			successJson("false");
		}
	}

}
