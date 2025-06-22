package com.example.librarymanagementsystem.repository.database.interfaces;

import com.example.librarymanagementsystem.model.entity.BorrowRecords;
import com.example.librarymanagementsystem.model.entity.Users;

import java.util.List;

public interface BorrowRecordRepository {

    int createUserBorrowRecord(BorrowRecords borrowRecords);
    int returnRecord(BorrowRecords borrowRecords);
    List<BorrowRecords> getAllBorrowRecords();
    List<BorrowRecords> getAllOverdueRecords();
    List<BorrowRecords> getAllCurrentBorrowRecords();
    List<BorrowRecords> getBorrowRecordsByUserId(String borrowUserId);
    BorrowRecords getBorrowRecordById(String borrowId);
    List<BorrowRecords> getBorrowRecordsByBookId(String borrowBookId);

    int markOverdueBorrowRecords();
    int setPenaltyStage(String borrowPenaltyStage, String borrowId);
    int setPenalized(int borrowPenalized, String borrowId);
}
