package com.example.user.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tm_user")
@Getter @Setter
@NoArgsConstructor
public class User {

    @SequenceGenerator(name = "userManagement", sequenceName = "userManagement")
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userManagement")
    @Column(name = "ID")
    private Long userId;

    @Column(name = "PERSONAL_ID")
    private String personalId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Builder
    public User(Long userId, String firstName, String lastName, String email, String password, String personalId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.personalId = personalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        if (userId != null ? !userId.equals(user.userId) : user.userId != null) {
            return false;
        }
        if (email != null ? !email.equals(user.email) : user.email != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}