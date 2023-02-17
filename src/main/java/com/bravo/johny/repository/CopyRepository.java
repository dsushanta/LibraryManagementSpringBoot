package com.bravo.johny.repository;

import com.bravo.johny.dto.IssuedBook;
import com.bravo.johny.entity.BookEntity;
import com.bravo.johny.entity.CopyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CopyRepository extends JpaRepository<CopyEntity, Integer> {

    @Nullable
    CopyEntity findFirstByCopyId(int copyId);

    // TODO - Check and remove
    Optional<CopyEntity> findByCopyId(int copyId);

    @Nullable
    List<CopyEntity> findByBook(BookEntity book);

    @Nullable
    List<CopyEntity> findByBook(BookEntity book, Pageable pageable);

    // TODO
    @Nullable
    //CopyEntity findFirstByCopyIdAndIssued(int copyId, boolean isIssued);

    CopyEntity findFirstByCopyIdAndIsIssued(int copyId, boolean isIssued);

    Optional<CopyEntity> findByCopyIdAndIsIssued(int copyId, boolean isIssued);

    @Nullable
    CopyEntity findFirstByBookAndIsIssuedOrderByCopyId(BookEntity book, boolean isIssued);

    //@Nullable
    //long countByBookAndIsIssuedOrderByCopyId(BookEntity bookEntity, boolean isIssued);

    Optional<CopyEntity> findByBookAndIsIssued(BookEntity book, boolean isIssued);

    //CopyEntity findFirstBy(int copyId, boolean isIssued);

    @Transactional
    void deleteByCopyId(int copyId);

    @Transactional
    void deleteByBook(BookEntity bookEntity);

    /*@Query("SELECT new com.bravo.johny.dto.IssuedBook(b.bookId, b.title, b.author, b.genre, COUNT(c.issued)) " +
            "FROM CopyEntity c " +
            "JOIN c.book b " +
            "WHERE c.issued = 1" +
            "GROUP BY b.bookId, b.title, b.author, b.genre " +
            "HAVING COUNT(c.issued) > 0")
    List<IssuedBook> getAllIssuedBooks();*/
}
