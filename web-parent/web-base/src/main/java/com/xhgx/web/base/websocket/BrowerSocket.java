package com.xhgx.web.base.websocket;

import javax.websocket.Session;

/**
 * @ClassName BrowerSocket
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class BrowerSocket {
	/**用户id*/
	private String userId;
	/**用户登录后存在session里的httpSessionId*/
	private String httpSessionId;
	/**socketSession*/
	private Session socketSession;
	
	/**默认无参*/
	public BrowerSocket() {
		super();
	}
	
	/**带参数*/
	public BrowerSocket(String userId,String httpSessionId, Session socketSession) {
		super();
		this.userId=userId;
		this.httpSessionId = httpSessionId;
		this.socketSession = socketSession;
	}
	public String getHttpSessionId() {
		return httpSessionId;
	}
	public void setHttpsessionId(String httpSessionId) {
		this.httpSessionId = httpSessionId;
	}
	
	public Session getSocketSession() {
		return socketSession;
	}

	public void setSocketSession(Session socketSession) {
		this.socketSession = socketSession;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
