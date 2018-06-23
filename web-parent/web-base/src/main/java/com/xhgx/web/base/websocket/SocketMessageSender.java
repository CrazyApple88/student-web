package com.xhgx.web.base.websocket;

import java.io.IOException;
import java.util.List;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName SocketMessageSender
 * @Description socket消息发送类
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class SocketMessageSender {
	
	private static final Logger log = LoggerFactory.getLogger(SocketMessageSender.class);
	
	/**在指定的socket 连接中推送消息*/
	private static void pushToSocket(List<BrowerSocket> socketList,String socketMsg){
		for(BrowerSocket socketVo:socketList){
			Session socketSession=socketVo.getSocketSession();
			  try {
				   synchronized (socketSession) {
					 // 推送消息
					   socketSession.getBasicRemote().sendText(socketMsg);
	               }
			  }catch(Exception e){
				  log.info(" websocket push Error: Failed to send message to client  ["+e+"]");
				  SocketConnectPool.deleteConnection(socketVo);
	                try { 
	                	socketSession.close();
	                } catch (IOException ee) { }
			  }//end catch
		}//end for
	}
	
	/**
	 * 消息推送给指定用户
	 * @param userId
	 * @param socketMsg
	 */
	public static void pushToUser(String userId,String socketMsg){
		//返回该成员已建立的所有socket 结合
		List<BrowerSocket> socketList=SocketConnectPool.getSocketListByUserId(userId);
		pushToSocket(socketList,socketMsg);
	}
	
	/**
	 * 消息推送给用户集合
	 * @param userIds
	 * @param socketMsg
	 */
	public static void pushToUsers(List<String> userIds,String socketMsg){
		for(String userId:userIds){
			pushToUser(userId,socketMsg);
		}
	}
	
	/**
	 * 向所有前台成员推送消息
	 * @param msg
	 */
	public static void pushAllUser(String msg){
		//返回该成员已建立的所有socket 结合
		List<BrowerSocket> socketList=SocketConnectPool.getAllSocketList();
		pushToSocket(socketList, msg);
	}
}
