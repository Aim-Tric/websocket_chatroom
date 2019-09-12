package service;

import pojo.qo.UserQO;
import pojo.vo.UserVO;

import java.util.List;

public interface UserService {

    UserQO getUserByRecord(UserQO user);

    int register(UserQO user);

    List<UserVO> getFriendList(Integer id);

    UserVO getUserById(Integer id);

    boolean checkPhone(UserQO userQO);

    int updateUser(UserQO userQO);

    int addFriend(Integer userId, Integer friendId);

    List<UserVO> searchUserByNickname(UserQO userQO);

}
