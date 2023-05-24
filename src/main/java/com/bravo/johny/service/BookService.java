package com.bravo.johny.service;

import com.bravo.johny.controller.filterbeans.BookFilterBean;
import com.bravo.johny.dto.Book;
import com.bravo.johny.dto.Genre;
import com.bravo.johny.dto.User;

import java.util.List;

public interface BookService {

    Book addNewBook(Book book);

    List<Book> getBooks(BookFilterBean bookBean);

    Book getBookDetails(int bookId);

    Book updateBookDetails(int bookId, Book book);

    void deleteBook(int bookId);

    List<Genre> getAllGenre();

    List<User> getIssuedUsers(String bookId);
}
