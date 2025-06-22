package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.model.entity.Books;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookMapper implements RowMapper<Books> {

    @Override
    public Books mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Books.builder()
                .bookId(rs.getString("bookId"))
                .bookTitle(rs.getString("bookTitle"))
                .bookAuthor(rs.getString("bookAuthor"))
                .bookIsbn(rs.getString("bookIsbn"))
                .bookGenre(rs.getString("bookGenre"))
                .bookQuantity(rs.getInt("bookQuantity"))
                .bookStatus(rs.getString("bookStatus"))
                .build();
    }
}
