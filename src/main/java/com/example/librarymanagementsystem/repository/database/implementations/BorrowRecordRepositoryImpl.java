package com.example.librarymanagementsystem.repository.database.implementations;

import com.example.librarymanagementsystem.mapper.BorrowRecordsMapper;
import com.example.librarymanagementsystem.model.entity.BorrowRecords;
import com.example.librarymanagementsystem.repository.database.interfaces.BorrowRecordRepository;
import com.example.librarymanagementsystem.repository.database.query.BorrowRecordQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BorrowRecordRepositoryImpl implements BorrowRecordRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BorrowRecordRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public int createUserBorrowRecord(BorrowRecords borrowRecords) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("borrowUserId", borrowRecords.getBorrowUserId())
                .addValue("borrowBookId", borrowRecords.getBorrowBookId())
                .addValue("borrowDuration", borrowRecords.getBorrowDuration());

        // First: insert borrow record
        int rowsInserted = jdbcTemplate.update(BorrowRecordQuery.BORROW_BOOK, params);
        // Second: decrease book quantity
        jdbcTemplate.update(BorrowRecordQuery.DECREASE_BOOK_QUANTITY, params);

        return rowsInserted;
    }

    @Transactional
    @Override
    public int returnRecord(BorrowRecords borrowRecords) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("borrowId", borrowRecords.getBorrowId())
                .addValue("borrowBookId", borrowRecords.getBorrowBookId())
                .addValue("borrowUserId", borrowRecords.getBorrowUserId());

        // First: insert borrow record
        int rowsInserted = jdbcTemplate.update(BorrowRecordQuery.RETURN_BOOK_BY_BORROW_ID, params);
        // Second: increase book quantity
        jdbcTemplate.update(BorrowRecordQuery.INCREASE_BOOK_QUANTITY, params);
        // Third: increase user credit score
        jdbcTemplate.update(BorrowRecordQuery.INCREMENT_USER_CREDIT, params);
        return rowsInserted;
    }

    @Override
    public List<BorrowRecords> getAllBorrowRecords() {

        return jdbcTemplate.query(
                BorrowRecordQuery.GET_ALL_BORROW_RECORDS,
                new BorrowRecordsMapper()
        );
    }


    @Override
    public List<BorrowRecords> getAllOverdueRecords() {

        return jdbcTemplate.query(
                BorrowRecordQuery.GET_ALL_OVERDUE_BORROW_RECORDS,
                new BorrowRecordsMapper()
        );
    }

    @Override
    public List<BorrowRecords> getAllCurrentBorrowRecords() {

        return jdbcTemplate.query(
                BorrowRecordQuery.GET_ALL_ACTIVE_BORROW_RECORDS,
                new BorrowRecordsMapper()
        );
    }

    @Override
    public List<BorrowRecords> getBorrowRecordsByUserId(String borrowUserId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("borrowUserId", borrowUserId);

        return jdbcTemplate.query(
                BorrowRecordQuery.GET_BORROW_RECORDS_BY_USER_ID, params,
                new BorrowRecordsMapper()
        );
    }

    @Override
    public BorrowRecords getBorrowRecordById(String borrowId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("borrowId", borrowId);

        return jdbcTemplate.queryForObject(
                BorrowRecordQuery.GET_BORROW_RECORD_BY_ID, params,
                new BorrowRecordsMapper()
        );
    }

    @Override
    public List<BorrowRecords> getBorrowRecordsByBookId(String borrowBookId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("borrowBookId", borrowBookId);

        return jdbcTemplate.query(
                BorrowRecordQuery.GET_BORROW_RECORDS_BY_BOOK_ID, params,
                new BorrowRecordsMapper()
        );
    }

    @Override
    public int markOverdueBorrowRecords() {
        return jdbcTemplate.update(BorrowRecordQuery.MARK_OVERDUE_BORROW_RECORDS, new MapSqlParameterSource());
    }

    @Override
    public int setPenaltyStage(String borrowPenaltyStage, String borrowId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("borrowPenaltyStage", borrowPenaltyStage)
                .addValue("borrowId", borrowId);

        return jdbcTemplate.update(BorrowRecordQuery.SET_PENALTY_STAGE, params);
    }

    public int setPenalized(int borrowPenalized, String borrowId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("borrowPenalized", borrowPenalized)
                .addValue("borrowId", borrowId);

        return jdbcTemplate.update(BorrowRecordQuery.SET_BORROW_PENALIZED, params);
    }
}
