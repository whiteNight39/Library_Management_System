package com.example.librarymanagementsystem.model.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BorrowRecords {

    private String borrowId;
    private String borrowUserId;
    private String borrowBookId;
    private LocalDateTime borrowBorrowDate;
    private LocalDateTime borrowReturnDate;
    private LocalDateTime borrowDueDate;
    private String borrowStatus;
    private int borrowDuration;
    private String borrowPenaltyStage;
    private int borrowPenalized;
    private LocalDateTime borrowCreatedAt;
    private LocalDateTime borrowUpdatedAt;
}
