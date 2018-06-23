package com.xhgx.web.admin.message.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xhgx.web.admin.message.entity.Message;
import com.xhgx.sdk.dao.GenericDao;

 /**
 * @ClassName MessageDao
 * @Description tb_sys_message 持久层接口
 * @author <font color='blue'>ryh</font> 
 * @date 2017-11-08 15:10:05
 * @version 1.0
 */
public interface MessageDao extends  GenericDao {
	
	/**根据消息id查询所有消息接收者的用户信息*/
	public List<HashMap> getUsersByMsgId(String id);
	/**根据用户id查询所有接收到的信息*/
	public List<HashMap> getMsgByUserId(Map<String, Object> params);
	/**根据用户id条件查询所有接收到的信息的数量*/
	public int countMsgByUserId(Map<String, Object> params);
	/**根据用户id查询所有未读信息的数量*/
	public int countUnMsgByUserId(String userId);
}
