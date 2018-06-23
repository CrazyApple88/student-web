package com.xhgx.web.admin.message.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xhgx.web.admin.message.entity.Message;
import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;

 /**
 * @ClassName MessageService
 * @Description tb_sys_message 业务逻辑层接口
 * @author <font color='blue'>ryh</font> 
 * @date 2017-11-08 15:10:05
 * @version 1.0
 */
 public interface MessageService extends BaseService,CacheService{
	 
	 /**
	 * 通过id快速加载对象
	 * 
	 * @param messageId
	 * @return
	 */
	 public Message getMessage(String messageId);
	 /**根据消息id查询所有消息接收者的用户信息*/
	 public String getUsersByMsgId(String id);
	 /**根据用户id查询所有接收到的信息*/
	 public List<Message> getMsgByUserId(Map<String, Object> params);
	 /**根据用户id查询所有未读信息的数量*/
	 public int countUnMsgByUserId(String userId);
	 /**根据用户id条件查询所有接收到的信息的数量*/
	 public int countMsgByUserId(Map<String, Object> params);
	 /**根据消息id删除消息，并删除对应消息用户表tb_sys_msg_user中的数据*/
	 public void deleteAndPublish(Message msg);
	 /**根据消息id批量删除消息，并批量删除对应消息用户表tb_sys_msg_user中的数据*/
	 public void deleteBatchAndPublish(String[] ids);
	 /**保存消息到数据库，并向消息用户表tb_sys_msg_user中添加对应的数据*/
	 public void saveAndPublish(Message msg,String[] ids);
	 /**对消息进行更新，并更新相应的消息用户表tb_sys_msg_user中的数据*/
	 public void updateAndPublish(Message msg,String[] ids);
	  
}