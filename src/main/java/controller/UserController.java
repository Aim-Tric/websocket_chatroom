package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import controller.socket.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pojo.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @RequestMapping(value = "/login.do")
    public void login(@RequestBody User u, HttpSession session, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String username = u.getUsername();
        String password = u.getPassword();
        Map<String, Object> jsonMap = new HashMap<>();
        try {
            User user = service.login(username, password);
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("nickname", user.getNickname());
            Cookie c = new Cookie("nickname", user.getNickname());
            resp.addCookie(c);
            jsonMap.put("result", "success");
        } catch (RuntimeException e) {
            String error = e.getMessage();
            System.err.println("error:" + error);
            //TODO 接收异常并处理
            jsonMap.put("result", error);
        }
        resp.getWriter().print(JSONArray.toJSONString(jsonMap));
    }

    @RequestMapping("/login")
    public String login() {
        return "redirect:/index.jsp";
    }

    @RequestMapping("/chat")
    public String chat() {
        return "chat";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/register.do")
    public String doRegister(User user) {
        user.setRole("用户");
        service.register(user);
        return "redirect:/index.jsp";
    }

    @RequestMapping("/guest.do")
    public void guestIn(HttpSession session, HttpServletResponse resp, @RequestBody Map<String, String> data) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        Map<String, Object> jsonMap = new HashMap<>();
        String nickname = data.get("nickname");
        if (SocketHandler.userMap.get(nickname) != null) {
            jsonMap.put("result", "该昵称已被使用，请更换昵称");
        } else {
            session.setAttribute("user", nickname);
            session.setAttribute("nickname", nickname);
            Cookie c = new Cookie("nickname", nickname);
            resp.addCookie(c);
            jsonMap.put("result", "success");
        }
        resp.getWriter().print(JSONArray.toJSONString(jsonMap));
    }


}
