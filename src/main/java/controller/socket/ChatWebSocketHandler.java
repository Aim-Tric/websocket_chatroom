package controller.socket;

import common.Const;
import controller.socket.strategy.WebSocketStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import pojo.Message;
import pojo.qo.UserQO;
import utils.JSONUtil;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 聊天室WebSocket处理类
 */
@Service
public class ChatWebSocketHandler extends AbstractWebSocketHandler {

    private Logger logger = LogManager.getLogger(ChatWebSocketHandler.class);

    @Resource
    private Map<String, WebSocketStrategy> strategyMap;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Message m = (Message) JSONUtil.parse(message.getPayload(), Message.class);
        strategyMap.get(String.valueOf(m.getType())).execute(session, m);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        Map attrs = webSocketSession.getAttributes();
        UserQO user = (UserQO) attrs.get(Const.USER);
        if (user != null) {
            UserPool.hall.put(user.getId(), webSocketSession);
            logger.info(user.getNickname() + "用户登录");
        }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        super.handleMessage(webSocketSession, webSocketMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.err.println("error: " + throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        Map attrs = webSocketSession.getAttributes();
        UserQO user = (UserQO) attrs.get(Const.USER);
        UserPool.hall.remove(user.getId());

        logger.info(user.getNickname() + "用户退出登录");
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}
