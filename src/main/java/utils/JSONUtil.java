package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JSONUtil {

    public static Object parse(String text, Class target) {
        JSONObject jsonObject = JSONObject.parseObject(text);
        return JSON.toJavaObject(jsonObject, target);
    }


}
