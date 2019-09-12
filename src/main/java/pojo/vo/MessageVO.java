package pojo.vo;

import java.util.Date;

/**
 * 消息的视图对象
 * user 来源的用户信息
 */
public class MessageVO {

    private UserVO user;
    private Integer roomId;
    private Integer type;
    private Date createTime;
    private boolean group;
    private Object body;
    private boolean handle = false;

    public MessageVO() {
    }

    public MessageVO(UserVO user, Integer roomId, Integer type, Date createTime, boolean group, Object body, boolean handle) {
        this.user = user;
        this.roomId = roomId;
        this.type = type;
        this.createTime = createTime;
        this.group = group;
        this.body = body;
        this.handle = handle;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
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

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public boolean isHandle() {
        return handle;
    }

    public void setHandle(boolean handle) {
        this.handle = handle;
    }

    @Override
    public String toString() {
        return "MessageVO{" +
                "user=" + user +
                ", roomId=" + roomId +
                ", type=" + type +
                ", createTime=" + createTime +
                ", group=" + group +
                ", body=" + body +
                ", handle=" + handle +
                '}';
    }
}
