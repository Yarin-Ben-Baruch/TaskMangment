package com.example.user.controller;

import com.example.common.pojo.UserDto;
import com.example.user.pojo.UserCreation;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDto saveUser(@RequestBody UserCreation user) {
        return userService.addUser(user);
    }

    @PutMapping("/update/{id}")
    public UserDto updateUser(@RequestBody UserCreation detailsToUpdate,@PathVariable long id){
        return userService.updateUser(detailsToUpdate, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.removeUser(id);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable long id){
        return userService.getUser(id);
    }

    @GetMapping
    public Collection<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }
}