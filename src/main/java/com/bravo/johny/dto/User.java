package com.bravo.johny.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(includeFieldNames = true)
public class User extends RepresentationModel<User> implements Serializable {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String favGenre;
    private int bookCount;
    private int fine;

    public User(String userName, String firstName, String lastName) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
