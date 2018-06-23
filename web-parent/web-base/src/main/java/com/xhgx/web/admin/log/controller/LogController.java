package com.xhgx.web.admin.log.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.log.entity.LogEntity;
import com.xhgx.web.admin.log.service.LogService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;

/**
 * @ClassName LogController
 * @Description 操作日志表Controller
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@RequestMapping("/admin/log")
@Controller("logController")
@Scope("prototype")
public class LogController extends AbstractBaseController {

	@Autowired
	public LogService logService;

	/**
	 * 进入分页查询页面
	 * 
	 * @param logEntity
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getLogQueryPage")
	public String getLogQueryPage(LogEntity logEntity,
			BindingResult bindingResult) {
		return "admin/log/log_query";
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            分页条件
	 * @param orderBy
	 *            排序条件
	 * @param bindingResult
	 */
	@RequestMapping("/findPage")
	public void findPage(Page page, String orderBy, BindingResult bindingResult) {
		Map<String, Object> param = RequestSearch.getSearch(request);
		if (StringUtils.isBlank(orderBy)) {
			orderBy = " create_date desc";
		}
		logService.findPage(param, page, orderBy);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(page);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/deleteBatch")
	public void deleteBatch(String[] ids) {

		logService.deleteBatch(ids);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(ids);
	}
}
