package com.xhgx.web.admin.message.controller;
import com.xhgx.web.admin.company.service.CompanyService;
import com.xhgx.web.admin.dept.service.DeptService;
import com.xhgx.web.admin.message.entity.Message;
import com.xhgx.web.admin.message.service.MessageService;
import com.xhgx.web.admin.msgUser.entity.MsgUser;
import com.xhgx.web.admin.msgUser.service.MsgUserService;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.admin.user.service.UserService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;
import com.xhgx.commons.lang.GlobleConfig;
import com.xhgx.sdk.entity.Page;
import com.xhgx.sdk.entity.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
 * @ClassName MessageController
 * @Description tb_sys_message 控制层
 * @author <font color='blue'>ryh</font> 
 * @date 2017-11-08 15:10:04
 * @version 1.0
 */
@Controller("messageController")
@RequestMapping("/admin/message")
@Scope("prototype")
public class MessageController extends AbstractBaseController{

	public Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
			.create();
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Message.class);

	
	
	/**Service对象，可以调用其中的业务逻辑方法*/	
	@Autowired
	@Qualifier("messageService")
	private MessageService messageService;
	
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public MsgUserService msgUserService;
	/**
	 * 统一页面路径
	 * 
	 * @return
	 */	
	public String viewPrefix() {
		return "admin/message/message_";
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
    	messageService.findPage(param, page, orderBy);
    	//业务逻辑操作成功，ajax 使用successJson 返回
    	successJson(page);
    }
	/**
	 * 进入我的消息页面
	 * 
	 * @return
	 */
	@RequestMapping("/getMsgByUserIdPage")
	public String getMsgByUserIdPage(){
		return this.viewPrefix()+"personal";
	}
	/**
	 * 分页查询功能
	 * 
	 * @param page
	 * @param orderBy
	 * @return
	 */
	@RequestMapping("/getMsgByUserId")
	public void getMsgByUserId(Page page,String orderBy){
		UserEntity user = (UserEntity) this.getCurrentUser();
		Map<String,Object> params = RequestSearch.getSearch(request);
		if (StringUtils.isBlank(orderBy)) {
			orderBy = " create_time desc";
		}
		messageService.findPage(params, page, orderBy);
		params.put("userId", user.getId());
		//System.out.println(params.get("status"));
		int count = messageService.countMsgByUserId(params);
		page.setPageCount(count);
		page.setResult(messageService.getMsgByUserId(params));
		int size = page.getPageSize();
		int pageMax = count % size == 0 ? (count / size) : (count / size + 1);
		page.setPageTotal(pageMax);
    	//System.out.println("base::pagecount:"+page.getPageCount()+",pageno:"+page.getPageNo()+",pagesize:"+page.getPageSize()+",pageTotal:"+page.getPageTotal());
		successJson(page);
	}
	
	/**
     * 信息保存
     * @param entity
     * @param bindingResult
     */
    @RequestMapping("/save")
    public void save(@Valid Message entity, BindingResult bindingResult) {
    	
    	//这里是验证，要依赖实体类的 注解
        if(!validate(bindingResult)){
        	return;
        }
        UserEntity user = (UserEntity) this.getCurrentUser();
        entity.setCreateBy(user.getRealName());
        entity.setType("0");
        entity.setCreateTime(new Date());
        String userIds = entity.getUserIds();
        String[] ids=userIds.split(",");
        //messageService.save(entity);
        //msgUserService.saveByUserIds(ids, entity.getId());
        //业务逻辑操作成功，ajax 使用successJson 返回
        messageService.saveAndPublish(entity, ids);
        successJson(entity);
    }
    /**
	 * 根据用户id查询所有未读信息的数量
	 */
    @RequestMapping("/countUnMsgByUserId")
	public void countUnMsgByUserId(){
		UserEntity user = (UserEntity) this.getCurrentUser();
		int count=0;
		count = messageService.countUnMsgByUserId(user.getId());
		successJson(count);
	}
	
	/**
	 * 进入新增/编辑页面
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(Message entity) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (Message) messageService.get(entity);
			entity.setUsers(messageService.getUsersByMsgId(entity.getId()));
		}
		request.setAttribute("entity", entity);
		return this.viewPrefix() + "edit";
	}
	/**
	 * 查询同一企业下的所有用户，根据部门显示
	 * 进入用户选择页面
	 */
	@RequestMapping("/getUserByDept")
	public String getUserByDept() {
		List<HashMap> list = null;
		UserEntity user = (UserEntity) this.getCurrentUser();
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("compId", user.getCompId());
		paramsMap.put("userId", user.getId());
		//获取该企业下所有部门信息
		list = userService.getDeptsLeftJoinUserChecked(paramsMap);
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
		//获取该企业下所有不隶属于任何部门的用户信息
		List<HashMap> listUser = userService.getUsersByCompNotDept(paramsMap);
		for (HashMap hashMap : listUser) {
			HashMap map = new HashMap();
			map.put("id", hashMap.get("USERID"));
			map.put("pId",hashMap.get("ID"));
			map.put("name",hashMap.get("REALNAME"));
			map.put("open", true);
			map.put("checked", false);
			maps.add(map);
		}
		//获取该企业下所有隶属于部门的用户及对应的部门信息
		List<HashMap> listUser2 = userService.getUserByCompInDept(paramsMap);
		if(listUser2!=null){
			for (HashMap hashMap : listUser2) {
				HashMap map = new HashMap();
				map.put("id", hashMap.get("USERID"));
				map.put("pId", hashMap.get("ID"));
				map.put("name", hashMap.get("REALNAME"));
				map.put("open", true);
				map.put("checked", false);
				maps.add(map);
			}
		}
		String json = gson.toJson(maps);
		request.setAttribute("jsonTree", json);
		return "admin/message/msg_user_choose";
	}
	
	 /**
     * 修改信息操作
     * @param entity
     * @param bindingResult
     */
    @RequestMapping("/update")
    public void update(@Valid  Message entity, BindingResult bindingResult) {
    	//这里是手动验证，错误信息用 errorJson 返回，这里是ajax请求
    	if(!validate(bindingResult)){
    		return;
    	} 
    	//如果没有点击用户选择，没有改变消息接收者，则只需更新tb_sys_message表即可
    	if(entity.getUserIds()==null||("").equals(entity.getUserIds())){
    		messageService.update(entity);
    	}else{
    		String userIds =entity.getUserIds();
			String[] ids = userIds.split(",");
			messageService.updateAndPublish(entity, ids);
    	}
        //业务逻辑操作成功，ajax 使用successJson 返回
        //messageService.update(entity);
		successJson(entity);
    }
	
	 /**
     * 删除信息操作
     * @param entity   
     */
	@RequestMapping("/delete")
    public void delete(Message entity) {        
    	 messageService.deleteAndPublish(entity);
        //业务逻辑操作成功，ajax 使用successJson 返回
        successJson(entity);
    }
	
	
	/**
	 * 批量删除	
	 * @param ids
	 */
	@RequestMapping("/deleteBatch")
	public void deleteBatch(String[] ids) {
		 messageService.deleteBatchAndPublish(ids);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		 successJson(ids);
	}
	
	/**
	 * 进入查看页面
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("/seePage")
	public String seePage(Message entity,String type) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (Message) messageService.get(entity);
			//如果entity.uers中没值，则根据消息id查询所有消息接收者的信息
			if(entity.getUsers()==null||entity.getUsers().equals("")){
				entity.setUsers(messageService.getUsersByMsgId(entity.getId()));
			}
		}
		//如果type!=null,即是进入我的消息页面，当点击查看时需改变消息的状态，从未读改为已读
		UserEntity user = (UserEntity) this.getCurrentUser();
		if(type!=null){
			MsgUser msgUser=new MsgUser();
			msgUser.setMsgId(entity.getId());
			msgUser.setUserId(user.getId());
			msgUserService.modifyMsgStatus(msgUser);
		}
		request.setAttribute("type", type);
		request.setAttribute("entity", entity);
		return this.viewPrefix() + "see";
	}
	

	
}
