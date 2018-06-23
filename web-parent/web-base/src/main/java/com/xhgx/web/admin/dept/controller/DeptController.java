package com.xhgx.web.admin.dept.controller;

import java.util.ArrayList;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhgx.commons.lang.ObjAnalysis;
import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.company.entity.CompanyEntity;
import com.xhgx.web.admin.company.service.CompanyService;
import com.xhgx.web.admin.config.service.ConfigHelper;
import com.xhgx.web.admin.dept.entity.DeptEntity;
import com.xhgx.web.admin.dept.service.DeptService;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName DeptController
 * @Description 部门Controller
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@RequestMapping("/admin/dept")
@Controller("deptController")
@Scope("prototype")
public class DeptController extends AbstractBaseController {

	public Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
			.create();

	@Autowired
	private DeptService deptService;

	@Autowired
	public CompanyService companyService;

	/**
	 * 进入新增/编辑页面
	 * 
	 * @param dept
	 * @param parentName
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getDeptEditPage")
	public String getDeptEditPage(DeptEntity dept, String parentName,
			BindingResult bindingResult) {
		OnlineUser loginUser = this.getCurrentUser();
		// 超级个管理员账号
		String superAdmin = ConfigHelper.getInstance().get("super.administrator");
		// 如果登录用户是超级管理员，则可以查询所有的企业
		if (superAdmin.equals(loginUser.getLoginName())) {
			request.setAttribute("superUser", "yes");
		}
		CompanyEntity company = null;

		String type = "edit";
		// 如果是修改页面
		if (!StringUtils.isBlank(dept.getId())) {
			dept = (DeptEntity) deptService.get(dept);
			if (!StringUtils.isBlank(dept.getCompId())) {
				// 使用已分配的所属企业
				company = (CompanyEntity) companyService.get(dept.getCompId());
				if (company != null){
					dept.setCompName(company.getCompName());
				}
			}
		} else {
			// 如果是新增页面
			if (!StringUtils.isBlank(dept.getParentId())) {
				DeptEntity parentDept = new DeptEntity();
				parentDept.setId(dept.getParentId());
				parentDept = (DeptEntity) deptService.get(parentDept);
				request.setAttribute("parentDept", parentDept);
				type = "add";

				company = (CompanyEntity) companyService.get(parentDept
						.getCompId());
				// 新增的时候默认排序为100
				dept.setSort(100);
				if (company != null) {
					dept.setCompId(company.getId());
					dept.setCompName(company.getCompName());
				}

			}
		}
		request.setAttribute("dept", dept);
		request.setAttribute("type", type);
		return "admin/dept/dept_edit";
	}

	/**
	 * 部门保存
	 * 
	 * @param entity
	 * @param bindingResult
	 */
	@RequestMapping("/save")
	public void save(@Valid DeptEntity entity, BindingResult bindingResult) {

		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		OnlineUser loginUser = this.getCurrentUser();
		// 如果菜单的父ID不为空
		if (!StringUtils.isBlank(entity.getParentId())) {
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("parentId", entity.getParentId());
			// 查出父ID的菜单信息
			Map<String, Object> map = deptService.queryDeptParentId(paramsMap);
			if (map.get("LEVELNUM") != null) {
				String le = map.get("LEVELNUM").toString();
				entity.setLevelnum(Integer.parseInt(le) + 1);
			}
			boolean flag = map.get("MAXDEPTCODE") != null
					&& (!StringUtils.isBlank(map.get("MAXDEPTCODE") + "") && (map
							.get("MAXDEPTCODE") + "").length() >= 3);
			if (flag) {
				String maxDeptCode = map.get("MAXDEPTCODE") + "";
				String newDeptCode = ObjAnalysis.getNewCodeByCode(maxDeptCode,
						4);
				entity.setDeptCode(newDeptCode);
			} else {
				entity.setDeptCode(map.get("DEPTCODE") + "0001");
			}

			entity.setCreateBy(loginUser.getLoginName());
			entity.setCreateDate(new Date());
			deptService.save(entity);
			// 业务逻辑操作成功，ajax 使用successJson 返回
			successJson(entity);
		} else {
			errorJson(getMessage("system.management.text.not.find.current.parent.department.information","没有找到当前父级部门信息！"));
		}

	}

	/**
	 * 部门删除
	 * 
	 * @param entity
	 */
	@RequestMapping("/delete")
	public void delete(DeptEntity entity) {
		entity = (DeptEntity) deptService.get(entity);
		if (entity != null) {
			if ("0".equals(entity.getParentId())) {
				errorJson(getMessage("system.management.text.top.department.not.delete.delete.company","顶级部门不能删除！如果需要删除请删除企业！"));
			} else {
				deptService.deleteByDeptId(entity);
				successJson(entity);
			}
		}
	}

	/**
	 * 公司/企业修改
	 * 
	 * @param entity
	 * @param bindingResult
	 */
	@RequestMapping("/update")
	public void update(@Valid DeptEntity entity, BindingResult bindingResult) {
		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		deptService.update(entity);
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
		deptService.findPage(param, page, orderBy);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(page);
	}

	/**
	 * 进入部门单选页面
	 * 
	 * @param entity
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getOneDeptQueryPage")
	public String getOneDeptQueryPage(DeptEntity entity,
			BindingResult bindingResult) {
		UserEntity user = (UserEntity) this.getCurrentUser();
		// 超级个管理员账号
		String superAdmin = ConfigHelper.getInstance().get("super.administrator");
		Map<String, Object> param = new HashMap<String, Object>();
		// 如果登录用户是超级管理员，则可以查询所有的
		if (!superAdmin.equals(user.getLoginName())) {
			param.put("compId", user.getCompId());
		}
		List<HashMap> list = deptService.queryDeptTree(param);
		List<HashMap> mapsList = new ArrayList<HashMap>();
		for (HashMap hashMap : list) {
			HashMap map = new HashMap();
			map.put("id", hashMap.get("ID"));
			map.put("pId", hashMap.get("PID"));
			map.put("name", hashMap.get("NAME"));
			map.put("open", true);
			mapsList.add(map);
		}
		String json = gson.toJson(mapsList);
		request.setAttribute("jsonTree", json);
		return "admin/dept/dept_radio_page";
	}

	/**
	 * 进入部门树查询页面
	 * 
	 * @return String
	 */
	@RequestMapping("/getDeptTreeQueryPage")
	public String getDeptTreeQueryPage() {
		return "admin/dept/dept_tree_query";
	}

	/**
	 * 获取部门树信息(采用异步加载，可以进行异步刷新树)
	 * 
	 */
	@RequestMapping("/getDeptTreeQuery")
	public void getDeptTreeQuery() {
		UserEntity user = (UserEntity) this.getCurrentUser();
		// 超级个管理员账号
		String superAdmin = ConfigHelper.getInstance().get("super.administrator");
		Map<String, Object> param = new HashMap<String, Object>();
		// 如果登录用户是超级管理员，则可以查询所有的
		if (!superAdmin.equals(user.getLoginName())) {
			param.put("compId", user.getCompId());
		}
		List<HashMap> list = deptService.queryDeptTree(param);
		List<HashMap> mapsList = new ArrayList<HashMap>();
		for (HashMap hashMap : list) {
			HashMap map = new HashMap();
			map.put("id", hashMap.get("ID"));
			map.put("pId", hashMap.get("PID"));
			map.put("name", hashMap.get("NAME"));
			map.put("open", true);
			mapsList.add(map);
		}
		successJson(mapsList);
	}
	
	/**
	 * 部门拖拽后更新相关的部门的sort信息
	 * @param dept
	 */
	@RequestMapping("/updateSort")
	public void updateSort(DeptEntity dept){
		DeptEntity targetDept = new DeptEntity();
		targetDept.setId(dept.getTargetId());
		// 拖拽时选中的目标部门
		targetDept = (DeptEntity) deptService.get(targetDept);	
		String moveType = dept.getMoveType();
		dept = (DeptEntity) deptService.get(dept);
		// 参数集合  
		Map<String,Object> paramsMap = new  HashMap<String, Object>();
		// 向前移动  
	    if (moveType.equals("prev")) {
	    	  paramsMap.put("parentId", dept.getParentId());
	    	  paramsMap.put("sort", dept.getSort());
	    	  paramsMap.put("targetSort", targetDept.getSort());  
	          List<DeptEntity> deptList = deptService.getDeptListBySort(paramsMap);  
	          int sort = targetDept.getSort() + 1;  
	          dept.setSort(targetDept.getSort());  
	          targetDept.setSort(sort);  
	          // 更新所有受影响的排序字段  
	          for (int i = 0; i < deptList.size(); i++) {
	        	  DeptEntity deptTemp = deptList.get(i);  
	               if(!(deptTemp.getId()).equals(dept.getId()) && !(deptTemp.getId()).equals(targetDept.getId())){  
	            	    deptTemp.setSort(deptTemp.getSort()+1); 
	                    deptService.update(deptTemp);  
	               }  
	          }  
	          deptService.update(dept);  
	          deptService.update(targetDept);
	          // 向后移动  
	     }else if(moveType.equals("next")){
	    	  paramsMap.put("parentId", dept.getParentId());
	    	  paramsMap.put("sort", dept.getSort());
	    	  paramsMap.put("targetSort", targetDept.getSort());  
	          List<DeptEntity> deptList = deptService.getDeptListBySort(paramsMap); 
	          int sort = targetDept.getSort();  
	          dept.setSort(sort);
	          if(sort-1<0){
	        	  targetDept.setSort(0);
	          }else{
	        	  targetDept.setSort(sort - 1);  
	          }
	          // 更新所有受影响的排序字段 
	          for (int i = 0; i < deptList.size(); i++) { 
	        	  DeptEntity deptTemp = deptList.get(i);  
	               if(!(deptTemp.getId()).equals(dept.getId()) && !(deptTemp.getId()).equals(targetDept.getId())){  
	            	   	if(deptTemp.getSort()-1<0){
	            	   		deptTemp.setSort(0);
	            	   	}else{
	            	   		deptTemp.setSort(deptTemp.getSort()-1);  
	            	   	}
	            	    deptService.update(deptTemp); 
	               }  
	          }  
	          deptService.update(dept);  
	          deptService.update(targetDept);  
	     }else{  
	          //  
	     }  
		successJson(1);
	}
}
