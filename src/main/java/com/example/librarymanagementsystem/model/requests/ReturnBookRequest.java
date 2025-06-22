package com.example.librarymanagementsystem.model.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReturnBookRequest {

    @NotBlank(message = "Borrow Id is required")
    private String borrowId;
}
