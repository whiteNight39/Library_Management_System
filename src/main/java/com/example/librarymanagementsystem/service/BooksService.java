package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.mapper.BookMapper;
import com.example.librarymanagementsystem.mapper.BookRequestMapper;
import com.example.librarymanagementsystem.model.entity.Books;
import com.example.librarymanagementsystem.model.requests.BookCreateRequest;
import com.example.librarymanagementsystem.model.requests.BookUpdateRequest;
import com.example.librarymanagementsystem.repository.database.interfaces.BookRepository;
import com.example.librarymanagementsystem.repository.database.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BooksService {

    private final BookRepository bookRepository;
    private final BookRequestMapper bookRequestMapper;

    @Autowired
    public BooksService(BookRepository bookRepository, BookRequestMapper bookRequestMapper) {
        this.bookRepository = bookRepository;
        this.bookRequestMapper = bookRequestMapper;
    }

    public void createBook(BookCreateRequest request) {
        try {
            Books books = bookRequestMapper.toEntity(request);
            bookRepository.createBook(books);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create book");
        }
    }

    public void updateBook(BookUpdateRequest request) {
        try {
            Books books = bookRequestMapper.toEntity(request);
            bookRepository.updateBook(books);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update book/This didn't work");
        }
    }

    public Books getBookById(String bookId) {
        return bookRepository.getBookById(bookId);
    }

    public List<Books> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public void deleteBook(String bookId) {
        bookRepository.deleteBook(bookId);
    }

    public List<Books> getAvailableBooks() {
        return bookRepository.getAvailableBooks();
    }

    public List<Books> getBooksByQuery(String query) {
        return bookRepository.getBooksByQuery(query);
    }
}
