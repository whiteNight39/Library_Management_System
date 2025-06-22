package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.mapper.BorrowRecordsRequestMapper;
import com.example.librarymanagementsystem.model.entity.Books;
import com.example.librarymanagementsystem.model.entity.BorrowRecords;
import com.example.librarymanagementsystem.model.entity.Users;
import com.example.librarymanagementsystem.model.requests.BorrowBookRequest;
import com.example.librarymanagementsystem.model.requests.ReturnBookRequest;
import com.example.librarymanagementsystem.repository.database.interfaces.BookRepository;
import com.example.librarymanagementsystem.repository.database.interfaces.BorrowRecordRepository;
import com.example.librarymanagementsystem.repository.database.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowRecordService {

    private BorrowRecordRepository borrowRecordRepository;
    private BorrowRecordsRequestMapper borrowRecordsRequestMapper;
    private UserRepository userRepository;
    private BookRepository  bookRepository;

    @Autowired
    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository, BorrowRecordsRequestMapper borrowRecordsRequestMapper,  UserRepository userRepository, BookRepository bookRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.borrowRecordsRequestMapper = borrowRecordsRequestMapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void createUserBorrowRecord(BorrowBookRequest request) {
        // 1. Check user
        Users user = userRepository.getUserById(request.getBorrowUserId());
        if (user == null) throw new IllegalArgumentException("User not found");
        if ("BANNED".equalsIgnoreCase(user.getUserStatus()) || "DELETED".equalsIgnoreCase(user.getUserStatus())) {
            throw new IllegalStateException("User is not allowed to borrow books");
        }

        // 2. Check book
        Books book = bookRepository.getBookById(request.getBorrowBookId());
        if (book == null) throw new IllegalArgumentException("Book not found");
        if (book.getBookQuantity() <= 1 || "UNAVAILABLE".equalsIgnoreCase(book.getBookStatus())) {
            throw new IllegalStateException("Book is not available for borrowing");
        }

        // 3. Proceed
        BorrowRecords borrowRecords = borrowRecordsRequestMapper.toEntity(request);
        borrowRecordRepository.createUserBorrowRecord(borrowRecords);
    }

    public void returnRecord(ReturnBookRequest request) {
        BorrowRecords borrowRecords = borrowRecordRepository.getBorrowRecordById(request.getBorrowId());
//        BorrowRecords borrowRecords = borrowRecordsRequestMapper.toEntity(request);
//        borrowRecords.setBorrowBookId();
        borrowRecordRepository.returnRecord(borrowRecords);
    }

    public List<BorrowRecords> getAllBorrowRecords() {
        return borrowRecordRepository.getAllBorrowRecords();
    }

    public List<BorrowRecords> getAllOverdueRecords() {
        return borrowRecordRepository.getAllOverdueRecords();
    }

    public List<BorrowRecords> getAllCurrentBorrowRecords() {
        return borrowRecordRepository.getAllCurrentBorrowRecords();
    }

    public List<BorrowRecords> getBorrowRecordsByUserId(String borrowUserId) {
        return borrowRecordRepository.getBorrowRecordsByUserId(borrowUserId);
    }

    public List<BorrowRecords> getBorrowRecordsByBookId(String borrowBookId) {
        return borrowRecordRepository.getBorrowRecordsByBookId(borrowBookId);
    }

    public BorrowRecords getBorrowRecordById(String borrowId) {
        return borrowRecordRepository.getBorrowRecordById(borrowId);
    }

}
