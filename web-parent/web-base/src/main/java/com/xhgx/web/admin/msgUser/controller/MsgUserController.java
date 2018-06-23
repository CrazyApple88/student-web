package com.xhgx.web.admin.msgUser.controller;
import com.xhgx.web.admin.msgUser.entity.MsgUser;
import com.xhgx.web.admin.msgUser.service.MsgUserService;
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
 * @ClassName MsgUserController
 * @Description tb_sys_msg_user 控制层
 * @author <font color='blue'>ryh</font> 
 * @date 2017-11-08 15:10:05
 * @version 1.0
 */
@Controller("msgUserController")
@RequestMapping("/admin/msgUser")
@Scope("prototype")
public class MsgUserController extends AbstractBaseController{


	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(MsgUser.class);

	
	
	/**Service对象，可以调用其中的业务逻辑方法*/	
	@Autowired
	@Qualifier("msgUserService")
	private MsgUserService msgUserService;
	
	
	
	/**
	 * 统一页面路径
	 * 
	 * @return
	 */	
	public String viewPrefix() {
		return "admin/msgUser/msg_user_";
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
    	msgUserService.findPage(param, page, orderBy);
    	//业务逻辑操作成功，ajax 使用successJson 返回
        successJson(page);
    }
	
	
	/**
     * 信息保存
     * @param entity
     * @param bindingResult
     */
    @RequestMapping("/save")
    public void save(@Valid MsgUser entity, BindingResult bindingResult) {
    	
    	//这里是验证，要依赖实体类的 注解
        if(!validate(bindingResult)) {
        	return;
        }
        msgUserService.save(entity);
        //业务逻辑操作成功，ajax 使用successJson 返回
        successJson(entity);
    }
    /**
     * 将用户的消息状态批量的改为已读
     * @param ids
     */
	@RequestMapping("/modifyMsgStatus")
	public void modifyMsgStatus(String[] ids){
		UserEntity user = (UserEntity) this.getCurrentUser();
		msgUserService.modifyBatchMsgStatus(user.getId(), ids);
		successJson(ids);
	}
	
	/**
	 * 进入新增/编辑页面
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(MsgUser entity) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (MsgUser) msgUserService.get(entity);
		}
		request.setAttribute("entity", entity);
		return this.viewPrefix()+"edit";
	}
	
	
	 /**
     * 修改信息操作
     * @param entity
     * @param bindingResult
     */
    @RequestMapping("/update")
    public void update(@Valid  MsgUser entity, BindingResult bindingResult) {
    	//这里是手动验证，错误信息用 errorJson 返回，这里是ajax请求
    	if(!validate(bindingResult)){
    		return;
    	} 
        msgUserService.update(entity);
        //业务逻辑操作成功，ajax 使用successJson 返回
        successJson(entity);
    }
	
	 /**
     * 删除信息操作
     * @param entity   
     */
	@RequestMapping("/delete")
    public void delete(MsgUser entity) {        
    	 msgUserService.delete(entity);
        //业务逻辑操作成功，ajax 使用successJson 返回
        successJson(entity);
    }
	
	
	/**
	 * 批量删除	
	 * @param ids
	 */
	@RequestMapping("/deleteBatch")
	public void deleteBatch(String[] ids) {
		 msgUserService.deleteBatch(ids);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(ids);
	}
	
}
