package com.xhgx.web.admin.msgUser.entity;
import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

 /**
 * @ClassName MsgUser
 * @Description tb_sys_msg_user实体信息
 * @author <font color='blue'>ryh</font> 
 * @date 2017-11-08 15:10:05
 * @version 1.0
 */
@AutoUUID
public class MsgUser extends BaseEntity<String>{
	
	/** serialVersionUID */
	
	private static final long serialVersionUID = 4841799362659062357L;
	
	/**序号*/
	private String id;
	/**消息接收者*/
	private String userId;
	/**消息id*/
	private String msgId;
	/**消息状态*/
	private String status;

	

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * <b>方法名</b>：getId<br>
	 * <b>功能</b>：取得序号<br>
	 * 
	 * @author <font color='blue'>ryh</font> 
	 * @date  2017-11-08 15:10:05
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public String getMsgId() {
		return this.msgId;
	}
	
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}


