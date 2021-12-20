package com.kiddo.signaling_server.websocket;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@ServerEndpoint(value = "/demows/{id}")
public class WebSocketDemo {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>(16);

    public static Map<String, Session> getSessionMap() {
        return sessionMap;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        session.getUserProperties();
        logger.info("链接用户 id=[{}]", id);

        sessionMap.put(id, session);
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("onMessage session=[{}] userId=[{}] message=[{}]", session.getId(), getUserIdFromSessionId(session.getId()), message);

        handleMessage(message, session);
    }

    @OnClose
    public void onClose(Session session) {
        logger.info("移除不用的 session=[{}] userId=[{}]", getUserIdFromSessionId(session.getId()), session.getId());

        for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
            if (entry.getValue().getId().equals(session.getId())) {
                sessionMap.remove(entry.getKey());
            }
        }

        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("onError sessionId=[{}] userId=[{}]", session.getId(), getUserIdFromSessionId(session.getId()), throwable);
    }

    private String getUserIdFromSessionId(String sId) {
        if (sId != null) {
            for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
                if (entry.getValue().getId().equals(sId)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    private void handleMessage(String message, Session session) {

        JSONObject jsonObject = JSONObject.parseObject(message);
        String receiver = jsonObject.getString("receiver");

        if (receiver != null) {
            sendMessage(receiver, message);
        }
    }

    private void sendMessage(String id, String message) {
        logger.info("sendMsg id=[{}] String=[{}]", id, message);

        Session session = sessionMap.get(id);
        if (session == null) {
            logger.error("session is null");
            return;
        }

        synchronized (session) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
