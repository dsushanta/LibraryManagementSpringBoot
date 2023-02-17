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
@Table(name="LI_BOOK")
public class BookEntity implements Serializable {

    @Id
    @Column(name = "BookId")
    @GeneratedValue
    private Integer bookId;

    @Column(name = "Title",
            nullable = false,
            columnDefinition = "TEXT")
    private String title;

    @Column(name = "Description",
            nullable = false,
            columnDefinition = "TEXT")
    private String description;

    @Column(name = "Author",
            nullable = false,
            columnDefinition = "TEXT")
    private String author;

    @Column(name = "Genre",
            nullable = false)
    private String genre;

    @Column(name = "Likes",
            nullable = false,
            columnDefinition = "integer default 0")
    private Integer likes;

    @Column(name = "Available",
            nullable = false,
            columnDefinition = "tinyint(1) default 1")
    private boolean available;

    @OneToMany(mappedBy = "book", orphanRemoval = true)
    private List<CopyEntity> copyEntities;

    @OneToMany(mappedBy = "book", orphanRemoval = true)
    private List<BookLifeCycleEntity> copiesIssued;

    public BookEntity(String title, String description, String author, String genre) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.genre = genre;
    }

    public BookEntity(String title, String description, String author, String genre, int likes) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.genre = genre;
        this.likes = likes;
    }
}
