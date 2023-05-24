package com.bravo.johny.service;

import com.bravo.johny.controller.filterbeans.BookIssueFilterBean;
import com.bravo.johny.controller.filterbeans.UserIssuedBookFilterBean;
import com.bravo.johny.dto.BookIssue;
import com.bravo.johny.dto.UserIssuedBook;

import java.util.List;

public interface BookIssueService {

    BookIssue issueABook(BookIssue bookIssue);

    BookIssue reIssueABook(BookIssue bookIssue);

    BookIssue returnABook(BookIssue bookIssue);

    int isBookAvailable(int bookId);

    void removeBookIssueEntryFromDatabase(int bookIssueId);

    List<BookIssue> getBookIssueEntries(BookIssueFilterBean bookIssueFilterBean);

    BookIssue getBookIssueDetails(int bookIssueId);

    List<UserIssuedBook> getListOfBooksIssuedToAUser(String userName, UserIssuedBookFilterBean bean);
}
