package controller.socket.strategy;

import controller.socket.UserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import pojo.Message;
import pojo.vo.MessageVO;
import pojo.vo.UserVO;
import service.ChatRoomSystemService;
import service.UserService;
import utils.BeanUtil;

@Component("1")
public class RequestStrategy implements WebSocketStrategy {

    @Autowired
    private ChatRoomSystemService chatRoomSystemService;

    @Autowired
    private UserService userService;

    @Override
    public void execute(WebSocketSession session, Message message) {

        UserVO user = userService.getUserById(message.getSource());
        message.setType(3);
        Message request = chatRoomSystemService.getRequest(message);
        MessageVO messageVO = BeanUtil.copyProperties(message, MessageVO.class);
        messageVO.setUser(user);
        messageVO.setBody(user.getNickname() + "请求添加你为好友");

        UserPool.oneToOne(UserPool.hall.get(message.getTarget()), messageVO);
        if (request == null) {
            chatRoomSystemService.insertRequest(message);
        }
    }
}
