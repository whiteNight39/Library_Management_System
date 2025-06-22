package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.entity.Users;
import com.example.librarymanagementsystem.model.requests.UserCreateRequest;
import com.example.librarymanagementsystem.model.requests.UserUpdateRequest;
import com.example.librarymanagementsystem.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    private final UserService usersService;

    @Autowired
    public UserController(UserService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            try {
                return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(errors));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Error formatting validation response.");
            }
        }
        usersService.createUser(request);
        return ResponseEntity.ok("User created successfully");
    }

    @PatchMapping("/update-user")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserUpdateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            try {
                return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(errors));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Error formatting validation response.");
            }
        }
        usersService.updateUser(request);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
        usersService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/get-user-by-id/{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(usersService.getUserById(userId));
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    @GetMapping("/get-users-by-query/{query}")
    public ResponseEntity<List<Users>> getAllUsersByQuery(@PathVariable("query") String query) {
        return ResponseEntity.ok(usersService.getAllUsersByQuery(query));
    }

    @PatchMapping("/ban-user/{userId}")
    public ResponseEntity<String> banUser(@PathVariable("userId") String userId) {
        usersService.banUser(userId);
        return ResponseEntity.ok("User banned successfully");
    }

    @PatchMapping("/unban-user/{userId}")
    public ResponseEntity<String> unBanUser(@PathVariable("userId") String userId) {
        usersService.unBanUser(userId);
        return ResponseEntity.ok("User unbanned successfully");
    }

    @GetMapping("/get-users-by-status/{status}")
    public ResponseEntity<List<Users>> getUsersByStatus(@PathVariable("status") String status) {
        return ResponseEntity.ok(usersService.getUsersByStatus(status));
    }

    @GetMapping("/get-users-with-low-credit")
    public ResponseEntity<List<Users>> getUsersWithLowCredit() {
        return ResponseEntity.ok(usersService.getUsersWithLowCredit());
    }
}
