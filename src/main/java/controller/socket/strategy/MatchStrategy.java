package controller.socket.strategy;

import controller.socket.UserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import pojo.Message;
import pojo.vo.MessageVO;
import pojo.vo.UserVO;
import service.HobbyService;
import service.UserService;
import utils.BeanUtil;

import java.util.List;

@Component("2")
public class MatchStrategy implements WebSocketStrategy {

    @Autowired
    private UserService userService;

    @Autowired
    private HobbyService hobbyService;

    @Override
    public void execute(WebSocketSession session, Message message) {

        UserVO user = userService.getUserById(message.getSource());
        MessageVO messageVO = BeanUtil.copyProperties(message, MessageVO.class);
        messageVO.setUser(user);

        Object body = message.getBody();
        if (body instanceof Integer[]) {
            // 查询拥有相同爱好的人
            List<Integer> members = hobbyService.getMembersByHobbyId((Integer[]) body);
            System.out.println("匹配到的人有： " + members.size() + " 个");
            // 发送匹配聊天请求给在线的用户
            UserPool.oneToMany(members, messageVO);
        }
    }
}
