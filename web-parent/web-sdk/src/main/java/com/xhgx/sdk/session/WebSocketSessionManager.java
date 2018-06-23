package com.xhgx.sdk.session;


import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName WebSocketSessionManager
 * @Description websocket session 管理器
 * @author zohan(inlw@sina.com)
 * @date 2017-03-30 13:23
 * @vresion 1.0
 */
public class WebSocketSessionManager {

    private final static Map<String, Session> MAP = new ConcurrentHashMap();

    /**
     * Add session.
     * 放入管理
     *
     * @param userId  the user id
     * @param session the session
     */
    public synchronized static void addSession(String userId, Session session) {
        MAP.put(userId, session);
    }

    /**
     * Gets session.
     * 通過用戶id獲取session
     *
     * @param userId the user id
     * @return the session
     */
    public synchronized static Session getSession(String userId) {
        return MAP.get(userId);
    }
}
