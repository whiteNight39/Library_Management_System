package com.example.librarymanagementsystem.model.requests;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
@Builder
public class BookUpdateRequest {

    @NotBlank(message = "Book Id is required")
    private String bookId;

//    @NotBlank(message = "Book title is required")
    private String bookTitle;

//    @NotBlank(message = "Book author is required")
    private String bookAuthor;

    private String bookIsbn;

    private String bookGenre;

//    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "At least one book must be added")
    private int bookQuantity;
}
