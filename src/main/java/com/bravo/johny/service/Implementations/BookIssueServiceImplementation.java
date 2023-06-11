package com.bravo.johny.service.Implementations;

import com.bravo.johny.config.ProjectConfig;
import com.bravo.johny.controller.filterbeans.BookIssueFilterBean;
import com.bravo.johny.controller.filterbeans.UserIssuedBookFilterBean;
import com.bravo.johny.dto.BookIssue;
import com.bravo.johny.dto.IssuedBook;
import com.bravo.johny.dto.UserIssuedBook;
import com.bravo.johny.entity.*;
import com.bravo.johny.repository.*;
import com.bravo.johny.service.BookIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bravo.johny.utils.CommonUtils.throwBadRequestRuntimeException;


@Service
public class BookIssueServiceImplementation implements BookIssueService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CopyRepository copyRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookLifeCycleRepository bookLifeCycleRepository;
    @Autowired
    private ConfigRepository configRepository;

    @Override
    public BookIssue issueABook(BookIssue bookIssue) {

        nullFieldValueCheck(bookIssue);

        boolean checkUserName = checkIfUserNameExistsInDatabase(bookIssue.getUserName());
        if(!checkUserName)
            throwBadRequestRuntimeException("UserName : "+bookIssue.getUserName()+" does not exist");

        boolean checkBookId = checkIfBookExistsInDatabase(bookIssue.getBookId());
        if(!checkBookId)
            throwBadRequestRuntimeException("BookEntity with bookEntity-id : "+bookIssue.getBookId()+" does not exist");

        int copyId = isBookAvailable(bookIssue.getBookId());
        if(copyId == 0)
            throwBadRequestRuntimeException("Sorry, all copies of bookEntity with bookEntity-id : "+bookIssue.getBookId()+" have been issued");

        int bookCount = userRepository.findFirstByUserName(bookIssue.getUserName()).getBookCount();
        if(bookCount >= configRepository.findAll().get(0).getNoOfBooksPerUser())
            throwBadRequestRuntimeException("Sorry, You have already exceeded the maximum number of books a userEntity can keep, please return a bookEntity to issue a new one !!");

        bookIssue.setCopyId(copyId);
        clearAllDuesOfAUser(bookIssue.getUserName());
        updateUserDues(bookIssue.getUserName(), 0);
        BookIssue newBookIssue = issue(bookIssue);
        CopyEntity copyEntity = copyRepository.findFirstByCopyId(copyId);
        if(copyEntity != null){
            copyEntity.setIssued(true);
            //copyEntity.setIsIssued(1);
            copyRepository.save(copyEntity);
        }

        UserEntity userEntity = userRepository.findFirstByUserName(bookIssue.getUserName());
        userEntity.setBookCount(bookCount+1);
        userRepository.save(userEntity);

        copyId = isBookAvailable(bookIssue.getBookId());
        if(copyId == 0) {
            BookEntity bookEntity = bookRepository.findFirstByBookId(bookIssue.getBookId());
            if(bookEntity != null){
                bookEntity.setAvailable(false);
                bookRepository.save(bookEntity);
            }
        }
        return newBookIssue;
    }

    @Override
    public BookIssue reIssueABook(BookIssue bookIssue) {

        int bookIssueId = bookIssue.getBookIssueId();

        BookLifeCycleEntity bookLifeCycleEntity = bookLifeCycleRepository.findFirstByBookIssueId(bookIssueId);
        bookIssue.setUserName(bookLifeCycleEntity.getUser().getUserName());
        bookIssue.setExpectedReturnDate(bookLifeCycleEntity.getExpectedReturnDate());
        boolean checkBookIssueId = checkIfBookIssueEntryExists(bookIssueId);
        if(!checkBookIssueId)
            throwBadRequestRuntimeException("BookEntity issue entry : "+bookIssueId+" does not exist");

        boolean checkBookId = checkIfBookExistsInDatabase(bookIssue.getBookId());
        if(!checkBookId)
            throwBadRequestRuntimeException("BookEntity with bookEntity-id : "+bookIssue.getBookId()+" does not exist");

        boolean checkUsername = checkIfUserNameExistsInDatabase(bookIssue.getUserName());
        if(!checkUsername)
            throwBadRequestRuntimeException("Username : "+bookIssue.getUserName()+" does not exist");

        boolean checkReIssueStatus = checkIfBookIsAlreadyReIssued(bookIssueId);
        if(checkReIssueStatus)
            throwBadRequestRuntimeException("BookEntity is already re-issued");

//        bookIssueDAO.clearAllDuesOfAUser(bookIssue.getUserName());
        bookIssue = reIssue(bookIssue);

        UserEntity userEntity = userRepository.findFirstByUserName(bookIssue.getUserName());

        int userdue = bookLifeCycleRepository.getTotalDuesOfAUser(bookIssue.getUserName());

        userEntity.setFine(userdue);
        userRepository.save(userEntity);

        return bookIssue;
    }

    @Override
    public BookIssue returnABook(BookIssue bookIssue) {

        int bookIssueId = bookIssue.getBookIssueId();

        BookLifeCycleEntity bookLifeCycleEntity = bookLifeCycleRepository.findFirstByBookIssueId(bookIssueId);
        bookIssue.setUserName(bookLifeCycleEntity.getUser().getUserName());
        bookIssue.setExpectedReturnDate(bookLifeCycleEntity.getExpectedReturnDate());
        boolean checkBookIssueId = checkIfBookIssueEntryExists(bookIssueId);
        if(!checkBookIssueId)
            throwBadRequestRuntimeException("BookEntity issue entry : "+bookIssueId+" does not exist");

        boolean checkBookId = checkIfBookExistsInDatabase(bookIssue.getBookId());
        if(!checkBookId)
            throwBadRequestRuntimeException("BookEntity with bookEntity-id : "+bookIssue.getBookId()+" does not exist");

        boolean checkUserName = checkIfUserNameExistsInDatabase(bookIssue.getUserName());
        if(!checkUserName)
            throwBadRequestRuntimeException("Username : "+bookIssue.getUserName()+" does not exist");

        boolean checkReturnStatus = checkIfBookIsAlreadyReturned(bookIssueId);
        if(checkReturnStatus)
            throwBadRequestRuntimeException("BookEntity is already returned");

        bookIssue = bookReturn(bookIssue);

        CopyEntity copyEntity = copyRepository.findFirstByCopyId(bookIssue.getCopyId());
        if(copyEntity != null){
            copyEntity.setIssued(false);
            //copyEntity.setIsIssued(0);
            copyRepository.save(copyEntity);
        }


        BookEntity bookEntity = bookRepository.findFirstByBookId(bookIssue.getBookId());
        if(bookEntity != null){
            bookEntity.setAvailable(true);
            bookRepository.save(bookEntity);
        }

        UserEntity userEntity = userRepository.findFirstByUserName(bookIssue.getUserName());

        int bookCount = userEntity.getBookCount();

        int userdue = bookLifeCycleRepository.getTotalDuesOfAUser(bookIssue.getUserName());

        userEntity.setBookCount(bookCount-1);
        userEntity.setFine(userdue);
        userRepository.save(userEntity);

        return bookIssue;
    }

    @Override
    public int isBookAvailable(int bookId) {

        boolean bookIssueStatus = false;
        BookEntity bookEntity = bookRepository.findFirstByBookId(bookId);
        CopyEntity copyEntity = copyRepository.findFirstByBookAndIsIssuedOrderByCopyId(bookEntity, bookIssueStatus);

        return (copyEntity == null) ? 0 : copyEntity.getCopyId();
    }

    @Override
    public void removeBookIssueEntryFromDatabase(int bookIssueId) {

        BookLifeCycleEntity bookLifeCycleEntity = bookLifeCycleRepository.findFirstByBookIssueId(bookIssueId);

        if(bookLifeCycleEntity == null)
            throwBadRequestRuntimeException("No BookEntity Issue entry found with bookEntity-issue-id : "+bookIssueId);

        bookLifeCycleRepository.deleteByBookIssueId(bookIssueId);
    }

    @Override
    public List<BookIssue> getBookIssueEntries(BookIssueFilterBean bookIssueFilterBean) {

        List<BookLifeCycleEntity> bookLifeCycleEntities = null;

        int bookId = bookIssueFilterBean.getBookId();
        String userName = bookIssueFilterBean.getUsername();
        int offset = bookIssueFilterBean.getOffset();
        int limit = bookIssueFilterBean.getLimit();

        BookEntity bookEntity = bookRepository.findFirstByBookId(bookId);
        UserEntity userEntity = userRepository.findFirstByUserName(userName);

        Pageable pageable;
        boolean anyfilter = anyFilterInTheURL(bookIssueFilterBean);

        if(anyfilter) {
            if(bookId !=0 && userName != null) {
                if (offset >= 0 && limit > 0) {
                    pageable = PageRequest.of(offset, limit);
                    bookLifeCycleEntities = bookLifeCycleRepository.findByBookAndUser(bookEntity, userEntity, pageable);
                }
                else
                    bookLifeCycleEntities = bookLifeCycleRepository.findByBookAndUser(bookEntity, userEntity);
            } else if(bookId != 0) {
                if(offset >= 0 && limit > 0) {
                    pageable = PageRequest.of(offset, limit);
                    bookLifeCycleEntities = bookLifeCycleRepository.findByBook(bookEntity, pageable);
                }
                else
                    bookLifeCycleEntities = bookLifeCycleRepository.findByBook(bookEntity);
            } else if(userName != null) {
                if(offset >= 0 && limit > 0) {
                    pageable = PageRequest.of(offset, limit);
                    bookLifeCycleEntities = bookLifeCycleRepository.findByUser(userEntity, pageable);
                }
                else
                    bookLifeCycleEntities = bookLifeCycleRepository.findByUser(userEntity);
            }
        }

        return bookLifeCycleEntities
                .stream()
                .map(bookLifeCycleEntity -> prepareBookIssueDTOFromBookLifeCycleEntity(bookLifeCycleEntity))
                .collect(Collectors.toList());
    }

    @Override
    public BookIssue getBookIssueDetails(int bookIssueId) {

        BookLifeCycleEntity bookLifeCycleEntity = bookLifeCycleRepository.findFirstByBookIssueId(bookIssueId);

        if(bookLifeCycleEntity == null)
            throwBadRequestRuntimeException("No bookEntity issue entry found with bookEntity-issue-id : "+bookIssueId);

        return prepareBookIssueDTOFromBookLifeCycleEntity(bookLifeCycleEntity);
    }

    @Override
    public List<UserIssuedBook> getListOfBooksIssuedToAUser(String userName, UserIssuedBookFilterBean bean) {

        boolean checkUsername = checkIfUserNameExistsInDatabase(userName);
        if(!checkUsername)
            throwBadRequestRuntimeException("Username : "+userName+" does not exist");

        List<UserIssuedBook> userIssuedBooks = null;

        int bookId = bean.getBookId();
        int offset = bean.getOffset();
        int limit = bean.getLimit();

        Pageable pageable;

        if(bookId != 0) {
            if(offset >=0 && limit >0) {
                pageable = PageRequest.of(offset, limit);
                userIssuedBooks = bookLifeCycleRepository.getBooksIssuedByAUser(userName, bookId, pageable);
            } else
                userIssuedBooks = bookLifeCycleRepository.getBooksIssuedByAUser(userName, bookId);
        } else {
            if(offset >=0 && limit >0) {
                pageable = PageRequest.of(offset, limit);
                userIssuedBooks = bookLifeCycleRepository.getBooksIssuedByAUser(userName, pageable);
            }
            else
                userIssuedBooks = bookLifeCycleRepository.getBooksIssuedByAUser(userName);
        }

        userIssuedBooks.forEach(userIssuedBook -> {
            if(userIssuedBook.getActualReturnDate() == null)
                userIssuedBook.setReturned(false);
            else
                userIssuedBook.setReturned(true);
        });

        return userIssuedBooks;
    }

    /*public List<IssuedBook> getListOfAllIssuedBooks() {

        return copyRepository.getAllIssuedBooks();
    }*/



    // ##################### PRIVATE METHODS ######################


    private BookIssue issue(BookIssue bookIssue) {

        ConfigEntity configEntity = configRepository.findAll().get(0);
        UserEntity userEntity = userRepository.findFirstByUserName(bookIssue.getUserName());
        CopyEntity copyEntity = copyRepository.findFirstByCopyId(bookIssue.getCopyId());
        BookEntity bookEntity = bookRepository.findFirstByBookId(bookIssue.getBookId());

        long millis=System.currentTimeMillis();
        Date currentDate = new Date(millis);
        Date expectedReturnDate = addDaysToADate(currentDate, configEntity.getNoOfDaysAUserCanKeepABook());
        int fine = 0;

        BookLifeCycleEntity bookLifeCycleEntity = new BookLifeCycleEntity(
                userEntity, bookEntity, copyEntity, currentDate, expectedReturnDate, fine
        );

        BookLifeCycleEntity newBookLifeCycleEntity = bookLifeCycleRepository.save(bookLifeCycleEntity);

        return prepareBookIssueDTOFromBookLifeCycleEntity(newBookLifeCycleEntity);
    }

    private BookIssue reIssue(BookIssue bookIssue) {

        ConfigEntity configEntity = configRepository.findAll().get(0);
        BookLifeCycleEntity bookLifeCycleEntity = bookLifeCycleRepository.findFirstByBookIssueId(bookIssue.getBookIssueId());

        int numberOfDaysAfterExpectedReturnDate = getDelayInDaysFromCurrentDate(bookIssue.getExpectedReturnDate());
        int fine = bookLifeCycleEntity.getFine();
        if(numberOfDaysAfterExpectedReturnDate > 0)
            fine += numberOfDaysAfterExpectedReturnDate * configEntity.getFinePerDay();

        long millis=System.currentTimeMillis();
        Date currentDate = new Date(millis);
        Date expectedReturnDate = addDaysToADate(currentDate, configEntity.getNoOfDaysAUserCanKeepABook());

        bookLifeCycleEntity.setRenewed(true);
        bookLifeCycleEntity.setFineCleared(false);
        bookLifeCycleEntity.setFine(fine);
        bookLifeCycleEntity.setRenewDate(currentDate);
        bookLifeCycleEntity.setExpectedReturnDate(expectedReturnDate);

        bookLifeCycleEntity = bookLifeCycleRepository.save(bookLifeCycleEntity);

        return prepareBookIssueDTOFromBookLifeCycleEntity(bookLifeCycleEntity);
    }

    private BookIssue bookReturn(BookIssue bookIssue) {

        ConfigEntity configEntity = configRepository.findAll().get(0);
        BookLifeCycleEntity bookLifeCycleEntity = bookLifeCycleRepository.findFirstByBookIssueId(bookIssue.getBookIssueId());

        int numberOfDaysAfterExpectedReturnDate = getDelayInDaysFromCurrentDate(bookIssue.getExpectedReturnDate());
        int fine = bookLifeCycleEntity.getFine();

        if(numberOfDaysAfterExpectedReturnDate > 0)
            fine += numberOfDaysAfterExpectedReturnDate * configEntity.getFinePerDay();

        long millis=System.currentTimeMillis();
        Date currentDate = new Date(millis);

        bookLifeCycleEntity.setReturned(true);
        bookLifeCycleEntity.setFineCleared(false);
        bookLifeCycleEntity.setFine(fine);
        bookLifeCycleEntity.setActualReturnDate(currentDate);

        bookLifeCycleEntity = bookLifeCycleRepository.save(bookLifeCycleEntity);

        return prepareBookIssueDTOFromBookLifeCycleEntity(bookLifeCycleEntity);
    }

    private void clearAllDuesOfAUser(String userName) {

        UserEntity userEntity = userRepository.findFirstByUserName(userName);
        List<BookLifeCycleEntity> booksIssued = bookLifeCycleRepository.findByUser(userEntity);
        booksIssued.forEach(bookIssued -> bookIssued.setFineCleared(true));
        bookLifeCycleRepository.saveAll(booksIssued);
    }

    private void updateUserDues(String userName, int userDue) {

        UserEntity userEntity = userRepository.findFirstByUserName(userName);
        userEntity.setFine(userDue);
        userRepository.save(userEntity);
    }

    private boolean anyFilterInTheURL(BookIssueFilterBean bookIssueFilterBean) {
        if(bookIssueFilterBean.getBookId() != 0 || bookIssueFilterBean.getUsername() != null)
            return true;
        else
            return false;
    }

    private void nullFieldValueCheck(BookIssue bookIssue) {
        if(bookIssue.getBookId() == 0 )
            throwBadRequestRuntimeException("Value for BookEntity Id field is either empty or null !!");
        if(bookIssue.getUserName() == null || bookIssue.getUserName().isEmpty())
            throwBadRequestRuntimeException("Value for Username field is either empty or null !!");
    }

    private boolean checkIfUserNameExistsInDatabase(String userName) {

        Optional<UserEntity> entity = userRepository.findByUserName(userName);

        return entity.isPresent();
    }

    private boolean checkIfBookIssueEntryExists(int bookIssueId) {

        long count = bookLifeCycleRepository.countByBookIssueId(bookIssueId);

        return count != 0;
    }

    private boolean checkIfBookIsAlreadyReturned(int bookIssueId) {

        BookLifeCycleEntity bookLifeCycleEntity = bookLifeCycleRepository.findFirstByBookIssueId(bookIssueId);

        return bookLifeCycleEntity.isReturned();
    }

    private boolean checkIfEmailExistsInDatabase(String email) {

        Optional<UserEntity> entity = userRepository.findByEmail(email);

        return entity.isPresent();
    }

    public boolean checkIfBookExistsInDatabase(String title, String author) {

        List<BookEntity> bookEntities = bookRepository.findByTitleAndAuthor(title, author);

        return bookEntities.size() > 0;
    }

    private boolean checkIfBookExistsInDatabase(int bookId) {

        Optional<BookEntity> entity = bookRepository.findByBookId(bookId);

        return entity.isPresent();
    }

    private boolean checkIfBookIsAlreadyReIssued(int bookIssueId) {

        BookLifeCycleEntity bookLifeCycleEntity = bookLifeCycleRepository.findFirstByBookIssueId(bookIssueId);

        return bookLifeCycleEntity.isRenewed();
    }

    private BookIssue prepareBookIssueDTOFromBookLifeCycleEntity(BookLifeCycleEntity bookLifeCycleEntity) {

        return new BookIssue(
                bookLifeCycleEntity.getBookIssueId(),
                bookLifeCycleEntity.getCopy().getCopyId(),
                bookLifeCycleEntity.getBook().getBookId(),
                bookLifeCycleEntity.getUser().getUserName(),
                bookLifeCycleEntity.isReturned(),
                bookLifeCycleEntity.isRenewed(),
                bookLifeCycleEntity.getIssueDate(),
                bookLifeCycleEntity.getExpectedReturnDate(),
                bookLifeCycleEntity.getActualReturnDate(),
                bookLifeCycleEntity.getRenewDate(),
                bookLifeCycleEntity.getFine(),
                bookLifeCycleEntity.isFineCleared(),
                null
        );
    }

    private Date addDaysToADate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);

        return new Date(c.getTimeInMillis());
    }

    private int getDelayInDaysFromCurrentDate(Date date) {
        long millis=System.currentTimeMillis();
        Date currentDate = new Date(millis);

        int days = (int) ((currentDate.getTime() - date.getTime()) / ProjectConfig.MILLISECONDS_IN_A_DAY);

        if(days < 0)
            days = 0;

        return days;
    }
}