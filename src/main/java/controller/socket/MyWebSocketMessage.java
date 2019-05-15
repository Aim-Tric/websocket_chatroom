package controller.socket;

import org.springframework.web.socket.WebSocketMessage;
import pojo.ChatMessage;

import java.util.Date;

public class MyWebSocketMessage implements WebSocketMessage<ChatMessage> {

    private ChatMessage chatMessage;
    private boolean isLast;

    public MyWebSocketMessage(String nickname, Date date, String message) {
        this(new ChatMessage(nickname, date, message), false);
    }

    private MyWebSocketMessage(ChatMessage chatMessage, boolean isLast) {
        this.chatMessage = chatMessage;
        this.isLast = isLast;
    }

    private byte[] asBytes() {
        return this.chatMessage.toString().getBytes();
    }

    public void setDate(Date date) {
        this.chatMessage.setDate(date);
    }

    @Override
    public ChatMessage getPayload() {
        return this.chatMessage;
    }

    @Override
    public int getPayloadLength() {
        return 1;
    }

    @Override
    public boolean isLast() {
        return isLast;
    }

    @Override
    public String toString() {
        return this.chatMessage.toString();
    }
}
