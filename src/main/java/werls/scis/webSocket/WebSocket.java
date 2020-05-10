package werls.scis.webSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author : LiJiWei
 * @version V1.0
 * @Project: scis
 * @Package werls.scis.webSocket
 * @Description: TODO
 * @date Date : 2020年04月07日 15:47
 */

@Component
@ServerEndpoint("/public/websocket/{userName}")
public class WebSocket {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebSocket webSocket = (WebSocket) o;
        return Objects.equals(session, webSocket.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session);
    }

    private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    private static Map<String,Session> sessionPool = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value="userName")String userName) {
        this.session = session;
        webSockets.add(this);
        sessionPool.put(userName, session);
        logger.info("用户id：{}连接，【websocket消息】有新的连接，总数为:{}",userName,webSockets.size());
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        logger.info("【websocket消息】连接断开，总数为:{}",webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {

    }

    // 此为广播消息

    public void sendAllMessage(String message) {
        for(WebSocket webSocket : webSockets) {
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息

    public void sendOneMessage(String userName, String message) {
        Session session = sessionPool.get(userName);
        if (session != null) {
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendOneMessage(String userName, Object message) {
        Session session = sessionPool.get(userName);
        if (session != null) {
            try {
               session.getAsyncRemote().sendObject(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
