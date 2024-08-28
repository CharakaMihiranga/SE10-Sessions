package lk.ijse.gdse.springboot.jwt.service;

import lk.ijse.gdse.springboot.jwt.dto.UserDto;

public interface UserService {
    int saveUser(UserDto userDto);
    UserDto searchUser(String username);
}
