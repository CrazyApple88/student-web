package com.xhgx.web.admin.message.entity;
import java.util.Date;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

 /**
 * @ClassName Message
 * @Description  tb_sys_message实体信息
 * @author <font color='blue'>ryh</font> 
 * @date 2017-11-08 15:10:05
 * @version 1.0
 */
@AutoUUID
public class Message extends BaseEntity<String>{
	
	/** serialVersionUID */
	
	private static final long serialVersionUID = 4841799362659062357L;
	
	/**消息id*/
	private String id;
	/**消息标题*/
	private String title;
	/**消息内容*/
	private String content;
	/**消息类型*/
	private String type;
	/**接收类型，0代表web，1代表安卓，2代表ios*/
	private String receiveType;
	/**消息发送者*/
	private String createBy;
	/**创建时间*/
	private Date createTime;
	/**消息接收者名单，临时字段*/
	private String users; 
	/**消息接收者名单id，临时字段*/
	private String userIds; 
	/**消息状态，0未读1已读，临时字段*/
	private String status;
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * <b>方法名</b>：getId<br>
	 * <b>功能</b>：取得消息id<br>
	 * 
	 * @author <font color='blue'>ryh</font> 
	 * @date  2017-11-08 15:10:05
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getCreateBy() {
		return this.createBy;
	}
	
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}


