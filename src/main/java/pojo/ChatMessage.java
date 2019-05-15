package pojo;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class ChatMessage {
    private String nickname;
    private Date date;
    private String message;

    public ChatMessage() {
    }

    public ChatMessage(String nickname, Date date, String message) {
        this.nickname = nickname;
        this.date = date;
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
