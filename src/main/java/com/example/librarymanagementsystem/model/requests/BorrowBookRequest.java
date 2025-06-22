package com.example.librarymanagementsystem.model.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BorrowBookRequest {

    @NotBlank(message = "uSER Id is required")
    private String borrowUserId;

    @NotBlank(message = "Book Id is required")
    private String borrowBookId;

    @NotNull(message = "Borrow duration is required")
    @Min(value = 1, message = "Book must be borrowed for at least one day")
    private int borrowDuration;
}
