package pojo.vo;

import pojo.HobbyTag;
import pojo.Message;

import java.util.List;

public class UserVO {

    private Integer id;
    private String pic;
    private String nickname;
    private String signature;
    private boolean group = false;
    private List<UserVO> friends;
    private List<HobbyTag> hobbys;
    private List<Message> friendInvites;

    public UserVO() {
    }

    public UserVO(Integer id, String pic, String nickname, String signature, boolean group, List<UserVO> friends, List<HobbyTag> hobbys, List<Message> friendInvites) {
        this.id = id;
        this.pic = pic;
        this.nickname = nickname;
        this.signature = signature;
        this.group = group;
        this.friends = friends;
        this.hobbys = hobbys;
        this.friendInvites = friendInvites;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public List<UserVO> getFriends() {
        return friends;
    }

    public void setFriends(List<UserVO> friends) {
        this.friends = friends;
    }

    public List<HobbyTag> getHobbys() {
        return hobbys;
    }

    public void setHobbys(List<HobbyTag> hobbys) {
        this.hobbys = hobbys;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<Message> getFriendInvites() {
        return friendInvites;
    }

    public void setFriendInvites(List<Message> friendInvites) {
        this.friendInvites = friendInvites;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", pic='" + pic + '\'' +
                ", nickname='" + nickname + '\'' +
                ", signature='" + signature + '\'' +
                ", group=" + group +
                ", friends=" + friends +
                ", hobbys=" + hobbys +
                '}';
    }
}
