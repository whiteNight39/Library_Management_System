package com.example.librarymanagementsystem.scheduler;

import com.example.librarymanagementsystem.model.entity.BorrowRecords;
import com.example.librarymanagementsystem.model.entity.Users;
import com.example.librarymanagementsystem.repository.database.interfaces.BorrowRecordRepository;
import com.example.librarymanagementsystem.repository.database.interfaces.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class PenaltyDeductionScheduler {

    private final BorrowRecordRepository borrowRecordRepository;
    private final UserRepository userRepository;

    public PenaltyDeductionScheduler(BorrowRecordRepository borrowRecordRepository, UserRepository userRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 2 * * ?") // Runs daily at 2:00 AM
    public void deductCreditForOverdueUsers() {
        List<BorrowRecords> overdueRecords = borrowRecordRepository.getAllOverdueRecords();

        for (BorrowRecords record : overdueRecords) {
            if (record.getBorrowReturnDate() != null) continue;

            Users user = userRepository.getUserById(record.getBorrowUserId());
            if (user.getUserStatus().equalsIgnoreCase("BANNED")) continue;

            String borrowId = record.getBorrowId();
            long overdueDays = ChronoUnit.DAYS.between(record.getBorrowDueDate().toLocalDate(), LocalDate.now());
            String expectedStage = determinePenaltyStage(overdueDays);

//            if (expectedStage.equalsIgnoreCase("None")) {
//                expectedStage = record.getBorrowPenaltyStage();
//            }

            // Update penalty stage and reset penalized only if necessary
            if (!expectedStage.equalsIgnoreCase(record.getBorrowPenaltyStage())) {
                borrowRecordRepository.setPenaltyStage(expectedStage, borrowId);
                borrowRecordRepository.setPenalized(0, borrowId);
            }

            int penalizedFlag = record.getBorrowPenalized();
            String currentStage = expectedStage; // or record.getBorrowPenaltyStage() if already updated in DB

            // Apply penalty based on stage if not already penalized
            if (currentStage.equalsIgnoreCase("Stage 1") && penalizedFlag == 0) {
                userRepository.decrementUserCredit(user.getUserId(), 0);
                borrowRecordRepository.setPenalized(1, borrowId);
            } else if (currentStage.equalsIgnoreCase("Stage 2") && penalizedFlag == 0) {
                userRepository.decrementUserCredit(user.getUserId(), 1);
                borrowRecordRepository.setPenalized(1, borrowId);
            } else if (currentStage.equalsIgnoreCase("Stage 3") && penalizedFlag == 0) {
                userRepository.decrementUserCredit(user.getUserId(), 2);
                borrowRecordRepository.setPenalized(1, borrowId);
            } else if (currentStage.equalsIgnoreCase("Daily") && penalizedFlag == 0) {
                userRepository.decrementUserCredit(user.getUserId(), 1);
                borrowRecordRepository.setPenalized(1, borrowId);
            }

            // Auto-ban if credit score is too low
            if (userRepository.getUserById(user.getUserId()).getUserCreditScore() <= -10) {
                userRepository.banUser(user.getUserId());
            }
        }
    }

    private String determinePenaltyStage(long overdueDays) {
        if (overdueDays == 1) return "Stage 1";
        else if (overdueDays == 3) return "Stage 2";
        else if (overdueDays == 8) return "Stage 3";
        else if (overdueDays >= 15) return "Daily";
        else return "None";
    }
}
