package com.xhgx.web.base.websocket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.websocket.Session;

/**
 * @ClassName SocketPoolManager
 * @Description web端建立连接的管理工具类
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class SocketPoolManager {
	
	/**定义资源池*/
	public static SocketConnectPool  socketPool=new SocketConnectPool(); 
	/**定义资源锁*/
	public  static  Lock  socketLock = new ReentrantLock();
	
	
	/**添加连接*/
	public static void addConnection(String userId,String httpSessionId, Session socketSession){
		try{
			socketLock.lock();
			SocketConnectPool.addConnection(userId, httpSessionId, socketSession);
		}finally{
			socketLock.unlock();
		}
		
	}
	
	/**删除连接*/
	public static void deleteConnection(String socketSessionId){
		try{
			socketLock.lock();
			SocketConnectPool.deleteConnection(socketSessionId);
		}finally{
			socketLock.unlock();
		}
	}
	
	/**删除连接*/
	public static void deleteConnection(BrowerSocket socket){
		try{
			socketLock.lock();
			SocketConnectPool.deleteConnection(socket);
		}finally{
			socketLock.unlock();
		}
	}
	

	/**
	 * 关闭用户websocket  一般不会主动关，可以用来测试 断线
	 * @param userId
	 */
	public static void closeUserConnect(String userId){
		List<BrowerSocket> socketList=SocketConnectPool.getSocketListByUserId(userId);
		if(socketList==null || socketList.isEmpty()){
			return;
		}
		//循环关闭
		for(BrowerSocket socketVo :socketList){
			try {
				socketVo.getSocketSession().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		socketList.clear();
	}
}
