package com.xhgx.web.base.websocket;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * @ClassName WebEventPusher
 * @Description web 端页面事件推送
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class WebEventPusher {
	
	/**
	 * 日志文件
	 */
	private static final Logger log = LoggerFactory.getLogger(WebEventPusher.class);
	/**
	 * 推送web 端 页面触发事件
	 * @param userId
	 * @param SocketMsg pushMsg
	 */
	public static void pushWebEventToUser(String userId,SocketMsg socketMsg){
		try{
			// 定义消息事件数据
			JSONObject jsonObject = JSONObject.fromObject(socketMsg);
		    String str = jsonObject.toString();
		    // 推送消息事件
			SocketMessageSender.pushToUser(userId, str);
		}catch(Exception e){
			log.info("WebEventPusher exception="+e);
		}
	}
	/**
	 * 给所有的在线用户推送消息事件
	 * @param SocketMsg pushMsg
	 */
	public static void pushWebEventToAll(SocketMsg socketMsg){
		try{
			JSONObject jsonObject = JSONObject.fromObject(socketMsg);
		    String str = jsonObject.toString();
		    List<BrowerSocket> list = SocketConnectPool.getAllSocketList();
		    for (BrowerSocket browerSocket : list) {
				String userId = browerSocket.getUserId();
				SocketMessageSender.pushToUser(userId, str);
			}
		}catch(Exception e){
			log.info("WebEventPusher exception="+e);
		}
	}
}
