package service.impl;

import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.User;
import service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper mapper;

    @Override
    public User login(String username, String password) {
        User user = mapper.login(username);
        if(user == null) {
            throw new RuntimeException("用户不存在");
        } else if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码错误，请重新输入密码");
        }
        return user;
    }

    @Override
    public void register(User user) {
        mapper.register(user);
    }
}
