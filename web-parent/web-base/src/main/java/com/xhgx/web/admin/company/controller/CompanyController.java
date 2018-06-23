package com.xhgx.web.admin.company.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.company.entity.CompanyEntity;
import com.xhgx.web.admin.company.service.CompanyService;
import com.xhgx.web.admin.dept.entity.DeptEntity;
import com.xhgx.web.admin.dept.service.DeptService;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.admin.user.service.UserService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName CompanyController
 * @Description 公司/企业Controller
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@RequestMapping("/admin/company")
@Controller("companyController")
@Scope("prototype")
public class CompanyController extends AbstractBaseController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@Autowired
	private DeptService deptService;

	/**
	 * 进入分页查询页面
	 * 
	 * @param page
	 * @return String
	 */
	@RequestMapping("/getCompanyQueryPage")
	public String getCompanyQueryPage(Page page) {
		return "admin/company/company_query";
	}

	/**
	 * 进入新增/编辑页面
	 * 
	 * @param company
	 * @return String
	 */
	@RequestMapping("/getCompanyEditPage")
	public String getCompanyEditPage(CompanyEntity company) {
		if (!StringUtils.isBlank(company.getId())) {
			company = (CompanyEntity) companyService.get(company);
		}
		request.setAttribute("company", company);
		return "admin/company/company_edit";
	}

	/**
	 * 公司/企业保存
	 * 
	 * @param entity
	 * @param bindingResult
	 */
	@RequestMapping("/save")
	public void save(@Valid CompanyEntity entity, BindingResult bindingResult) {

		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		OnlineUser loginUser = this.getCurrentUser();
		entity.setCreateBy(loginUser.getLoginName());
		entity.setCreateDate(new Date());
		companyService.save(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 公司/企业删除
	 * 
	 * @param entity
	 */
	@RequestMapping("/delete")
	public void delete(CompanyEntity entity) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("compId", entity.getId());
		List<UserEntity> listUser = userService.queryList(paramsMap);
		List<DeptEntity> listDept = deptService.queryList(paramsMap);
		if (listUser.size() > 0) {
			StringBuffer sb = new StringBuffer(getMessage("system.management.text.current.company.users.used","当前企业已有用户使用："));
			for (UserEntity user : listUser) {
				sb.append("[" + user.getUserName() + "]");
			}
			sb.append(getMessage("system.management.text.delete.users.first.not.delete.operation","请先删除用户，否则无法进行企业删除操作！！"));
			errorJson(sb.toString());
		} else if (listDept.size() > 1) {
			StringBuffer sb = new StringBuffer(getMessage("system.management.text.current.company.departments.used","当前企业已有部门使用："));
			for (DeptEntity dept : listDept) {
				sb.append("[" + dept.getDeptName() + "]");
			}
			sb.append(getMessage("system.management.text.delete.departments.first.not.delete.operation","请先删除部门，否则无法进行企业删除操作！！"));
			errorJson(sb.toString());
		} else {
			companyService.deleteByCompId(entity, listDept);
			successJson(entity);
		}

	}

	/**
	 * 公司/企业修改
	 * 
	 * @param entity
	 * @param bindingResult
	 */
	@RequestMapping("/update")
	public void update(@Valid CompanyEntity entity, BindingResult bindingResult) {

		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		companyService.update(entity);
		successJson(entity);
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            分页条件
	 * @param orderBy
	 *            排序条件
	 */
	@RequestMapping("/findPage")
	public void findPage(Page page, String orderBy) {
		Map<String, Object> param = RequestSearch.getSearch(request);
		if (StringUtils.isBlank(orderBy)) {
			orderBy = " create_date desc";
		}
		companyService.findPage(param, page, orderBy);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(page);
	}

	/**
	 * 进入企业单选页面
	 * 
	 * @param user
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getOneCompanyQueryPage")
	public String getOneCompanyQueryPage(UserEntity user,
			BindingResult bindingResult) {
		return "admin/company/company_radio_page";
	}

}
