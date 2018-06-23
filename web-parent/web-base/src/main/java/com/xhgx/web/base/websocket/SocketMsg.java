package com.xhgx.web.base.websocket;

/**
 * @ClassName SocketMsg
 * @Description 推送消息类
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class SocketMsg {
	/**业务id*/
	private String event;
	/**消息的具体内容*/
	private Object data;		
	
	public SocketMsg(){}
	public SocketMsg(String event, Object data) {
		super();
		this.event = event;
		this.data = data;
	}

	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
