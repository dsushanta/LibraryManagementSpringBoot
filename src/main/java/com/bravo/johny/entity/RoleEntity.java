package com.bravo.johny.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="LI_ROLE")
public class RoleEntity implements Serializable {

    @Id
    @Column(name = "RoleId")
    @GeneratedValue
    private Integer roleId;

    @Column(name = "RoleName",
            nullable = false,
            columnDefinition = "TEXT")
    private String roleName;

    /*@OneToOne(mappedBy = "role", orphanRemoval = true)
    private UserEntity userEntity;*/
}
