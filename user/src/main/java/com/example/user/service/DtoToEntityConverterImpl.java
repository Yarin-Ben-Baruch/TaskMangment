package com.example.user.service;

import com.example.user.pojo.User;
import com.example.user.pojo.UserCreation;
import com.example.user.pojo.UserDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class DtoToEntityConverterImpl implements DtoToEntityConverter{

    @Override
    public User getEntityFromDto(UserDto userDto) {
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .userId(userDto.getUserId())
                .personalId(userDto.getPersonalId())
                .build();

        return user;
    }

    @Override
    public UserDto toUserDto(User user) {
        UserDto userDto = UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .id(user.getUserId())
                .personalId(user.getPersonalId())
                .build();

        return userDto;
    }

    @Override
    public User getEntityFromDtoCreation(UserCreation userCreation) {
        User user = User.builder()
                .email(userCreation.getEmail())
                .firstName(userCreation.getFirstName())
                .lastName(userCreation.getLastName())
                .password(userCreation.getPassword())
                .personalId(userCreation.getPersonalId())
                .build();

        return user;
    }

    @Override
    public Collection<UserDto> getCollectionUsersOfDto(Collection<User> users) {
        return users.stream().map(this::toUserDto)
                .collect(Collectors.toList());
    }
}
