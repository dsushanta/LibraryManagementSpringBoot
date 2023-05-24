package com.bravo.johny.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="LI_USER", uniqueConstraints = {
        @UniqueConstraint(name = "Unique_Constraint_Email", columnNames = "Email")})
public class UserEntity implements Serializable {

    @Id
    @Column(name = "UserName")
    private String userName;

    @Column(name = "password",
            nullable = false,
            columnDefinition = "TEXT")
    private String password;

    @Column(name = "FirstName",
            nullable = false,
            columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "LastName",
            nullable = false,
            columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "Email",
            nullable = false)
    private String email;

    @Column(name = "FavGenre")
    private String favGenre;

    @Column(name = "Fine",
            columnDefinition = "integer default 0")
    private Integer fine;

    @Column(name = "BookCount",
            columnDefinition = "integer default 0")
    private Integer bookCount;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<BookLifeCycleEntity> copiesIssued;

    @OneToOne(fetch = FetchType.EAGER)
    private RoleEntity role;

    public UserEntity(String userName, String password, String firstName, String lastName, String email, String favGenre, Integer fine, Integer bookCount) {
        this.userName = userName;
        this.userName = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.favGenre = favGenre;
        this.fine = fine;
        this.bookCount = bookCount;
    }

    public UserEntity(String userName, String password, String firstName, String lastName, String email, String favGenre, Integer fine, Integer bookCount, RoleEntity role) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.favGenre = favGenre;
        this.fine = fine;
        this.bookCount = bookCount;
        this.role = role;
    }
}
