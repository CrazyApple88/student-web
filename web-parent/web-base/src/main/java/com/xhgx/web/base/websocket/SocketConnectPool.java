package com.xhgx.web.base.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName 
 * @Description  socket 连接缓存池
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class SocketConnectPool {
	
	/**日志*/
	private static final Logger log = LoggerFactory.getLogger(SocketConnectPool.class);
	
	/**连接缓存池*/
	/**第一个String为userId,第二个String为socketSessionId*/
	public static Map<String,Map<String,BrowerSocket>> connections = new HashMap<String,Map<String,BrowerSocket>>();
	
	/**用于反向查询，通过socketSessionId,查询到用户的id,key为socketSessionId，value为userId*/
	public static Map<String,String> inverseQuery = new HashMap<String,String>();
	
	/**获取连接*/
	public static Object getConnection(String userId,String requestsessionId){
		// 获取这个人的所有连接
		Map<String,BrowerSocket> sockets=connections.get(userId);
		if(sockets==null || sockets.isEmpty()){
			return null;
		}
		BrowerSocket socket=sockets.get(requestsessionId);
		return socket;
	}
	
	/**添加连接*/
	public static void addConnection(String userId,String httpsessionId, Session socketSession){
		// 获取这个人的所有连接
		Map<String,BrowerSocket> sockets=connections.get(userId);
		if(sockets==null || sockets.isEmpty()){
			sockets=new HashMap<String,BrowerSocket>();
			connections.put(userId, sockets);
		}
		BrowerSocket socket=new BrowerSocket(userId,httpsessionId, socketSession);
		sockets.put(socketSession.getId(), socket);
		inverseQuery.put(socketSession.getId(), userId);
	}
	
	/**删除连接*/
	public static void deleteConnection(String socketsessionId){
		// 获取这个人的所有连接
		String userId = inverseQuery.get(socketsessionId);
		Map<String,BrowerSocket> sockets=connections.get(userId); 
		if(sockets==null || sockets.isEmpty()){
			return;
		}
		sockets.remove(socketsessionId);
	}
	
	/**删除连接*/
	public static void deleteConnection(BrowerSocket socketVo){
		// 获取这个人的所有连接
		Map<String,BrowerSocket> sockets=connections.get(socketVo.getUserId());
		if(sockets==null || sockets.isEmpty()){
			return;
		}
		log.info("---------------------------------delete a websocket connection sockethttpsessionid="+socketVo.getHttpSessionId());
		sockets.remove(socketVo.getSocketSession().getId());
	}
	/**
	 * 根据人员查询这个人建立的所有socket 连接  主要针对同时打开多个浏览器
	 * @param userId
	 * @return
	 */
	public static List<BrowerSocket> getSocketListByUserId(String userId){ 
		// 创建返回对象
		List<BrowerSocket> socketList=new ArrayList<BrowerSocket>();
		// 查询已建立的所有连接
		Map<String,BrowerSocket> socketsMap=connections.get(userId);
		if(socketsMap!=null && !socketsMap.isEmpty()){
			Set<Entry<String,BrowerSocket>> socketEntrySet=socketsMap.entrySet();
			for(Entry<String,BrowerSocket> socketEntry: socketEntrySet){
				BrowerSocket value=socketEntry.getValue();
				// 加入返回集合
				socketList.add(value);
			}//end for
		}//end if
		return socketList;
	}
	
	/**
	 * 获取所有 socket  用于群发
	 * @return
	 */
	public static List<BrowerSocket> getAllSocketList(){
		// 创建返回对象
		List<BrowerSocket> allSocketList=new ArrayList<BrowerSocket>();
		Set<String> userIds=connections.keySet();
		if(userIds!=null && !userIds.isEmpty()){
			for(String userId:userIds){
				// 查询这个人的
				List<BrowerSocket> userSocketList=getSocketListByUserId(userId);
				allSocketList.addAll(userSocketList);
			}
		}
		// 返回socketList
		return allSocketList;
	}
}
