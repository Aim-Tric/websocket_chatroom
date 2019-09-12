package controller.socket;

import com.alibaba.fastjson.JSONObject;
import common.Const;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import pojo.qo.UserQO;
import pojo.vo.MessageVO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserPool {

    public static Map<Integer, WebSocketSession> hall = new HashMap<>();

    /**
     * 一对一发送信息
     * 当对方未在线的时候将信息存储起来，用户上线拉取未读取的信息
     *
     * @param session 目标用户session
     * @param message 发送的信息
     */
    public static void oneToOne(WebSocketSession session, MessageVO message) {
        try {
            if (session != null) {
                session.sendMessage(new TextMessage(JSONObject.toJSONString(message)));
            }
        } catch (IOException e) {
            handleException(session, e);
        }
    }

    /**
     * 一对多发送信息，用于群聊
     *
     * @param targets 目标session的keys
     * @param message 发送的信息
     */
    public static void oneToMany(List<Integer> targets, MessageVO message) {
        for (Integer t : targets) {
            WebSocketSession s = UserPool.hall.get(t);
            oneToOne(s, message);
        }
    }

    /**
     * 处理发送信息出错并记录日志
     *
     * @param session 出错的用户session
     * @param e       错误信息
     */
     private static void handleException(WebSocketSession session, Exception e) {
        Map attrs = session.getAttributes();
        UserQO user = (UserQO) attrs.get(Const.USER);
        String msg = user.getNickname() + "接收信息异常:  " + e.getMessage();
        System.err.println(msg);
        e.printStackTrace();
    }


}
