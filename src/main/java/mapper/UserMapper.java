package mapper;

import pojo.bo.UserBO;
import pojo.qo.UserQO;
import pojo.vo.UserVO;

import java.util.List;

public interface UserMapper {

    int insertUser(UserBO user);

    UserQO getByRecord(UserQO user);

    UserVO getUserById(Integer id);

    List<UserVO> getFriendList(Integer id);

    int updateUser(UserBO user);

    int insertFriend(Integer userId, Integer friendId);

    List<UserBO> searchUserByNickname(UserQO userQO);

}
