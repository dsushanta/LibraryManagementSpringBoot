package com.bravo.johny.service.Implementations;

import com.bravo.johny.controller.filterbeans.BookFilterBean;
import com.bravo.johny.dto.Book;
import com.bravo.johny.dto.Genre;
import com.bravo.johny.dto.User;
import com.bravo.johny.entity.BookEntity;
import com.bravo.johny.entity.CopyEntity;
import com.bravo.johny.repository.BookLifeCycleRepository;
import com.bravo.johny.repository.BookRepository;
import com.bravo.johny.repository.CopyRepository;
import com.bravo.johny.service.BookService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bravo.johny.utils.CommonUtils.throwBadRequestRuntimeException;


@Service
@NoArgsConstructor
public class BookServiceImplementation implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CopyRepository copyRepository;
    @Autowired
    private BookLifeCycleRepository bookLifeCycleRepository;

    @Override
    public Book addNewBook(Book book) {

        nullFieldValueCheck(book);

        boolean bookExists = checkIfBookExistsInDatabase(book.getTitle(), book.getAuthor());
        BookEntity bookEntity;
        if(bookExists) {
            bookEntity = bookRepository.findFirstByTitleAndAuthor(book.getTitle(), book.getAuthor());
            bookEntity.setAvailable(true);
            bookRepository.save(bookEntity);
        } else
            bookEntity = bookRepository.save(prepareBookEntityFromBookDTO(book));

        return prepareBookDTOFromBookEntity(bookEntity);
    }

    @Override
    public List<Book> getBooks(BookFilterBean bookBean) {

        List<BookEntity> bookEntities = null;

        String title = bookBean.getTitle();
        String author = bookBean.getAuthor();
        String genre = bookBean.getGenre();
        int offset = bookBean.getOffset();
        int limit = bookBean.getLimit();

        Pageable pageable = null;

        if(offset >= 0 && limit > 0)
            pageable = PageRequest.of(offset, limit);

        boolean anyfilter = anyFilterInTheURL(bookBean);
        if(anyfilter) {
            if (title != null && author != null && genre != null) {
                if (offset >= 0 && limit > 0)
                    bookEntities = bookRepository.findByTitleLikeAndAuthorLikeAndGenreLike(title, author, genre, pageable);
                else
                    bookEntities = bookRepository.findByTitleLikeAndAuthorLikeAndGenreLike(title, author, genre);
            } else if (title != null && author != null) {
                if (offset >= 0 && limit > 0)
                    bookEntities = bookRepository.findByTitleLikeAndAuthorLike(title, author, pageable);
                else
                    bookEntities = bookRepository.findByTitleLikeAndAuthorLike(title, author);
            } else if (author != null && genre != null) {
                if (offset >= 0 && limit > 0)
                    bookEntities = bookRepository.findByAuthorLikeAndGenreLike(author, genre, pageable);
                else
                    bookEntities = bookRepository.findByAuthorLikeAndGenreLike(author, genre);
            } else if (genre != null && title != null) {
                if (offset >= 0 && limit > 0)
                    bookEntities = bookRepository.findByGenreLikeAndTitleLike(genre, title, pageable);
                else
                    bookEntities = bookRepository.findByGenreLikeAndTitleLike(genre, title);
            } else if (title != null) {
                if (offset >= 0 && limit > 0)
                    bookEntities = bookRepository.findByTitleLike(title, pageable);
                else
                    bookEntities = bookRepository.findByTitleLike(title);
            } else if (author != null) {
                if (offset >= 0 && limit > 0)
                    bookEntities = bookRepository.findByAuthorLike(author, pageable);
                else
                    bookEntities = bookRepository.findByAuthorLike(author);
            } else if (genre != null) {
                if (offset >= 0 && limit > 0)
                    bookEntities = bookRepository.findByGenreLike(genre, pageable);
                else
                    bookEntities = bookRepository.findByGenreLike(genre);
            }
        } else {
            if (offset >= 0 && limit > 0)
                bookEntities = bookRepository.findAll(pageable).toList();
            else
                bookEntities = bookRepository.findAll();
        }

        return bookEntities
                .stream()
                .map(bookEntity -> prepareBookDTOFromBookEntity(bookEntity))
                .collect(Collectors.toList());
    }

    @Override
    public Book getBookDetails(int bookId) {

        Optional<BookEntity> entity = bookRepository.findByBookId(bookId);

        if(!entity.isPresent())
            throwBadRequestRuntimeException("No bookEntity found with bookId : "+bookId);

        return prepareBookDTOFromBookEntity(entity.get());
    }

    @Override
    public Book updateBookDetails(int bookId, Book book) {

        Optional<BookEntity> entity = bookRepository.findByBookId(bookId);

        if(!entity.isPresent())
            throwBadRequestRuntimeException("No bookEntity found with bookId : "+bookId);

        BookEntity bookEntityToBeUpdated = entity.get();
        if(book.getTitle() != null && !book.getTitle().isEmpty())
            bookEntityToBeUpdated.setTitle(book.getTitle());
        if(book.getDescription() != null && !book.getDescription().isEmpty())
            bookEntityToBeUpdated.setDescription(book.getDescription());
        if(book.getAuthor() != null && !book.getAuthor().isEmpty())
            bookEntityToBeUpdated.setAuthor(book.getAuthor());
        if(book.getGenre() != null && !book.getGenre().isEmpty())
            bookEntityToBeUpdated.setGenre(book.getGenre());

        BookEntity bookEntity = bookRepository.save(bookEntityToBeUpdated);

        return prepareBookDTOFromBookEntity(bookEntity);
    }

    @Override
    public void deleteBook(int bookId) {

        Optional<BookEntity> entity = bookRepository.findByBookId(bookId);

        if(!entity.isPresent())
            throwBadRequestRuntimeException("No bookEntity found with bookId : "+bookId);
        else if(checkIfAnyCopyOfABookIsIssued(bookId))
            throwBadRequestRuntimeException("BookEntity entry can not be removed as the bookEntity has been issued to a customer !!");
        else {
            bookLifeCycleRepository.deleteByBook(entity.get());
            copyRepository.deleteByBook(entity.get());
            bookRepository.deleteByBookId(bookId);
        }
    }

    @Override
    public List<Genre> getAllGenre() {

        return bookRepository.getAllGenre();
    }

    // TODO
    @Override
    public List<User> getIssuedUsers(String bookId) {
        return bookLifeCycleRepository.getIssuedUsers(bookId);
    }

    // ##################### PRIVATE METHODS ######################

    private void nullFieldValueCheck(Book book) {

        if(book.getTitle() == null || book.getTitle().isEmpty())
            throwBadRequestRuntimeException("Value for Title field is either empty or null !!");
        if(book.getDescription() == null || book.getDescription().isEmpty())
            throwBadRequestRuntimeException("Value for Description field is either empty or null !!");
        if(book.getAuthor() == null || book.getAuthor().isEmpty())
            throwBadRequestRuntimeException("Value for Author field is either empty or null !!");
        if(book.getGenre() == null || book.getGenre().isEmpty())
            throwBadRequestRuntimeException("Value for Genre field is either empty or null !!");
    }

    private boolean anyFilterInTheURL(BookFilterBean bookBean) {

        if(bookBean.getAuthor() != null || bookBean.getTitle() != null || bookBean.getGenre() != null)
            return true;
        else
            return false;
    }

    public boolean checkIfBookExistsInDatabase(String title, String author) {

        List<BookEntity> bookEntities = bookRepository.findByTitleAndAuthor(title, author);

        return bookEntities.size() > 0;
    }

    public boolean checkIfBookExistsInDatabase(int bookId) {

        Optional<BookEntity> entity = bookRepository.findByBookId(bookId);

        return entity.isPresent();
    }

    public boolean checkIfAnyCopyOfABookIsIssued(int bookId) {

        Optional<BookEntity> entityBook = bookRepository.findByBookId(bookId);
        Optional<CopyEntity> entity = copyRepository.findByBookAndIsIssued(entityBook.get(), true);

        return entity.isPresent();
    }

    public boolean checkIfCopyOfABookIsIssued(int copyId) {

        Optional<CopyEntity> entity = copyRepository.findByCopyIdAndIsIssued(copyId, true);

        return entity.isPresent();
    }

    private BookEntity prepareBookEntityFromBookDTO(Book book) {

        return new BookEntity(
                book.getTitle(),
                book.getDescription(),
                book.getAuthor(),
                book.getGenre(),
                book.getLikes()
        );
    }

    private Book prepareBookDTOFromBookEntity(BookEntity bookEntity) {

        Book book = new Book();
        book.setBookId(bookEntity.getBookId());
        book.setTitle(bookEntity.getTitle());
        book.setDescription(bookEntity.getDescription());
        book.setAuthor(bookEntity.getAuthor());
        book.setGenre(bookEntity.getGenre());
        book.setAvailable(bookEntity.isAvailable());
        book.setLikes(bookEntity.getLikes());

        return book;
    }
}
