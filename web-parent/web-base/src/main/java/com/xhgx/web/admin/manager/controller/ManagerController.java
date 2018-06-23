package com.xhgx.web.admin.manager.controller;
import com.xhgx.web.admin.manager.entity.Manager;
import com.xhgx.web.admin.manager.service.ManagerService;
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
 * @ClassName: ManagerController
 * @Description: tb_sys_manager 控制层
 * @author: <font color='blue'>swx</font> 
 * @date:  2018-05-17 13:51:43
 * @version: 1.0
 * 
 */
@Controller("managerController")
@RequestMapping("/admin/manager")
@Scope("prototype")
public class ManagerController extends AbstractBaseController{

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Manager.class);
	
	/*Service对象，可以调用其中的业务逻辑方法*/	
	@Autowired
	@Qualifier("managerService")
	private ManagerService managerService;
	
	/**
	 * 统一页面路径
	 * 
	 * @return
	 */	
	public String viewPrefix() {
		return "admin/manager/manager_";
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
    	managerService.findPage(param, page, orderBy);
    	//业务逻辑操作成功，ajax 使用successJson 返回
        successJson(page);
    }
	
	/**
     * 信息保存
     * @param entity
     * @param bindingResult
     */
    @RequestMapping("/save")
    public void save(@Valid Manager entity, BindingResult bindingResult) {
    	UserEntity user = (UserEntity) this.getCurrentUser();
    	//这里是验证，要依赖实体类的 注解
        if(!validate(bindingResult)) {
        	return;
        }
    	if(!StringUtils.isBlank(entity.getLogo())){
    		entity.setLogo("/admin/files/getFileByte?id="+entity.getLogo());
    	}
		entity.setCompId(user.getCompId());
        entity.setCreateDt(new Date());
        entity.setState(0);
        managerService.save(entity);
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
	public String editPage(Manager entity) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (Manager) managerService.get(entity);
		}
		
		request.setAttribute("entity", entity);
		return this.viewPrefix()+"edit";
	}
	
	 /**
     * 修改信息
     * @param entity
     * @param bindingResult
     */
    @RequestMapping("/update")
    public void update(@Valid  Manager entity, BindingResult bindingResult) {
    	//这里是手动验证，错误信息用 errorJson 返回，这里是ajax请求
    	if(!validate(bindingResult)) {
         	return;
    	}
		entity.setLogo("/admin/files/getFileByte?id="+entity.getLogo());
		entity.setCreateDt(new Date());
        managerService.update(entity);
        //业务逻辑操作成功，ajax 使用successJson 返回
        successJson(entity);
    }
	
	 /**
     * 删除信息操作
     * @param entity   
     */
	@RequestMapping("/delete")
    public void delete(Manager entity) {        
    	 managerService.delete(entity);
        //业务逻辑操作成功，ajax 使用successJson 返回
        successJson(entity);
    }
	
	/**
	 * 批量删除	
	 * @param ids
	 */
	@RequestMapping("/deleteBatch")
	public void deleteBatch(String[] ids) {
		 managerService.deleteBatch(ids);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(ids);
	}
	
	/**
	 * 更新状态
	 */
	@RequestMapping("/updateState")
	public void updateState(String id,Integer state){
		Manager manager = (Manager)managerService.get(id);
		Map<String,Object> param = new HashMap<String, Object>();
		if(state==0){
			manager.setState(1);
		}else{
			manager.setState(0);
		}
		managerService.update(manager);
		successJson(manager);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getLogoByCompId")
	public void getLogoByCompId(){
		UserEntity user = (UserEntity)this.getCurrentUser();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("comp_id", user.getCompId());
		param.put("state", 1);
		List<Manager> list = managerService.findList(param, "create_dt desc");
		Manager manager = new Manager();
		if(list!=null && list.size()>0){
			manager = list.get(0);
		}
		successJson(manager);
		
	}
}
