package com.bravo.johny.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="LI_BOOK_COPY")
public class CopyEntity  implements Serializable {

    @Id
    @Column(name = "CopyId")
    @GeneratedValue
    private Integer copyId;

    @Column(name = "IsIssued",
            nullable = false,
            columnDefinition = "tinyint(1) default 1")
    private boolean isIssued;

    @ManyToOne(targetEntity = BookEntity.class)
    @JoinColumn(name = "BookId", referencedColumnName = "BookId")
    private BookEntity book;

    @OneToMany(mappedBy = "copy", orphanRemoval = true)
    private List<BookLifeCycleEntity> copiesIssued;

    public CopyEntity(BookEntity book) {
        this.book = book;
    }
}
