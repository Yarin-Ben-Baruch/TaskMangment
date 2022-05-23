package com.example.user.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserCreation {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String personalId;

    @Builder
    public UserCreation(String firstName, String lastName, String email, String password, String personalId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.personalId = personalId;
    }
}
