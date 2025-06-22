package com.example.librarymanagementsystem.scheduler;

import com.example.librarymanagementsystem.repository.database.interfaces.BorrowRecordRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OverdueStatusScheduler {

    private final BorrowRecordRepository borrowRecordRepository;

    public OverdueStatusScheduler(BorrowRecordRepository borrowRecordRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateOverdueStatus(){
        borrowRecordRepository.markOverdueBorrowRecords();
    }
}
