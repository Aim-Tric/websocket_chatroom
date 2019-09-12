package service.impl;

import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.bo.UserBO;
import pojo.qo.UserQO;
import pojo.vo.UserVO;
import service.UserService;
import utils.BeanUtil;

import java.util.Base64;
import java.util.List;

/**
 * 用户服务类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 通过用户的实例获取完整用户信息
     *
     * @param userQO 包含用户部分信息实例
     * @return 完整用户实例
     */
    @Override
    public UserQO getUserByRecord(UserQO userQO) {
        return userMapper.getByRecord(userQO);
    }

    /**
     * 用户注册
     *
     * @param userQO 用户填写的相关信息
     * @return 数据库受影响行数
     */
    @Override
    public int register(UserQO userQO) {
        UserBO userBO = BeanUtil.copyProperties(userQO, UserBO.class);
        return userMapper.insertUser(userBO);
    }

    /**
     * 获取好友列表的信息
     *
     * @param id 用户的id
     * @return 用户的好友列表
     */
    @Override
    public List<UserVO> getFriendList(Integer id) {
        return userMapper.getFriendList(id);
    }

    /**
     * 获取用户实例
     *
     * @param id 用户的id
     * @return 完整用户实例
     */
    @Override
    public UserVO getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    /**
     * 检验手机号是否存在
     *
     * @param userQO 包含部分用户信息的实例
     * @return true 为用户不存在， false 为用户存在
     */
    @Override
    public boolean checkPhone(UserQO userQO) {
        return userMapper.getByRecord(userQO) == null;
    }

    /**
     * 更新用户信息
     *
     * @param userQO 需要更新的用户信息，根据他的id来更新
     * @return 数据库受影响行数
     */
    @Override
    public int updateUser(UserQO userQO) {
        UserBO userBO = BeanUtil.copyProperties(userQO, UserBO.class);
        // 这里将图片的base64转为byte[]
        String base64Str = userQO.getPic();
        if (base64Str != null) {
            Base64.Decoder decoder = Base64.getDecoder();
            userBO.setPicByte(decoder.decode(base64Str));
        }
        return userMapper.updateUser(userBO);
    }

    @Override
    public int addFriend(Integer userId, Integer friendId) {
        return userMapper.insertFriend(userId, friendId);
    }

    @Override
    public List<UserVO> searchUserByNickname(UserQO userQO) {
        List<UserBO> userBOList = userMapper.searchUserByNickname(userQO);
        List<UserVO> userVOList = BeanUtil.copyProperties(userBOList, UserVO.class);
        for (int i = 0, len = userBOList.size(); i < len; i++) {
            byte[] pic =  userBOList.get(i).getPicByte();
            if(pic != null) {
                String base64 = Base64.getEncoder().encodeToString(pic);
                userVOList.get(i).setPic("data:image/jpg;base64," + base64);
            }
        }
        return userVOList;
    }

}
