package com.example.user.service;

import com.example.common.pojo.UserDto;
import com.example.user.pojo.UserCreation;

import java.util.Collection;

public interface UserService {
    UserDto addUser(UserCreation user);

    void removeUser(long id);

    UserDto getUser(long id);

    Collection<UserDto> getAllUsers();

    UserDto updateUser(UserCreation detailsToUpdate, long id);
}
