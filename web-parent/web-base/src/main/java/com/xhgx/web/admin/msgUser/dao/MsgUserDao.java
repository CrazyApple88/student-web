package com.xhgx.web.admin.msgUser.dao;
import java.util.HashMap;
import java.util.List;

import com.xhgx.web.admin.msgUser.entity.MsgUser;
import com.xhgx.sdk.dao.GenericDao;

 /**
 * @ClassName MsgUserDao
 * @Description tb_sys_msg_user 持久层接口
 * @author <font color='blue'>ryh</font> 
 * @date 2017-11-08 15:10:05
 * @version 1.0
 */
public interface MsgUserDao extends  GenericDao {

	public void deleteByMsgId(String msgId);
	/**改变消息的状态，从未读改成已读*/
	public void modifyMsgStatus(MsgUser msgUser);
}
