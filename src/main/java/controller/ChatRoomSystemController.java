package controller;

import common.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Result;
import pojo.qo.UserQO;
import service.ChatRoomSystemService;
import utils.ResultUtil;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatRoomSystemController {

    @Autowired
    ChatRoomSystemService chatRoomSystemService;

    @RequestMapping("/room")
    public Result getRoomId(@RequestBody Map<String, Object> datas, HttpSession session) {
        Integer fId = (Integer) datas.get("targetId");
        UserQO user = (UserQO) session.getAttribute(Const.USER);
        return ResultUtil.success(chatRoomSystemService.getRoomId(fId, user.getId()));
    }

    @RequestMapping("/hobbys")
    public Result getHobbyCards(@RequestBody Map<String, Object> datas) {

        return ResultUtil.success();
    }
}
