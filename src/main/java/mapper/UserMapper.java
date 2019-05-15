package mapper;

import pojo.User;

public interface UserMapper {

    User login(String username);

    void register(User user);

}
