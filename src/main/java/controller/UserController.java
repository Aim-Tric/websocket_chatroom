package controller;

import common.Const;
import common.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.qo.UserQO;
import pojo.Result;
import pojo.vo.UserVO;
import service.ChatRoomSystemService;
import service.HobbyService;
import service.UserService;
import utils.BeanUtil;
import utils.ResultUtil;
import utils.UUIDUtils;
import utils.VCodeUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户接口，提供了登录注册和登录状态获取
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HobbyService hobbyService;
    @Autowired
    private ChatRoomSystemService chatRoomSystemService;

    @RequestMapping("/login")
    public Result login(@RequestBody UserQO userQO, HttpServletResponse response
            , HttpSession session) {
        UserQO user = userService.getUserByRecord(userQO);

        if (user == null) {
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
        } else if (!user.getPassword().equals(userQO.getPassword())) {
            return ResultUtil.error(ResultEnum.USER_PASSWORD_ERROR);
        }
        //  封装用户相关数据
        Integer userId = user.getId();
        System.out.println(user);

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        userVO.setFriends(userService.getFriendList(userId));
        userVO.setHobbys(hobbyService.getUserHobby(userId));
        userVO.setFriendInvites(chatRoomSystemService.getRequestByUserId(userId));

        // TODO: token这里先使用UUID，后期更换为对称加密算法
        String token = UUIDUtils.getUUID64();
        Cookie c = new Cookie(Const.TOKEN, token);
        c.setPath("/");
        c.setMaxAge(3600);
        response.addCookie(c);

        session.setAttribute(Const.USER, user);
        session.setAttribute(Const.TOKEN, token);
        session.setMaxInactiveInterval(3600);
        return ResultUtil.success(userVO);
    }

    @RequestMapping("/register")
    public Result register(@RequestBody UserQO userQO) {
        // TODO: 后面更换为手机号注册 获取验证码 验证
        int result = userService.register(userQO);
        if (result == Const.ERROR) {
            return ResultUtil.error(ResultEnum.USER_EXIST);
        } else if (result == Const.NULL) {
            return ResultUtil.error(ResultEnum.ERROR);
        }
        return ResultUtil.success();
    }

    @RequestMapping("/status")
    public Result getLoginStatus(HttpSession session) {
//        UserQO user = (UserQO) session.getAttribute(Const.USER);
//        if (user != null) {
//            return ResultUtil.success(BeanUtil.copyProperties(user, UserVO.class));
//        }
        return ResultUtil.error();
    }

    @RequestMapping("/checkPhone")
    public Result checkPhone(@RequestBody UserQO userQO) {
        Pattern pattern = Pattern.compile(Const.REGEXP_PHONE);
        Matcher matcher = pattern.matcher(userQO.getPhone());
        if(!matcher.find()) {
            return ResultUtil.error(ResultEnum.PHONE_UNVAILED);
        }
        if (userService.checkPhone(userQO)) {
            return ResultUtil.success();
        }
        return ResultUtil.error();
    }

    @RequestMapping("/checkCaptcha")
    public Result checkVerifyCode(@RequestBody Map<String, Object> data, HttpSession session) {
        // 验证验证码
        String code = data.get("captcha").toString();
        String sessionCode = session.getAttribute("code").toString();
        if (code != null && !"".equals(code) && sessionCode != null && !"".equals(sessionCode)) {
            if (code.equalsIgnoreCase(sessionCode)) {
                return ResultUtil.success();
            }
        }
        return ResultUtil.error();
    }

    @RequestMapping("/getCaptcha")
    public void getVCode(HttpSession session, HttpServletResponse response) {
        // 调用工具类生成的验证码和验证码图片
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成随机字串
        String verifyCode = VCodeUtils.generateVerifyCode(4);
        // 删除以前的验证码
        session.setAttribute("code", verifyCode.toLowerCase());
        // 生成图片
        int w = 200, h = 80;
        try {
            VCodeUtils.outputImage(w, h, response.getOutputStream(),
                    verifyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/update")
    public Result updateUser(@RequestBody UserQO userQO) {
        if(userService.updateUser(userQO) == Const.NULL) {
            return ResultUtil.error();
        }
        return ResultUtil.success();
    }

    @RequestMapping("/search")
    public Result searchUser(@RequestBody UserQO userQO) {
        return ResultUtil.success(userService.searchUserByNickname(userQO));
    }

}
