package pojo.qo;

import pojo.HobbyTag;

import java.util.List;

public class UserQO {

    private Integer id;
    private String pic;
    private String phone;
    private String password;
    private String nickname;
    private String signature;
    private List<HobbyTag> hobbys;

    public UserQO() {
    }

    public UserQO(Integer id, String pic, String phone, String password, String nickname, String signature, List<HobbyTag> hobbys) {
        this.id = id;
        this.pic = pic;
        this.phone = phone;
        this.password = password;
        this.nickname = nickname;
        this.signature = signature;
        this.hobbys = hobbys;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<HobbyTag> getHobbys() {
        return hobbys;
    }

    public void setHobbys(List<HobbyTag> hobbys) {
        this.hobbys = hobbys;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", pic='" + pic + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", signature='" + signature + '\'' +
                ", hobbys=" + hobbys +
                '}';
    }
}
