package com.xhgx.web.base.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
 
/**
 * @ClassName 
 * @Description 让session中的值传入到websocket中
 * @author renyh 
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator{
    @Override
    public void modifyHandshake(ServerEndpointConfig config,HandshakeRequest request, HandshakeResponse response){
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        if(httpSession==null){
        	return;
        }
        config.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}