package com.xhgx.web.admin.user.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhgx.commons.excel.ExportExcelUtil;
import com.xhgx.commons.excel.ReadExcelUtil;
import com.xhgx.commons.lang.GlobleExcelConfig;
import com.xhgx.commons.lang.MD5Utils;
import com.xhgx.commons.lang.ObjAnalysis;
import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.company.entity.CompanyEntity;
import com.xhgx.web.admin.company.service.CompanyService;
import com.xhgx.web.admin.config.service.ConfigHelper;
import com.xhgx.web.admin.log.service.LoginLogService;
import com.xhgx.web.admin.user.entity.UserDeptEntity;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.admin.user.service.UserDeptService;
import com.xhgx.web.admin.user.service.UserRoleService;
import com.xhgx.web.admin.user.service.UserService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName UserController
 * @Description 用户表Controller
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@RequestMapping("/admin/user")
@Controller("userController")
@Scope("prototype")
public class UserController extends AbstractBaseController {

	public Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
			.create();

	@Autowired
	public UserService userService;

	@Autowired
	public UserRoleService userRoleService;

	@Autowired
	public UserDeptService userDeptService;

	@Autowired
	public LoginLogService loginLogService;

	@Autowired
	public CompanyService companyService;

	/**
	 * 进入分页查询页面
	 * 
	 * @param page
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getUserQueryPage")
	public String getUserQueryPage(Page page, BindingResult bindingResult) {
		return "admin/user/user_query";
	}

	/**
	 * 进入新增/编辑页面
	 * 
	 * @param user
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getUserEditPage")
	public String getUserEditPage(UserEntity user, BindingResult bindingResult) {
		//
		UserEntity loginUser = (UserEntity) this.getCurrentUser();
		// 超级个管理员账号
		String superAdmin = ConfigHelper.getInstance().get("super.administrator");
		// 如果登录用户是超级管理员，则可以查询所有的企业
		if (superAdmin.equals(loginUser.getLoginName())) {
			request.setAttribute("superUser", "yes");
		}
		String deptId="";
		CompanyEntity company = null;
		// 修改页面
		if (!StringUtils.isBlank(user.getId())) {
			user = (UserEntity) userService.get(user);
			if (!StringUtils.isBlank(user.getCompId())) {
				company = (CompanyEntity) companyService.get(user.getCompId());
				if (company != null){
					user.setCompName(company.getCompName());
				}
			}
		} else {// 新增页面，将当前登录用户的公司信息填充
			//  用户部门页面中的新增用户将deptId暂时存到compId中
			deptId= user.getCompId();
			if (!StringUtils.isBlank(loginUser.getCompId())) {
				company = (CompanyEntity) companyService.get(loginUser
						.getCompId());
				if (company != null) {
					user.setCompId(company.getId());
					user.setCompName(company.getCompName());
				}
			}
		}
		request.setAttribute("deptId", deptId);
		request.setAttribute("user", user);
		return "admin/user/user_edit";
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
		UserEntity user = (UserEntity) this.getCurrentUser();
		// 超级个管理员账号
		String superAdmin = ConfigHelper.getInstance().get("super.administrator");
		// 如果登录用户是超级管理员，则可以查询所有的
		if (!superAdmin.equals(user.getLoginName())) {
			param.put("compId", user.getCompId());
		}
		userService.findPage(param, page, orderBy);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(page);
	}

	/**
	 * 定时调度保存
	 * 
	 * @param user
	 * @param bindingResult
	 */
	@RequestMapping("/save")
	public void save(@Valid UserEntity user, BindingResult bindingResult,String deptId) {
		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		if (StringUtils.isBlank(user.getPassword())) {
			errorJson(getMessage("user.management.form.confirm.password.empty","密码不能为空"));
			return;
		}
		// 验证用户名是否存在
		UserEntity valUser = userService.queryByUserNameAndComp(
				user.getUserName(), "");
		if (valUser != null && valUser.getId() != null) {
			errorJson(getMessage("user.management.form.confirm.username.exist","用户名已存在！"));
			return;
		}
		OnlineUser loginUser = this.getCurrentUser();
		String salt = ObjAnalysis.getRandomString(10);
		String password = MD5Utils.getMD5String(user.getPassword() + salt);
		// 密码加密串
		user.setSalt(salt);
		user.setPassword(password);
		// 是否删除，1正常
		user.setIsDel(1);
		user.setCreateBy(loginUser.getLoginName());
		user.setCreateDate(new Date());
		userService.save(user);
		if(!"".equals(deptId)){
			UserDeptEntity userDept = new UserDeptEntity();
			userDept.setDeptId(deptId);
			userDept.setUserId(user.getId());
			userDeptService.save(userDept);
		}
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(user);
	}

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @param bindingResult
	 */
	@RequestMapping("/update")
	public void update(@Valid UserEntity user, BindingResult bindingResult) {
		// 这里是手动验证，错误信息用 errorJson 返回，这里是ajax请求
		if (!validate(bindingResult)){
			return;
		}
		userService.update(user);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(user);
	}

	/**
	 * 进入新增/编辑页面
	 * 
	 * @param user
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getPasswordResetPage")
	public String getPasswordResetPage(UserEntity user,
			BindingResult bindingResult) {
		request.setAttribute("user", user);
		return "admin/user/user_password_reset";
	}

	/**
	 * 重置密码 密码规则 = MD5(用户密码+salt安全字段)
	 * 
	 * @param user
	 * @param bindingResult
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updatePassword")
	public void updatePassword(UserEntity user, BindingResult bindingResult) {
		if (StringUtils.isBlank(user.getId())) {
			errorJson(getMessage("system.management.text.not.find.user.information","查找不到用户信息"));
			return;
		}
		if (StringUtils.isBlank(user.getPassword())) {
			errorJson(getMessage("user.management.form.confirm.password.empty","密码不能为空"));
			return;
		}
		// 根据ID查询出用户信息
		UserEntity userNow = (UserEntity) userService.get(user);
		if (userNow == null) {
			errorJson(getMessage("system.management.text.not.find.user.information","查找不到用户信息"));
		}
		String password = MD5Utils.getMD5String(user.getPassword()
				+ userNow.getSalt());
		user.setPassword(password);
		userService.updatePasswordById(user);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(user);
	}
	

	/**
	 * 当前用户修改自己的密码页面
	 * 
	 * @param user
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getPasswordUpdatePage")
	public String getPasswordUpdatePage(UserEntity user,
			BindingResult bindingResult) {
		
		return "admin/user/user_password_update";
	}
	
	/**
	 * 当前用户修改密码 密码规则 = MD5(用户密码+salt安全字段)
	 * 
	 * @param user
	 * @param bindingResult
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updatePasswordByNowUser")
	public void updatePasswordByNowUser(String oldPassword,String password,String passwordAgain) {
		UserEntity user = (UserEntity) this.getCurrentUser();
		if (StringUtils.isBlank(password)) {
			errorJson(getMessage("user.management.form.confirm.password.empty","密码不能为空"));
			return;
		}
		// 根据ID查询出用户信息
		UserEntity userNow = (UserEntity) userService.get(user);
		if (userNow == null) {
			errorJson(getMessage("system.management.text.not.find.user.information","查找不到用户信息"));
			return;
		}
		String oldPasswordMd5 = MD5Utils.getMD5String(oldPassword + userNow.getSalt());
		if(!oldPasswordMd5.equals(userNow.getPassword())){
			errorJson("旧密码不对，请重新输入！");
			return;
		}
		if(password != null && !password.equals(passwordAgain)){
			errorJson("新密码两次输入的不一致！");
			return;
		}
		String passwordMd5 = MD5Utils.getMD5String(password + userNow.getSalt());
		userNow.setPassword(passwordMd5);
		userService.updatePasswordById(userNow);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(user);
	}
	

	/**
	 * 删除用户信息，假删除，将isDel状态改为2
	 * 
	 * @param user
	 * @param bindingResult
	 */
	@RequestMapping("/delete")
	public void delete(UserEntity user, BindingResult bindingResult) {
		user = (UserEntity) userService.get(user);
		// 超级个管理员账号
		String superAdmin = ConfigHelper.getInstance().get("super.administrator");
		// 如果登录用户是超级管理员，则可以查询所有的企业
		if (superAdmin.equals(user.getUserName())) {
			errorJson(getMessage("system.management.text.super.administrator.deleted","超级管理员不能被删除！！"));
		} else {
			userService.delete(user);
			// 业务逻辑操作成功，ajax 使用successJson 返回
			successJson("");
		}

	}

	/**
	 * 进入用户角色分配页面
	 * 
	 * @param user
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getUserRoleAssignmentPage")
	public String getUserRoleAssignmentPage(UserEntity user,
			BindingResult bindingResult) {
		List<HashMap> list = null;
		if (!StringUtils.isBlank(user.getId())) {
			// 查询选择的用户所在企业（）
			UserEntity selectUser = (UserEntity) userService.get(user.getId());
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("compId", selectUser.getCompId());
			paramsMap.put("userId", user.getId());
			list = userService.getRolesLeftJoinUserChecked(paramsMap);
		}
		List<HashMap> listMap = new ArrayList<HashMap>();
		for (HashMap hashMap : list) {
			HashMap map = new HashMap();
			map.put("id", hashMap.get("ID"));
			map.put("roleName", hashMap.get("ROLENAME"));
			map.put("compId", hashMap.get("COMPID"));
			map.put("selected", hashMap.get("ROLEID") != null ? true : false);
			listMap.add(map);
		}
		request.setAttribute("list", listMap);
		request.setAttribute("user", user);
		return "admin/user/user_role_assignment";
	}

	/**
	 * 保存用户角色分配
	 * 
	 * @param user
	 * @param roleIds
	 * @param bindingResult
	 */
	@RequestMapping("/saveUserRole")
	public void saveUserRole(UserEntity user, String roleIds,
			BindingResult bindingResult) {
		String[] roleId = null;
		if (roleIds != null) {
			roleId = roleIds.split(",");
		}
		userRoleService.updateUserRole(user.getId(), roleId);

		successJson("");
	}

	/**
	 * 进入用户部门分配页面
	 * 
	 * @param user
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getUserDeptAssignmentPage")
	public String getUserDeptAssignmentPage(UserEntity user,
			BindingResult bindingResult) {
		List<HashMap> list = null;
		if (!StringUtils.isBlank(user.getId())) {
			// 查询选择的用户所在企业（）
			UserEntity selectUser = (UserEntity) userService.get(user.getId());
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("compId", selectUser.getCompId());
			paramsMap.put("userId", user.getId());
			list = userService.getDeptsLeftJoinUserChecked(paramsMap);
			user.setUserName(selectUser.getUserName());
		}
		List<HashMap> maps = new ArrayList<HashMap>();
		for (HashMap hashMap : list) {
			HashMap map = new HashMap();
			map.put("id", hashMap.get("ID"));
			map.put("pId", hashMap.get("PID"));
			map.put("name", hashMap.get("DEPTNAME"));
			map.put("open", true);
			map.put("checked", hashMap.get("DEPT_ID") != null ? true : false);
			maps.add(map);
		}
		String json = gson.toJson(maps);
		request.setAttribute("jsonTree", json);
		request.setAttribute("user", user);
		return "admin/user/user_dept_assignment";
	}

	/**
	 * 保存用户部门分配
	 * 
	 * @param user
	 * @param deptIds
	 * @param bindingResult
	 */
	@RequestMapping("/saveUserDept")
	public void saveUserDept(UserEntity user, String deptIds,
			BindingResult bindingResult) {
		String[] deptId = null;
		if (deptIds != null && !"".equals(deptIds)) {
			deptId = deptIds.split(",");
		}
		userDeptService.updateUserDept(user.getId(), deptId);
		successJson("");
	}

	/**
	 * 进入用户单选页面
	 * 
	 * @param user
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getOneUserQueryPage")
	public String getOneUserQueryPage(UserEntity user,
			BindingResult bindingResult) {
		return "admin/user/user_radio_page";
	}

	/**
	 * 进入用户查看页面，根据中间表role和dept
	 * 
	 * @param user
	 * @param selectType
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getUserByRoleOrDeptPage")
	public String getUserByRoleOrDeptPage(UserEntity user, String selectType,
			BindingResult bindingResult) {
		// 将ID临时放在userId上
		String selectId = user.getId();
		request.setAttribute("selectId", selectId);
		request.setAttribute("selectType", selectType);
		return "admin/user/user_list_query";
	}

	/**
	 * 根据用户名称验证用户是否存在
	 * 
	 * @param user
	 * @param bindingResult
	 */
	@RequestMapping("/validateUserName")
	public void validateUserName(UserEntity user, BindingResult bindingResult) {
		System.out.println(getMessage("system.management.text.verify.user","验证用户：") + user.getUserName());
		UserEntity u = userService.queryByUserNameAndComp(user.getUserName(),
				null);
		if (u != null && u.getId() != null) {
			// 用户已存在yes
			successJson("y");
		} else {
			// 用户不存在no
			successJson("n");
		}
	}

	/**
	 * 封装获取用户详细信息
	 * 
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/userSearchableSelect", produces = "text/html;charset=UTF-8")
	public String userSearchableSelect() {
		UserEntity userEntity = new UserEntity();
		List<UserEntity> userList = userService.queryAllObj(userEntity);
		JSONArray json = JSONArray.fromObject(userList);
		String str = json.toString();
		return str;
	}

	@RequestMapping("/importUserInfo")
	public void importUserInfo(UserEntity user) {
		StringBuffer impBuf = new StringBuffer();
		try {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multiRequest.getFile("importExcel");
			if (file != null) {
				InputStream inputStream = file.getInputStream();
				// new String[] {
				// "用户名","真实姓名","电话","手机","身份证","工号","email","地址","用户类型" }
				String[] hdNames = GlobleExcelConfig.getProperty(
						"excel_head_column").split(",");
				List<Map<String, String>> listMap = ReadExcelUtil.parseByMap(
						inputStream, hdNames);
				// System.out.println(listMap);
				List<UserEntity> userList = new ArrayList<UserEntity>();
				int len = 1;
				for (Map<String, String> map : listMap) {
					len++;
					UserEntity userInfo = new UserEntity();
					userInfo.setUserName(map.get(getMessage("user.management.text.username","用户名")));

					// 验证用户名是否存在
					UserEntity valUser = userService.queryByUserNameAndComp(
							userInfo.getUserName(), "");
					UserEntity loginUser = (UserEntity) this.getCurrentUser();
					String salt = ObjAnalysis.getRandomString(10);
					String password = MD5Utils.getMD5String(map.get(getMessage("common.text.password","密码"))
							+ salt);
					// 密码加密串
					userInfo.setSalt(salt);
					userInfo.setPassword(password);

					userInfo.setRealName(map.get(getMessage("user.management.text.real.name","真实姓名")));
					userInfo.setPhone(map.get(getMessage("user.management.text.telephone","电话")));
					userInfo.setMobile(map.get(getMessage("user.management.text.mobile.phone","手机")));
					userInfo.setIdCard(map.get(getMessage("user.management.text.identity.card","身份证")));
					userInfo.setEmpNo(map.get(getMessage("user.management.text.job.number","工号")));
					userInfo.setEmail(map.get("email"));
					userInfo.setAddress(map.get(getMessage("user.management.text.contact.address","联系地址")));
					userInfo.setUserType(map.get(getMessage("user.management.text.user.type","用户类型")));

					userInfo.setCompId(loginUser.getCompId());
					userInfo.setLoginStatus(1);
					// 登录状态
					// 是否删除，1正常
					userInfo.setIsDel(1);
					userInfo.setCreateBy(loginUser.getLoginName());
					userInfo.setCreateDate(new Date());

					if (valUser != null && valUser.getId() != null) {
						impBuf.append(getMessage("system.management.text.No","第") + len + getMessage("system.management.text.row","行，") + userInfo.getUserName()
								+ getMessage("user.management.form.confirm.username.exist","用户名已存在！"));
					} else {
						userList.add(userInfo);
					}

				}
				userService.saveList(userList);
				if (impBuf.length() > 0) {
					errorJson(impBuf.toString());
				} else {
					impBuf.append(getMessage("system.management.text.all.imported.successfully","全部导入成功！"));
					successJson(impBuf.toString());
				}
			} else {
				errorJson(getMessage("system.management.text.not.find.corresponding.file","没有找到对应的文件！"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			errorJson("");
		}
	}

	@RequestMapping("/exportUserInfo")
	public void exportUserInfo(UserEntity user, String orderBy)
			throws Exception {
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
		List<UserEntity> list = userService.findList(param, orderBy);

		// String[] hds = { "userName", "realName", "phone", "mobile", "idCard",
		// "address", "empNo", "email"};
		// String[] hdNames = { "用户名", "真实姓名", "电话", "手机", "身份证", "地址", "工号",
		// "email"};
		String[] hds = ConfigHelper.getInstance().get("excel_entity_column")
				.split(",");
		String[] hdNames = ConfigHelper.getInstance().get("excel_head_column")
				.split(",");
		String xlsName = getMessage("system.management.text.user.infromation.export","用户信息导出");
		ExportExcelUtil.outPutExcel(list, hdNames, hds, xlsName, request,
				response);
	}

	public static void main(String[] args) {
		String[] hds2 = ConfigHelper.getInstance().get("excel_entity_column")
				.split(",");
		System.out.println(hds2);
		String[] hdNames2 = ConfigHelper.getInstance().get("excel_head_column")
				.split(",");
		System.out.println(hdNames2);
	}

	/**
	 * 进入选择推送人页面
	 * 
	 * @param user
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getNoticeUserPage")
	public String getNoticeUserPage(UserEntity user, BindingResult bindingResult) {
		return "admin/user/user_notice_page";
	}
}
