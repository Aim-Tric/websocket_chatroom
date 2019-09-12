package controller.socket.strategy;

import common.Const;
import controller.socket.UserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import pojo.Message;
import pojo.qo.UserQO;
import pojo.vo.MessageVO;
import pojo.vo.UserVO;
import service.ChatRoomSystemService;
import service.UserService;
import utils.BeanUtil;

import java.util.Map;

@Component("3")
public class HandleRequestStrategy implements WebSocketStrategy {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRoomSystemService chatRoomSystemService;

    @Override
    public void execute(WebSocketSession session, Message message) {
        // 由于消息是按照发送者发送的样子存储并转发的，所以在这里拿到的是target是本人，而source是对方
        Map attrs = session.getAttributes();
        UserQO user = (UserQO) attrs.get(Const.USER);

        MessageVO messageVO = BeanUtil.copyProperties(message, MessageVO.class);
        messageVO.setUser(BeanUtil.copyProperties(user, UserVO.class));

        UserPool.oneToOne(UserPool.hall.get(message.getSource()), messageVO);
        userService.addFriend(user.getId(), message.getTarget());
        chatRoomSystemService.handleRequest(message);
    }
}
