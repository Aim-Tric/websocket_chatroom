package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.ChatMessage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    ChatMapper mapper;

    private static final Integer MAX = 30;

    @RequestMapping("/getRecords")
    public void getRecords(HttpServletResponse resp, Integer page) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        Integer current = (page-1) * MAX;
        List<ChatMessage> list = mapper.getRecords(current, MAX);
        Integer count = mapper.getRecordCount();
        Map<String, Object> map = new HashMap<>();
        map.put("pages", count);
        map.put("data", list);
        resp.getWriter().print(JSONObject.toJSONString(map));
    }

    @RequestMapping("/getCount")
    public void getCount(HttpServletResponse resp) throws IOException {
        resp.getWriter().print(mapper.getRecordCount());
    }



}
