package com.bravo.johny.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="LI_BOOK_LIFE_CYCLE")
public class BookLifeCycleEntity  implements Serializable {

    @Id
    @Column(name = "BookIssueId")
    @GeneratedValue
    private Integer bookIssueId;

    @Column(name = "IsReturned",
            nullable = false,
            columnDefinition = "tinyint(1) default 0")
    private boolean isReturned;

    @Column(name = "IsRenewed",
            nullable = false,
            columnDefinition = "tinyint(1) default 0")
    private boolean isRenewed;

    @Column(name = "IssueDate",
            nullable = false)
    private Date issueDate;

    @Column(name = "ExpectedReturnDate",
            nullable = false)
    private Date expectedReturnDate;

    @Column(name = "ActualReturnDate")
    private Date actualReturnDate;

    @Column(name = "RenewDate")
    private Date renewDate;

    @Column(name = "Fine",
            columnDefinition = "integer default 0")
    private Integer fine;

    @Column(name = "FineCleared",
            columnDefinition = "tinyint(1) default 1")
    private boolean fineCleared;

    @ManyToOne(targetEntity = BookEntity.class)
    @JoinColumn(name = "BookId", referencedColumnName = "BookId")
    private BookEntity book;

    @ManyToOne(targetEntity = CopyEntity.class)
    @JoinColumn(name = "CopyId", referencedColumnName = "CopyId")
    private CopyEntity copy;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "UserName", referencedColumnName = "UserName")
    private UserEntity user;

    public BookLifeCycleEntity(UserEntity userEntity, BookEntity bookEntity, CopyEntity copyEntity, Date issueDate, Date expectedReturnDate, int fine) {
        this.issueDate = issueDate;
        this.expectedReturnDate = expectedReturnDate;
        this.book = bookEntity;
        this.copy = copyEntity;
        this.user = userEntity;
        this.fine = fine;
    }
}
