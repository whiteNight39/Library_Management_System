package com.example.librarymanagementsystem.model.entity;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Books {

    private String bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookIsbn;
    private String bookGenre;
    private int bookQuantity;
    private String bookStatus;
    private LocalDateTime bookCreatedAt;
    private LocalDateTime bookUpdatedAt;
}
