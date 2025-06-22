package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.model.entity.BorrowRecords;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BorrowRecordsMapper implements RowMapper<BorrowRecords> {

    @Override
    public BorrowRecords mapRow(ResultSet rs, int rowNum) throws SQLException {

        return BorrowRecords.builder()
            .borrowId(rs.getString("borrowId"))
            .borrowUserId(rs.getString("borrowUserId"))
            .borrowBookId(rs.getString("borrowBookId"))
            .borrowBorrowDate(rs.getTimestamp("borrowBorrowDate").toLocalDateTime())
            .borrowReturnDate(rs.getTimestamp("borrowReturnDate") != null ? rs.getTimestamp("borrowReturnDate").toLocalDateTime() : null)
            .borrowDueDate(rs.getTimestamp("borrowDueDate").toLocalDateTime())
            .borrowStatus(rs.getString("borrowStatus"))
            .borrowDuration(rs.getInt("borrowDuration"))
            .build();
    }
}
