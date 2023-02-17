package com.bravo.johny.repository;

import com.bravo.johny.dto.Genre;
import com.bravo.johny.entity.BookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    // TODO - can be removed
    @Nullable
    BookEntity findFirstByBookId(int bookId);

    Optional<BookEntity> findByBookId(int bookId);

    // TODO - check and remove
    @Nullable
    List<BookEntity> findByTitleAndAuthor(String title, String author);

    @Nullable
    BookEntity findFirstByTitleAndAuthor(String title, String author);

    @Transactional
    void deleteByBookId(int bookId);

    List<BookEntity> findByTitleLike(String title);

    List<BookEntity> findByTitleLike(String title, Pageable pageable);

    List<BookEntity> findByAuthorLike(String author);

    List<BookEntity> findByAuthorLike(String author, Pageable pageable);

    List<BookEntity> findByGenreLike(String genre);

    List<BookEntity> findByGenreLike(String genre, Pageable pageable);

    List<BookEntity> findByTitleLikeAndAuthorLike(String title, String author);

    List<BookEntity> findByTitleLikeAndAuthorLike(String title, String author, Pageable pageable);

    List<BookEntity> findByAuthorLikeAndGenreLike(String author, String genre);

    List<BookEntity> findByAuthorLikeAndGenreLike(String author, String genre, Pageable pageable);

    List<BookEntity> findByGenreLikeAndTitleLike(String genre, String title);

    List<BookEntity> findByGenreLikeAndTitleLike(String genre, String title, Pageable pageable);

    List<BookEntity> findByTitleLikeAndAuthorLikeAndGenreLike(String title, String author, String genre);

    List<BookEntity> findByTitleLikeAndAuthorLikeAndGenreLike(String title, String author, String genre, Pageable pageable);

    @Query("SELECT new com.bravo.johny.dto.Genre(b.genre) FROM BookEntity b GROUP BY b.genre")
    List<Genre> getAllGenre();
}
