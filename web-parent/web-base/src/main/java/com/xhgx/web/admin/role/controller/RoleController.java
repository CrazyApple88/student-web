package com.xhgx.web.admin.role.controller;

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
import com.xhgx.web.admin.config.service.ConfigHelper;
import com.xhgx.web.admin.role.entity.RoleEntity;
import com.xhgx.web.admin.role.service.RoleService;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.admin.user.service.UserRoleService;
import com.xhgx.web.admin.user.service.UserService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName RoleController
 * @Description 角色表Controller 
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@RequestMapping("/admin/role")
@Controller("roleController")
@Scope("prototype")
public class RoleController extends AbstractBaseController {

	@Autowired
	public RoleService roleService;

	@Autowired
	public UserService userService;

	@Autowired
	public UserRoleService userRoleService;

	@Autowired
	public CompanyService companyService;

	/**
	 * 进入角色查询页面
	 * 
	 * @param page
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getRoleQueryPage")
	public String getRoleQueryPage(Page page, BindingResult bindingResult) {
		return "admin/role/role_query";
	}

	/**
	 * 进入新增/编辑页面
	 * 
	 * @param role
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getRoleEditPage")
	public String getRoleEditPage(RoleEntity role, BindingResult bindingResult) {
		UserEntity loginUser = (UserEntity) this.getCurrentUser();
		// 超级个管理员账号
		String superAdmin = ConfigHelper.getInstance().get("super.administrator");
		// 如果登录用户是超级管理员，则可以查询所有的企业
		if (superAdmin.equals(loginUser.getLoginName())) {
			request.setAttribute("superUser", "yes");
		}
		CompanyEntity company = null;
		if (!StringUtils.isBlank(role.getId())) {
			role = (RoleEntity) roleService.get(role);
			if (!StringUtils.isBlank(role.getCompId())) {
				company = (CompanyEntity) companyService.get(role.getCompId());
				if (company != null){
					role.setCompName(company.getCompName());
				}
			}
		} else {// 新增页面，将当前登录用户的公司信息填充
			if (!StringUtils.isBlank(loginUser.getCompId())) {
				company = (CompanyEntity) companyService.get(loginUser
						.getCompId());
				if (company != null) {
					role.setCompId(company.getId());
					role.setCompName(company.getCompName());
				}
			}
		}
		request.setAttribute("role", role);
		return "admin/role/role_edit";
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
		UserEntity loginUser = (UserEntity) this.getCurrentUser();
		// 超级个管理员账号
		String superAdmin = ConfigHelper.getInstance().get("super.administrator");
		// 如果登录用户是超级管理员，则可以查询所有的
		if (!superAdmin.equals(loginUser.getLoginName())) {
			param.put("compId", loginUser.getCompId());
		}
		roleService.findPage(param, page, orderBy);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(page);
	}

	/**
	 * 保存角色信息
	 * 
	 * @param role
	 * @param bindingResult
	 */
	@RequestMapping("/save")
	public void save(@Valid RoleEntity role, BindingResult bindingResult) {
		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		OnlineUser loginUser = this.getCurrentUser();
		role.setUseable(1);
		role.setCreateBy(loginUser.getLoginName());
		role.setCreateDate(new Date());
		roleService.save(role);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(role);
	}

	/**
	 * 修改角色信息
	 * 
	 * @param role
	 * @param bindingResult
	 */
	@RequestMapping("/update")
	public void update(@Valid RoleEntity role, BindingResult bindingResult) {
		if (!validate(bindingResult)){
			return;
		}
		roleService.update(role);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(role);
	}

	/**
	 * 根据ID删除角色
	 * 
	 * @param role
	 * @param bindingResult
	 */
	@RequestMapping("/delete")
	public void delete(RoleEntity role, BindingResult bindingResult) {

		roleService.deleteByRoleId(role);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(role);
	}

	/**
	 * 进入角色单选页面
	 * 
	 * @param role
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getOneRoleQueryPage")
	public String getOneRoleQueryPage(RoleEntity role,
			BindingResult bindingResult) {
		return "admin/role/role_radio_page";
	}
	
	/**
	 * 进入角色分配用户页面
	 * 
	 * @param user
	 * @param selectType
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getRoleUserAssignPage")
	public String getRoleUserAssignPage(RoleEntity role, BindingResult bindingResult) {
		UserEntity loginUser = (UserEntity) this.getCurrentUser();
		Map<String,String> notParam = new HashMap<String,String>();
		notParam.put("compId", loginUser.getCompId());
		notParam.put("notEqualsRoleId", loginUser.getCompId());
		notParam.put("roleId", role.getId());
		List<UserEntity> listUserNotSelect = userService.findList(notParam, "user_name asc");
		
		Map<String,String> param = new HashMap<String,String>();
		param.put("compId", loginUser.getCompId());
		param.put("equalsRoleId", loginUser.getCompId());
		param.put("roleId", role.getId());
		List<UserEntity> listUserSelect = userService.findList(param, "user_name asc");
		
		request.setAttribute("role", role);
		request.setAttribute("listUserNotSelect", listUserNotSelect);
		request.setAttribute("listUserSelect", listUserSelect);
		
		
		return "admin/role/role_user_assign";
	}
	
	/**
	 * 保存角色用户分配信息
	 * 
	 * @param role
	 * @param bindingResult
	 */
	@RequestMapping("/saveRoleUser")
	public void saveRoleUser(RoleEntity role, String selectUserIds) {

		String[] userId = null;
		if (selectUserIds != null) {
			userId = selectUserIds.split(",");
		}
		userRoleService.updateRoleUser(userId, role.getId());
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(role);
	}

	/**
	 * 验证统一企业下的角色名称不能重复
	 * 
	 * @param role
	 * @param bindingResult
	 */
	@RequestMapping("/validateRoleName")
	public void validateRoleName(RoleEntity role, BindingResult bindingResult) {
		System.out.println("验证角色名称：" + role.getRoleName());
		if (!StringUtils.isBlank(role.getRoleName())
				// 企业编号和名称都不能为空
				&& !StringUtils.isBlank(role.getCompId())) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("valRoleName", role.getRoleName());
			param.put("compId", role.getCompId());
			List<RoleEntity> list = roleService.findList(param, "");
			if (list != null && list.size() > 0) {
				// 如果是修改，则和修改的ID进行比较，如果一样则提示不存在
				if (list.get(0).getId().equals(role.getId())) {
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
		} else {
			// 不存在no
			successJson("n");
		}
	}
}
