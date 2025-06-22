package com.example.librarymanagementsystem.repository.database.implementations;

import com.example.librarymanagementsystem.mapper.BookMapper;
import com.example.librarymanagementsystem.model.entity.Books;
import com.example.librarymanagementsystem.repository.database.interfaces.BookRepository;
import com.example.librarymanagementsystem.repository.database.query.BookQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createBook(Books book) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("bookTitle", book.getBookTitle())
                .addValue("bookAuthor", book.getBookAuthor())
                .addValue("bookIsbn", book.getBookIsbn())
                .addValue("bookGenre", book.getBookGenre())
                .addValue("bookQuantity", book.getBookQuantity());
        return jdbcTemplate.update(BookQuery.INSERT_BOOK, params);
    }

    @Override
    public int updateBook(Books book) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("bookId", book.getBookId())
                .addValue("bookTitle", book.getBookTitle())
                .addValue("bookAuthor", book.getBookAuthor())
                .addValue("bookIsbn", book.getBookIsbn())
                .addValue("bookGenre", book.getBookGenre())
                .addValue("bookQuantity", book.getBookQuantity());
        return jdbcTemplate.update(BookQuery.UPDATE_BOOK, params);
    }

    @Override
    public int deleteBook(String bookId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("bookId", bookId);
        return jdbcTemplate.update(BookQuery.DELETE_BOOK_BY_ID, params);
    }

    @Override
    public Books getBookById(String bookId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("bookId", bookId);
        return jdbcTemplate.queryForObject(
                BookQuery.GET_BOOKS_BY_ID, params,
                new BookMapper()
        );
    }

    @Override
    public List<Books> getAllBooks() {

        return jdbcTemplate.query(
                BookQuery.GET_ALL_BOOKS,
                new BookMapper()
        );
    }

    @Override
    public List<Books> getAvailableBooks() {

        return jdbcTemplate.query(
                BookQuery.GET_ALL_AVAILABLE_BOOKS,
                new BookMapper()
        );
    }

    @Override
    public List<Books> getBooksByQuery(String query) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("query", "%"+query+"%");

        return jdbcTemplate.query(
                BookQuery.GET_BOOKS_BY_QUERY, params,
                new BookMapper()
        );
    }
}
