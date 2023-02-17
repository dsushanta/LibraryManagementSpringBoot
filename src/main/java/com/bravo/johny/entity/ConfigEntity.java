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
@Table(name="LI_CONFIG")
public class ConfigEntity  implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;

    @Column(name = "NoOfDaysAUserCanKeepABook",
            columnDefinition = "integer default 0")
    private Integer noOfDaysAUserCanKeepABook;

    @Column(name = "FinePerDay",
            columnDefinition = "integer default 0")
    private Integer finePerDay;

    @Column(name = "NoOfBooksPerUser",
            columnDefinition = "integer default 0")
    private Integer noOfBooksPerUser;

    public ConfigEntity(int noOfDaysAUserCanKeepABook, int finePerDay, int noOfBooksPerUser) {
        this.noOfDaysAUserCanKeepABook = noOfDaysAUserCanKeepABook;
        this.finePerDay = finePerDay;
        this.noOfBooksPerUser = noOfBooksPerUser;
    }
}
