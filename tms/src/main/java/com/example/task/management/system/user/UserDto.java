package com.example.task.management.system.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UserDto {

    private Long userId;

    private String personalId;

    private String firstName;

    private String lastName;

    @Builder
    public UserDto(long id, String firstName, String lastName, String personalId) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalId = personalId;
    }
}
