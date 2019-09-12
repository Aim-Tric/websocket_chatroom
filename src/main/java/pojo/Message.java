package pojo;

import java.util.Date;

/**
 * 用户传递的信息类
 * source 来源用户的id
 * target 目标用户的id
 * type 信息的类型，比如 0 为聊天信息，1 为通知信息
 * createTime 发送的时间
 * group 是否为群消息
 * body 消息的具体内容
 */
public class Message {

    private Integer source;
    private Integer target;
    private Integer type;
    private Date createTime;
    private boolean group;
    private Object body;
    private boolean result;
    private boolean handle = false;

    public Message() {
    }

    public Message(Integer source, Integer target, Integer type, Date createTime, boolean group, Object body, boolean result) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.createTime = createTime;
        this.group = group;
        this.body = body;
        this.result = result;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isHandle() {
        return handle;
    }

    public void setHandle(boolean handle) {
        this.handle = handle;
    }

    @Override
    public String toString() {
        return "Message{" +
                "source=" + source +
                ", target=" + target +
                ", type=" + type +
                ", createTime=" + createTime +
                ", group=" + group +
                ", body=" + body +
                ", result=" + result +
                ", handle=" + handle +
                '}';
    }
}
