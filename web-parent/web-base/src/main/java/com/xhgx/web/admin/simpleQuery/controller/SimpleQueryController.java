package com.xhgx.web.admin.simpleQuery.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.simpleQuery.entity.QueryResult;
import com.xhgx.web.admin.simpleQuery.entity.SimpleQuery;
import com.xhgx.web.admin.simpleQuery.service.SimpleQueryService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;

/**
 * @ClassName SimpleQueryController
 * @Description 简单查询 控制层
 * @author <font color='blue'>zohan</font>
 * @date 2017-06-12 17:16:22
 * @version 1.0
 */
@Controller("simpleQueryController")
@RequestMapping("/admin/simpleQuery")
@Scope("prototype")
public class SimpleQueryController extends AbstractBaseController {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(SimpleQuery.class);

	/**Service对象，可以调用其中的业务逻辑方法 */
	@Autowired
	@Qualifier("simpleQueryService")
	private SimpleQueryService simpleQueryService;

	/**
	 * 统一页面路径
	 * 
	 * @return String
	 */
	public String viewPrefix() {
		return "admin/simpleQuery/simple_query_";
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
		simpleQueryService.findPage(param, page, orderBy);
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
	public void save(@Valid SimpleQuery entity, BindingResult bindingResult) {

		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
			
		simpleQueryService.save(entity);
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
	public String editPage(SimpleQuery entity) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (SimpleQuery) simpleQueryService.get(entity);
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
	public void update(@Valid SimpleQuery entity, BindingResult bindingResult) {
		// 这里是手动验证，错误信息用 errorJson 返回，这里是ajax请求
		if (!validate(bindingResult)){
			return;
		}
			
		simpleQueryService.update(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 删除信息操作
	 * 
	 * @param entity
	 */
	@RequestMapping("/delete")
	public void delete(SimpleQuery entity) {
		simpleQueryService.delete(entity);
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
		simpleQueryService.deleteBatch(ids);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(ids);
	}

	/**
	 * 查询
	 *
	 * @param request
	 * @param response
	 * @param id
	 * @return String
	 */
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
	public String query(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String id) {
		QueryResult result = simpleQueryService.updateQuery(id);
		request.setAttribute("queryResult", result);
		return viewPrefix() + "result";
	}

	/**
	 * 导出
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param json
	 * @return String
	 */
	@RequestMapping(value = "/exportFile", method = RequestMethod.POST)
	public String exportFile(HttpServletRequest request,
			HttpServletResponse response, Model model, String json) {
		response.setHeader("Content-disposition", "attachment; filename="
				+ System.currentTimeMillis() + ".xls"); // 线下
		response.setContentType("application/vnd.ms-excel");

		if (StringUtils.isNotBlank(json)) {
			json = json.replace(
					"<table class='table table-bordered table-hover'>", "");
			json = json.replace("</table>", "");
		}

		model.addAttribute("excelContent", json);
		return viewPrefix() + "export";

	}

}
