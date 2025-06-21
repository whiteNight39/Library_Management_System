package com.example.librarymanagementsystem.model.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Users {

    private String userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userStatus;
    private int userCreditScore;
    private LocalDateTime userCreatedAt;
    private LocalDateTime userUpdatedAt;
}
