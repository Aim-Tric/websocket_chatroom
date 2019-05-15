package controller.socket;

import mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SocketHandler implements WebSocketHandler {

    public static Map<String, WebSocketSession> userMap;
    private static Date latestDate;
    private static final long FIVE_MINUTES = 5 * 60 * 1000;

    @Autowired
    ChatMapper mapper;

    static {
        userMap = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        String nickname = (String) webSocketSession.getAttributes().get("nickname");
        userMap.put(nickname, webSocketSession);
        TextMessage textMessage = new TextMessage(nickname + "加入聊天室");
        this.handleMessage(webSocketSession, textMessage, "system");
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        String nickname = (String) webSocketSession.getAttributes().get("nickname");
        this.handleMessage(webSocketSession, webSocketMessage, nickname);
    }

    /**
     * 这个是写的真的菜，处理接收到的消息，并记录
     *
     * @param nickname 用户昵称
     */
    private void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage, String nickname) throws IOException {
        String m = webSocketMessage.getPayload().toString();
        if (webSocketMessage.getPayload() instanceof ByteBuffer) {
            ByteBuffer buffer = (ByteBuffer) webSocketMessage.getPayload();
            m = new String(buffer.array(), StandardCharsets.UTF_8);
            System.out.println(m);
        }
        Date now = new Date();
        MyWebSocketMessage message = new MyWebSocketMessage(nickname, now, m);
        if (!"system".equals(nickname)) {
            if (latestDate != null && now.getTime() - latestDate.getTime() <= FIVE_MINUTES) {
                message.setDate(null);
            }
            latestDate = now;
//            mapper.record(message.getPayload());
        }
        TextMessage textMessage = new TextMessage(message.toString());
        for (String k : userMap.keySet()) {
            userMap.get(k).sendMessage(textMessage);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        String nickname = (String) webSocketSession.getAttributes().get("nickname");
        System.err.println(nickname + "出错了:" + throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        String nickname = (String) webSocketSession.getAttributes().get("nickname");
        if (nickname != null) {
            userMap.remove(nickname);
        }
        TextMessage message = new TextMessage(nickname + "已退出聊天室");
        handleMessage(webSocketSession, message, "system");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
