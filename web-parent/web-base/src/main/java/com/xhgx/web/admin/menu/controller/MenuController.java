package com.xhgx.web.admin.menu.controller;

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
import com.xhgx.web.admin.config.service.ConfigHelper;
import com.xhgx.web.admin.dept.entity.DeptEntity;
import com.xhgx.web.admin.dept.service.DeptService;
import com.xhgx.web.admin.dict.entity.DictEntity;
import com.xhgx.web.admin.dicttype.service.DictHelper;
import com.xhgx.web.admin.files.entity.FilesEntity;
import com.xhgx.web.admin.files.service.FilesService;
import com.xhgx.web.admin.menu.entity.MenuCenterEntity;
import com.xhgx.web.admin.menu.entity.MenuEntity;
import com.xhgx.web.admin.menu.service.MenuCenterService;
import com.xhgx.web.admin.menu.service.MenuService;
import com.xhgx.web.admin.menuIcon.entity.MenuIcon;
import com.xhgx.web.admin.menuIcon.service.MenuIconService;
import com.xhgx.web.admin.role.entity.RoleEntity;
import com.xhgx.web.admin.role.service.RoleService;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.admin.user.service.UserService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName MenuController
 * @Description 菜单权限Controller
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */

@RequestMapping("/admin/menu")
@Controller("menuController")
@Scope("prototype")
public class MenuController extends AbstractBaseController {

	public Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
			.create();

	@Autowired
	public MenuService menuService;

	@Autowired
	public UserService userService;

	@Autowired
	public RoleService roleService;

	@Autowired
	public DeptService deptService;

	@Autowired
	public FilesService filesService;

	@Autowired
	public MenuCenterService menuCenterService;
	
	@Autowired
	private MenuIconService menuIconService;
	
	/**
	 * 进入分页查询页面
	 * 
	 * @param page
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getMenuQueryPage")
	public String getMenuQueryPage(Page page, BindingResult bindingResult) {
		return "admin/menu/menu_query";
	}
	/**
	 * 进入图标选择页面
	 * @param page
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("/getIconPage")
	public String getIconPage(Page page, BindingResult bindingResult) {
		//获取菜单图标类型的种类
		List<DictEntity> iconTypeList = DictHelper.getInstance().get("menu_icon_type");
	    request.setAttribute("iconTypeList", iconTypeList);
		//获取所有的菜单图标信息
		Map<String,Object> param = new HashMap<String,Object>();
		String orderBy="type_id";
		List<MenuIcon> menuIconList = menuIconService.findList(param, orderBy);
		request.setAttribute("menuIconList", menuIconList);
		return "admin/menu/menu_icon";
	}

	/**
	 * 进入新增/编辑页面
	 * 
	 * @param menu
	 * @param parentName
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getMenuEditPage")
	public String getMenuEditPage(MenuEntity menu, String parentName,
			BindingResult bindingResult) {
		String type = "edit";
		// 如果是修改页面
		if (!StringUtils.isBlank(menu.getId())) {
			menu = (MenuEntity) menuService.get(menu);
			Map<String, String> param = new HashMap<String, String>();
			param.put("relationId", menu.getId());
			List<FilesEntity> list = filesService.findList(param, "");
			if (list != null && list.size() > 0) {
				request.setAttribute("fileInfo", list.get(0));
			}
		} else {// 如果是新增页面
			if (!StringUtils.isBlank(menu.getParentId())) {
				MenuEntity parentMenu = new MenuEntity();
				parentMenu.setId(menu.getParentId());
				parentMenu = (MenuEntity) menuService.get(parentMenu);
				request.setAttribute("parentMenu", parentMenu);
				// 如果父级菜单权限是链接菜单
				if ("3".equals(parentMenu.getMenuType())) {
					// 页面上的选择按钮菜单
					menu.setMenuType("99");
				}
				menu.setSort(100);
				type = "add";
			}
		}
		request.setAttribute("menu", menu);
		request.setAttribute("type", type);
		return "admin/menu/menu_edit";
	}

	/**
	 * 分页查询
	 * 
	 * @param menu
	 *            查询条件实体对象
	 * @param page
	 *            分页条件
	 * @param orderBy
	 *            排序条件
	 * @param bindingResult
	 */
	@RequestMapping("/findPage")
	public void findPage(MenuEntity menu, Page page, String orderBy,
			BindingResult bindingResult) {
		Map<String, Object> param = RequestSearch.getSearch(request);
		if (StringUtils.isBlank(orderBy)) {
			orderBy = " levelnum asc, sort asc";
		}
		menuService.findPage(param, page, orderBy);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(page);
	}

	/**
	 * 进入菜单树查询页面
	 * 
	 * @param menu
	 * @return String
	 */
	@RequestMapping("/getMenuTreeQueryPage")
	public String getMenuTreeQueryPage(MenuEntity menu) {
		return "admin/menu/menu_tree_query";
	}
	/**
	 * 菜单拖拽后更新相关的菜单的sort信息
	 * @param menu
	 */
	@RequestMapping("/updateSort")
	public void updateSort(MenuEntity menu){
		MenuEntity targetMenu = new MenuEntity();
		targetMenu.setId(menu.getTargetId());
		// 拖拽时选中的目标菜单
		targetMenu = (MenuEntity) menuService.get(targetMenu);	
		String moveType = menu.getMoveType();
		menu = (MenuEntity) menuService.get(menu);
		// 参数集合
		Map<String,Object> paramsMap = new  HashMap<String, Object>(); 
		// 向前移动  
	    if (moveType.equals("prev")) {
	    	  paramsMap.put("parentId", menu.getParentId());
	    	  paramsMap.put("sort", menu.getSort());
	    	  paramsMap.put("targetSort", targetMenu.getSort());  
	          List<MenuEntity> menuList = menuService.getMenuListBySort(paramsMap);  
	          int sort = targetMenu.getSort() + 1;  
	          menu.setSort(targetMenu.getSort());  
	          targetMenu.setSort(sort);  
	          // 更新所有受影响的排序字段 
	          for (int i = 0; i < menuList.size(); i++) { 
	        	  MenuEntity menuTemp = menuList.get(i);  
	               if(!(menuTemp.getId()).equals(menu.getId()) && !(menuTemp.getId()).equals(targetMenu.getId())){  
	                    menuTemp.setSort(menuTemp.getSort()+1); 
	                    menuService.update(menuTemp);  
	               }  
	          }  
	          menuService.update(menu);  
	          menuService.update(targetMenu);
	       // 向后移动  
	     }else if(moveType.equals("next")){
	    	  paramsMap.put("parentId", menu.getParentId());
	    	  paramsMap.put("sort", menu.getSort());
	    	  paramsMap.put("targetSort", targetMenu.getSort());  
	          List<MenuEntity> menuList = menuService.getMenuListBySort(paramsMap); 
	          int sort = targetMenu.getSort();  
	          menu.setSort(sort);
	          if(sort-1<0){
	        	  targetMenu.setSort(0);
	          }else{
	        	  targetMenu.setSort(sort - 1);  
	          }
	       // 更新所有受影响的排序字段
	          for (int i = 0; i < menuList.size(); i++) {  
	        	  MenuEntity menuTemp = menuList.get(i);  
	               if(!(menuTemp.getId()).equals(menu.getId()) && !(menuTemp.getId()).equals(targetMenu.getId())){  
	            	   	if(menuTemp.getSort()-1<0){
	            	   		menuTemp.setSort(0);
	            	   	}else{
	            	   		menuTemp.setSort(menuTemp.getSort()-1);  
	            	   	}
	            	    menuService.update(menuTemp); 
	               }  
	          }  
	          menuService.update(menu);  
	          menuService.update(targetMenu);  
	     }else{  
	          //  
	     }  
		successJson(1);
	}
	/**
	 * 获取菜单树信息(采用异步加载，可以进行异步刷新树)
	 * 
	 * @param menu
	 * @param bindingResult
	 */
	@RequestMapping("/getMenuTreeQuery")
	public void getMenuTreeQuery(@Valid MenuEntity menu,
			BindingResult bindingResult) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		// paramsMap.put("useable", "1");//菜单树，查询素有的，包括不可用的
		List<HashMap> list = menuService.queryMenuTreeByParams(paramsMap);

		List<HashMap> maps = new ArrayList<HashMap>();
		for (HashMap hashMap : list) {
			HashMap map = new HashMap();
			map.put("id", hashMap.get("ID"));
			map.put("pId", hashMap.get("PID"));
			map.put("name", hashMap.get("NAME"));
			map.put("title", hashMap.get("TITLE"));
			map.put("open", true);
			maps.add(map);
		}
		successJson(maps);
	}

	/**
	 * 菜单信息保存
	 * 
	 * @param menu
	 * @param bindingResult
	 */
	@RequestMapping("/save")
	public void save(@Valid MenuEntity menu, BindingResult bindingResult) {
		OnlineUser user = this.getCurrentUser();
		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		// 如果菜单的父ID不为空
		if (!StringUtils.isBlank(menu.getParentId())) {
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("parentId", menu.getParentId());
			// 查出父ID的菜单信息
			Map<String, Object> map = menuService.queryMenuParentId(paramsMap);
			if (map.get("LEVELNUM") != null) {
				String le = map.get("LEVELNUM").toString();
				menu.setLevelnum(Integer.parseInt(le) + 1);
			}
			boolean flag = map.get("MAXMENUCODE") != null
					&& (!StringUtils.isBlank(map.get("MAXMENUCODE") + "") && (map
							.get("MAXMENUCODE") + "").length() >= 3);
			if (flag) {
				String maxMenuCode = map.get("MAXMENUCODE") + "";
				String newMenuCode = ObjAnalysis.getNewCodeByCode(maxMenuCode,
						3);
				menu.setMenuCode(newMenuCode);
			} else {
				menu.setMenuCode(map.get("MENUCODE") + "001");
			}

		} else {
			errorJson(getMessage("system.management.text.not.find.current.menu.parent.menu","没有找到当前菜单的父菜单"));
		}
		menu.setCreateBy(user.getLoginName());
		menu.setCreateDate(new Date());
		menuService.save(menu);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(menu);
	}

	/**
	 * 修改菜单信息
	 * 
	 * @param menu
	 * @param bindingResult
	 */
	@RequestMapping("/update")
	public void update(@Valid MenuEntity menu, BindingResult bindingResult) {
		if (!validate(bindingResult)){
			return;
		}
		menuService.update(menu);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(menu);
	}

	/**
	 * 删除菜单信息
	 * 
	 * @param menu
	 * @param bindingResult
	 */
	@RequestMapping("/delete")
	public void delete(MenuEntity menu, BindingResult bindingResult) {

		menuService.deleteByMenuId(menu);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(menu);
	}

	/**
	 * 进入权限授权页面
	 * 
	 * @param menuCenter
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getMenuCenterAssignmentPage")
	public String getMenuCenterAssignmentPage(MenuCenterEntity menuCenter,
			BindingResult bindingResult) {
		String relationName = "";
		String relationId = menuCenter.getRelationId();
		// 如果是从用户授权页面进入
		if ("1".equals(menuCenter.getRelationType())) {
			UserEntity user = (UserEntity) userService.get(relationId);
			if (user != null) {
				relationName = user.getUserName();
			} else {
				relationId = "";
			}
		} else if ("2".equals(menuCenter.getRelationType())) {
			RoleEntity role = (RoleEntity) roleService.get(relationId);
			if (role != null) {
				relationName = role.getRoleName();
			} else {
				relationId = "";
			}
		} else if ("3".equals(menuCenter.getRelationType())) {
			DeptEntity dept = (DeptEntity) deptService.get(relationId);
			if (dept != null) {
				relationName = dept.getDeptName();
			} else {
				relationId = "";
			}
		}
		request.setAttribute("relationType", menuCenter.getRelationType());
		request.setAttribute("relationId", relationId);
		request.setAttribute("relationName", relationName);
		return "admin/menu/menu_center_assignment";
	}

	/**
	 * 进入权限授权页面,分别查询可访问页面的权限 和 可授权的权限列表
	 * 
	 * @param selectId
	 * @param selectType
	 */
	@RequestMapping("/getMenuTreeByCenterId")
	public void getMenuTreeByCenterId(String selectId, String selectType) {
		OnlineUser user = this.getCurrentUser();
		if (StringUtils.isBlank(selectId) || StringUtils.isBlank(selectType)) {
			errorJson(getMessage("system.management.text.not.find.associated.information","没有找到的关联信息！"));
		}
		List<HashMap> listAccess = null;
		List<HashMap> listAuth = null;

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("relationName", "");
		paramsMap.put("relationId", selectId);
		paramsMap.put("relationType", selectType);
		// 超级个管理员账号
		String superAdmin = ConfigHelper.getInstance().get("super.administrator");
		// 如果是超级管理员则进行所有菜单授权操作
		if (superAdmin.equals(user.getLoginName())) {
			// 查询访问权限信息列表树
			paramsMap.put("authType", "1");
			// 获取访问权限菜单
			listAccess = menuService.getMenusLeftJoinChecked(paramsMap);
			// 查询授权权限信息列表树
			paramsMap.put("authType", "2");
			// 获取授权权限菜单
			listAuth = menuService.getMenusLeftJoinChecked(paramsMap);
		} else {
			// 其他用户则只可以授权他所拥有的权限
			// 查询访问权限信息列表树
			paramsMap.put("authType", "1");
			// 当前登录用户的ID
			paramsMap.put("loginUserId", user.getId());
			// 获取访问权限菜单
			listAccess = menuService.getMenusLeftJoinChecked(paramsMap);
			// 查询授权权限信息列表树
			paramsMap.put("authType", "2");
			// 当前登录用户的ID
			paramsMap.put("loginUserId", user.getId());
			// 获取授权权限菜单
			listAuth = menuService.getMenusLeftJoinChecked(paramsMap);
		}
		// 将list放入Map中，生成统一的json
		Map<String, Object> mapList = new HashMap<String, Object>();
		// 访问权限
		List<HashMap> mapsAccess = new ArrayList<HashMap>();
		for (HashMap hashMap : listAccess) {
			HashMap map = new HashMap();
			map.put("id", hashMap.get("ID"));
			map.put("pId", hashMap.get("PID"));
			map.put("name", hashMap.get("NAME"));
			map.put("title", hashMap.get("TITLE"));
			map.put("checked", hashMap.get("CHECKED") != null ? true : false);
			map.put("menu_id", hashMap.get("MENU_ID"));
			map.put("chkDisabled", hashMap.get("MENU_ID") != null ? false
					: true);
			map.put("open", true);
			mapsAccess.add(map);
		}
		mapList.put("listAccess", mapsAccess);
		// 授权权限
		List<HashMap> mapsAuth = new ArrayList<HashMap>();
		for (HashMap hashMap : listAuth) {
			HashMap map = new HashMap();
			map.put("id", hashMap.get("ID"));
			map.put("pId", hashMap.get("PID"));
			map.put("name", hashMap.get("NAME"));
			map.put("title", hashMap.get("TITLE"));
			map.put("checked", hashMap.get("CHECKED") != null ? true : false);
			map.put("menu_id", hashMap.get("MENU_ID"));
			map.put("chkDisabled", hashMap.get("MENU_ID") != null ? false
					: true);
			map.put("open", true);
			mapsAuth.add(map);
		}
		mapList.put("listAuth", mapsAuth);

		successJson(mapList);
	}

	/**
	 * 保存授权信息
	 * 
	 * @param menuCenter
	 */
	@RequestMapping("/saveMenuCenters")
	public void saveMenuCenters(MenuCenterEntity menuCenter) {
		String[] menuIds = menuCenter.getMenuId().split(",");
		List<MenuCenterEntity> list = new ArrayList<MenuCenterEntity>();
		for (String menuId : menuIds) {
			if (StringUtils.isBlank(menuId)){
				continue;
			}
				
			MenuCenterEntity mc = new MenuCenterEntity();
			mc.setMenuId(menuId);
			mc.setRelationId(menuCenter.getRelationId());
			mc.setRelationType(menuCenter.getRelationType());
			mc.setAuthType(menuCenter.getAuthType());
			list.add(mc);
		}

		Map<String, String> paramsMap = new HashMap<String, String>();
		// 中间关联表关联的ID
		paramsMap.put("relationId", menuCenter.getRelationId());
		// 关联类型：1用户，2角色，3部门
		paramsMap.put("relationType", menuCenter.getRelationType());
		// 1访问权限，2授权权限
		paramsMap.put("authType", menuCenter.getAuthType());
		// 超级个管理员账号
		// ConfigHelper.getInstance().get("super.administrator");
		// if(!superAdmin.equals(user.getLoginName())){//如果是admin则全部进行删除后插入，如果是普通用户则先根据用户当前拥有的授权权限里面进行删除，在新增
		// paramsMap.put("loginUserId", user.getId());//当前登录用户的ID
		// }
		menuCenterService.updateMenuCenters(list, paramsMap);
		successJson("");
	}

	/**
	 * 根据权限编码验证权限编码是否存在
	 * 
	 * @param menu
	 * @param bindingResult
	 */
	@RequestMapping("/validateAuthTab")
	public void validateAuthTab(MenuEntity menu, BindingResult bindingResult) {
		System.out.println(getMessage("system.management.text.verify.AuthTab","验证AuthTab：") + menu.getAuthTab());
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("authTab", menu.getAuthTab());
		List<MenuEntity> list = menuService.findList(paramsMap, null);
		String returnStr = "n";
		if (list != null && list.size() > 0) {
			// 防止出错，如果有超过2个则不能保存，不管是新增还是修改
			if (list.size() > 1) {
				// 用户已存在yes
				returnStr = "y";
				// 如果查出来只有一个，比较传过来的ID和查询出来的ID如果相等，则是修改
			} else if (list.size() == 1&& !list.get(0).getId().equals(menu.getId())) {
				// 用户已存在yes
				returnStr = "y";
			}
		}
		// 用户不存在no
		successJson(returnStr);

	}

	/**
	 * 根据权限编码验证权限编码是否存在
	 * 
	 * @param menu
	 * @param bindingResult
	 */
	@RequestMapping("/validateUrl")
	public void validateUrl(MenuEntity menu, BindingResult bindingResult) {
		System.out.println(getMessage("system.management.text.verify.url","验证url：") + menu.getUrl());
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("url", menu.getUrl());
		List<MenuEntity> list = menuService.findList(paramsMap, null);
		String returnStr = "n";
		if (list != null && list.size() > 0) {
			// 防止出错，如果有超过2个则不能保存，不管是新增还是修改
			if (list.size() > 1) {
				// 用户已存在yes
				returnStr = "y";
				// 如果查出来只有一个，比较传过来的ID和查询出来的ID如果相等，则是修改
			} else if (list.size() == 1
					&& !list.get(0).getId().equals(menu.getId())) {
				// 用户已存在yes
				returnStr = "y";
			}
		}
		// 用户不存在no
		successJson(returnStr);
	}

}
