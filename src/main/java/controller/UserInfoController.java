package controller;

import common.Const;
import common.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Result;
import pojo.qo.UserQO;
import pojo.vo.UserVO;
import service.UserService;
import utils.ResultUtil;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户信息接口，提供了获取用户相关信息接口
 */
@RestController
@RequestMapping("/info/user")
public class UserInfoController {

    @Autowired
    UserService service;

    @RequestMapping("/friends")
    public Result getFriendList(HttpSession session) {
        UserQO user = (UserQO) session.getAttribute(Const.USER);
        List<UserVO> userExt = service.getFriendList(user.getId());
        System.out.println(userExt);
        if (userExt != null) {
            return ResultUtil.success(userExt);
        }
        return ResultUtil.error(ResultEnum.ERROR);
    }

}
