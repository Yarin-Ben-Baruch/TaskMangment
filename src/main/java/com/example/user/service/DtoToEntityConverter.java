package com.example.user.service;

import com.example.user.pojo.User;
import com.example.user.pojo.UserCreation;
import com.example.user.pojo.UserDto;

import java.util.Collection;

public interface DtoToEntityConverter {
    User getEntityFromDto(UserDto userDto);
    UserDto toUserDto(User user);
    User getEntityFromDtoCreation(UserCreation userCreation);
    Collection<UserDto> getCollectionUsersOfDto(Collection<User> users);
}
