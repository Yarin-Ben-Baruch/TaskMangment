package com.example.user.service;

import com.example.common.pojo.UserDto;
import com.example.user.exceptions.UserNotFoundException;
import com.example.user.pojo.User;
import com.example.user.pojo.UserCreation;
import com.example.user.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoToEntityConverter dtoToEntityConverter;

    @Override
    public UserDto addUser(UserCreation userCreation) {
        isUserValidToAdd(userCreation);

        User user = dtoToEntityConverter.getEntityFromDtoCreation(userCreation);

        return dtoToEntityConverter.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(UserCreation detailsToUpdate, long id) {
        User userToUpdate = userRepository.findByUserId(id)
                .map(currentUser ->{
                    isUserValidToUpdate(detailsToUpdate);
                    return currentUser;
                })
                .orElseGet(() ->{
                    isUserValidToAdd(detailsToUpdate);
                    return dtoToEntityConverter.getEntityFromDtoCreation(detailsToUpdate);
                });

        userToUpdate = updateUser(detailsToUpdate, userToUpdate);

        return dtoToEntityConverter.toUserDto(userRepository.save(userToUpdate));
    }

    @Override
    public void removeUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUser(long id) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new UserNotFoundException("Id isn't exist"));

        return dtoToEntityConverter.toUserDto(user);
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        return dtoToEntityConverter.getCollectionUsersOfDto(userRepository.findAll());
    }

    private void isUserValidToUpdate(UserCreation userToCheck){
        isEmailExists(userToCheck);
        isEmailUniq(userToCheck);
        isPasswordExists(userToCheck);
    }

    private void isUserValidToAdd(UserCreation userToCheck){
        isEmailExists(userToCheck);
        isEmailUniq(userToCheck);
        isPasswordExists(userToCheck);
    }

    private void isEmailExists(UserCreation userCreation) {
        if (StringUtils.isEmpty(userCreation.getEmail())) {
            throw new UserNotFoundException("Email is a required field");
        }
    }

    private void isEmailUniq(UserCreation userCreation) {
        if (userRepository.findByEmail(userCreation.getEmail()).isPresent()) {
            throw new UserNotFoundException("Email should be unique");
        }
    }

    private void isPasswordExists(UserCreation userCreation) {
        if (StringUtils.isEmpty(userCreation.getPassword())) {
            throw new UserNotFoundException("Password is a required field");
        }
    }

    private User updateUser(UserCreation userCreation, User userToUpdate){
        userToUpdate.setEmail(userCreation.getEmail());
        userToUpdate.setFirstName(userCreation.getFirstName());
        userToUpdate.setLastName(userCreation.getLastName());
        userToUpdate.setPassword(userCreation.getPassword());

        return userToUpdate;
    }
}