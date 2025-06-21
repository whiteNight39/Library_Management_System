package com.example.librarymanagementsystem.repository.database.interfaces;

import com.example.librarymanagementsystem.model.entity.Books;

import java.util.List;


public interface BookRepository {

    int createBook(Books book);
    int updateBook(Books book);
    int deleteBook(String bookId);
    Books getBookById(String bookId);
    List<Books> getAllBooks();
    List<Books> getAvailableBooks();
    List<Books> getBooksByQuery(String query);

}
