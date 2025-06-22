package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.entity.BorrowRecords;
import com.example.librarymanagementsystem.model.requests.BorrowBookRequest;
import com.example.librarymanagementsystem.model.requests.ReturnBookRequest;
import com.example.librarymanagementsystem.service.BorrowRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/borrow")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    @Autowired
    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    @PostMapping("/borrow-book")
    public ResponseEntity<String> borrowBook(@Valid @RequestBody BorrowBookRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            try {
                return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(errors));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Error formatting validation response.");
            }
        }
        borrowRecordService.createUserBorrowRecord(request);
        return ResponseEntity.ok("Book borrowed successfully");
    }

    @PatchMapping("/return-book")
    public ResponseEntity<String> returnBook(@Valid @RequestBody ReturnBookRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            try {
                return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(errors));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Error formatting validation response.");
            }
        }
        borrowRecordService.returnRecord(request);
        return ResponseEntity.ok("Book returned successfully");
    }

    @GetMapping("/get-all-borrow-records")
    public ResponseEntity<List<BorrowRecords>> getAllBorrowRecords() {
        return ResponseEntity.ok(borrowRecordService.getAllBorrowRecords());
    }

    @GetMapping("/get-overdue-records")
    public ResponseEntity<List<BorrowRecords>> getAllOverdueRecords() {
        return ResponseEntity.ok(borrowRecordService.getAllOverdueRecords());
    }

    @GetMapping("/get-current-borrowed")
    public ResponseEntity<List<BorrowRecords>> getAllCurrentBorrowRecords() {
        return ResponseEntity.ok(borrowRecordService.getAllCurrentBorrowRecords());
    }

    @GetMapping("/get-by-user-id/{userId}")
    public ResponseEntity<List<BorrowRecords>> getBorrowRecordsByUserId(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(borrowRecordService.getBorrowRecordsByUserId(userId));
    }

    @GetMapping("/get-by-id/{borrowId}")
    public ResponseEntity<BorrowRecords> getBorrowRecordById(@PathVariable("borrowId") String borrowId) {
        return ResponseEntity.ok(borrowRecordService.getBorrowRecordById(borrowId));
    }

    @GetMapping("/get-by-book-id/{bookId}")
    public ResponseEntity<List<BorrowRecords>> getBorrowRecordsByBookId(@PathVariable("bookId") String bookId) {
        return ResponseEntity.ok(borrowRecordService.getBorrowRecordsByBookId(bookId));
    }
}
