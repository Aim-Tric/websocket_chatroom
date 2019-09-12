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

import java.util.List;

@Component("0")
public class MessageStrategy implements WebSocketStrategy {

    @Autowired
    private ChatRoomSystemService chatRoomSystemService;

    @Autowired
    private UserService userService;

    @Override
    public void execute(WebSocketSession session, Message message) {
        // 将信息转换成前端所需要的数据结构
        UserVO user = userService.getUserById(message.getSource());
        MessageVO messageVO = BeanUtil.copyProperties(message, MessageVO.class);
        messageVO.setUser(user);

        // TODO：聊天记录分离
        chatRoomSystemService.insertRecord(message);
        if (message.isGroup()) {
            List<Integer> roomMembers = chatRoomSystemService.getRoomMembersId(message.getTarget());
            UserPool.oneToMany(roomMembers, messageVO);
        } else {
            Integer target = message.getTarget();
            Integer source = message.getSource();
            messageVO.setRoomId(chatRoomSystemService.getRoomId(target, source));
            UserPool.oneToOne(UserPool.hall.get(target), messageVO);
            UserPool.oneToOne(UserPool.hall.get(source), messageVO);
        }
    }
}
