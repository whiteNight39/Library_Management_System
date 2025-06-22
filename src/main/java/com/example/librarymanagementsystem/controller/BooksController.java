package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.entity.Books;
import com.example.librarymanagementsystem.model.requests.BookCreateRequest;
import com.example.librarymanagementsystem.model.requests.BookUpdateRequest;
import com.example.librarymanagementsystem.service.BooksService;
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
@RequestMapping("/book")
public class BooksController {

    private BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PostMapping("/create-book")
    public ResponseEntity<String> createBook(@Valid @RequestBody BookCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            Map<String, String> response = new HashMap<>();
            response.put("status", String.valueOf(400));
            response.put("errors", errors.toString());

            try {
                String json = new ObjectMapper().writeValueAsString(response);
                return ResponseEntity.badRequest().body(json);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Error formatting validation response.");
            }
        }
        booksService.createBook(request);
        return ResponseEntity.ok().body("Book created successfully");
    }

    @PatchMapping("/update-book")
    public ResponseEntity<String> updateBook(@Valid @RequestBody BookUpdateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            Map<String, String> response = new HashMap<>();
            response.put("status", String.valueOf(400));
            response.put("errors", errors.toString());

            try {
                String json = new ObjectMapper().writeValueAsString(response);
                return ResponseEntity.badRequest().body(json);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Error formatting validation response.");
            }
        }
        booksService.updateBook(request);
        return ResponseEntity.ok().body("Book updated successfully");
    }

    @DeleteMapping("/delete-book/{bookId}")
    public ResponseEntity<String> deleteBook(@Valid @PathVariable("bookId") String bookId) {
        booksService.deleteBook(bookId);
        return ResponseEntity.ok().body("Book deleted successfully");
    }

    @GetMapping("/get-book-by-id/{bookId}")
    public ResponseEntity<Books> getBookById(@Valid @PathVariable("bookId") String bookId) {
        Books books = booksService.getBookById(bookId);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/get-all-books")
    public ResponseEntity<List<Books>> getAllBooks() {
        List<Books> books = booksService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/get-available-books")
    public ResponseEntity<List<Books>> getAvailableBooks() {
        List<Books> books = booksService.getAvailableBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/get-book-by-query/{query}")
    public ResponseEntity<List<Books>> getBooksByQuery(@Valid @PathVariable("query") String query) {
        List<Books> books = booksService.getBooksByQuery(query);
        return ResponseEntity.ok(books);
    }
}
