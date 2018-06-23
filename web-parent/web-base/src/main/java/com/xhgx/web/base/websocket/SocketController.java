package com.xhgx.web.base.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName SocketController
 * @Description WebSocket 消息推送服务类
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
@ServerEndpoint(value = "/websocket",configurator=GetHttpSessionConfigurator.class)
public class SocketController {
	/**日志*/
	private static final Logger log = LoggerFactory.getLogger(SocketController.class);

    /**初始化*/
    public SocketController() {}
    
    /**开启一个连接*/
    @OnOpen
    public void start(Session socketSession, EndpointConfig config) {
    	  //session
    	String onlineUserId="";
    	HttpSession httpSession = null;
    	if(config.getUserProperties().get(HttpSession.class.getName())!=null){
    		 httpSession=(HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    	}
    	UserEntity user = null;
    	if(httpSession!=null){
    		user = (UserEntity)httpSession.getAttribute(OnlineUser.ONLINE_USER_TAG);
    	}
    	//当前登录用户id 唯一标识
        if(user!=null){
        	onlineUserId = user.getId();
        }
        if(onlineUserId==null || onlineUserId.isEmpty()){
        	return;
        }
        
        String httpSessionId=null;
        //服务器重启或导致 httpsession 失效
        if(httpSession !=null){
        	httpSessionId=httpSession.getId();
        }
        
        //每个session id 建立一个连接 保证同一个用户多个浏览器同上接受消息
        SocketPoolManager.addConnection(onlineUserId,httpSessionId, socketSession);
    }

    /**关闭一个连接*/
    @OnClose
    public void end(Session socketSession,CloseReason reason) {
    	 String onlineUserId=null;
    	try{
            if(socketSession.getId()==null || socketSession.getId().isEmpty()){
            	return;
            }
            SocketPoolManager.deleteConnection(socketSession.getId() );
    	}catch(Exception e){
    		log.info("用户id:"+onlineUserId+" 关闭 socket 清除数据失败!");
    	}
    }

    /**
     * 消息发送触发方法
     * @param message
     */
    @OnMessage
    public void incoming(String message,Session session) {
        // 监听浏览器客户端发送的消息 字符过滤保证安全性
    	//本系统发送方式全部是系统发送，所以不用此方法
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
       log.info("websocket Error: " + t.toString());
    }
}