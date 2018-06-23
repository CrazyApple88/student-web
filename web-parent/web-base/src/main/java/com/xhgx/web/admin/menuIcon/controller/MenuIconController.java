package com.xhgx.web.admin.menuIcon.controller;
import com.xhgx.web.admin.menuIcon.entity.MenuIcon;
import com.xhgx.web.admin.menuIcon.service.MenuIconService;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;
import com.xhgx.sdk.entity.Page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;



import java.util.*;

 /**
 * @ClassName: MenuIconController
 * @Description: tb_sys_menu_icon 控制层
 * @author: <font color='blue'>ryh</font> 
 * @date:  2018-01-18 17:19:32
 * @version: 1.0
 * 
 */
@Controller("menuIconController")
@RequestMapping("/admin/menuIcon")
@Scope("prototype")
public class MenuIconController extends AbstractBaseController{


	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(MenuIcon.class);
	/**Service对象，可以调用其中的业务逻辑方法*/	
	@Autowired
	@Qualifier("menuIconService")
	private MenuIconService menuIconService;
	
	/**
	 * 统一页面路径
	 * 
	 * @return
	 */	
	public String viewPrefix() {
		return "admin/menuIcon/menu_icon_";
	}
	
	
	
	/**
	 * 进入分页查询页面
	 * 
	 * @return
	 */
	@RequestMapping("/queryPage")
	public String queryPage() {
		return this.viewPrefix()+"query";
	}
	
	/**
	 * 分页查询功能
	 * 
	 * @param page
	 * @param orderBy
	 * @return
	 */
	@RequestMapping("/findPage")
    public void findPage(Page page, String orderBy) {
    	Map<String,Object> param = RequestSearch.getSearch(request);    	
    	menuIconService.findPage(param, page, orderBy);
    	//业务逻辑操作成功，ajax 使用successJson 返回
        successJson(page);
    }
	
	
	/**
     * 信息保存
     * @param entity
     * @param bindingResult
     */
    @RequestMapping("/save")
    public void save(@Valid MenuIcon entity, BindingResult bindingResult) {
    	
    	//这里是验证，要依赖实体类的 注解
        if(!validate(bindingResult)){
        	return;
        } 
        UserEntity user = (UserEntity) this.getCurrentUser();
        entity.setCreateBy(user.getUserName());
        entity.setCreateTime(new Date());
        menuIconService.save(entity);
        //业务逻辑操作成功，ajax 使用successJson 返回
        successJson(entity);
    }
	
	
	/**
	 * 进入新增/编辑页面
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(MenuIcon entity) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (MenuIcon) menuIconService.get(entity);
		}
		request.setAttribute("entity", entity);
		return this.viewPrefix()+"edit";
	}
	
	
	 /**
     * 修改用户信息
     * @param entity
     * @param bindingResult
     */
    @RequestMapping("/update")
    public void update(@Valid  MenuIcon entity, BindingResult bindingResult) {
    	//这里是手动验证，错误信息用 errorJson 返回，这里是ajax请求
    	if(!validate(bindingResult)){
    		return;
    	} 
        menuIconService.update(entity);
        //业务逻辑操作成功，ajax 使用successJson 返回
        successJson(entity);
    }
	
	 /**
     * 删除信息操作
     * @param entity   
     */
	@RequestMapping("/delete")
    public void delete(MenuIcon entity) {        
    	 menuIconService.delete(entity);
        //业务逻辑操作成功，ajax 使用successJson 返回
        successJson(entity);
    }
	
	
	/**
	 * 批量删除	
	 * @param ids
	 */
	@RequestMapping("/deleteBatch")
	public void deleteBatch(String[] ids) {
		 menuIconService.deleteBatch(ids);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(ids);
	}
	
	
	

	
}
