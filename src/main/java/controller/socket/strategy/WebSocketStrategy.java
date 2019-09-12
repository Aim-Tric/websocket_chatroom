package controller.socket.strategy;

import org.springframework.web.socket.WebSocketSession;
import pojo.Message;

public interface WebSocketStrategy {

    void execute(WebSocketSession session, Message message);

}
