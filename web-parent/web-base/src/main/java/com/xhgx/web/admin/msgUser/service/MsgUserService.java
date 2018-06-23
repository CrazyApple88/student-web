package com.xhgx.web.admin.msgUser.service;
import com.xhgx.web.admin.msgUser.entity.MsgUser;
import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;

 /**
 * @ClassName MsgUserService
 * @Description tb_sys_msg_user 业务逻辑层接口
 * @author <font color='blue'>ryh</font> 
 * @date 2017-11-08 15:10:05
 * @version 1.0
 */
 public interface MsgUserService extends BaseService,CacheService{
	 
	 /**
	 * 通过id快速加载对象
	 * 
	 * @param msgUserId
	 * @return
	 */
	 public MsgUser getMsgUser(String msgUserId);
	 /**根据消息id删除消息*/
	 public void deleteByMsgId(String msgId);
	 /**改变消息的状态，从未读改成已读*/
	 public void modifyMsgStatus(MsgUser msgUser);
	 /**批量改变消息的状态，从未读改为已读*/
	 public void modifyBatchMsgStatus(String userId,String[] ids);
}