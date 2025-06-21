package com.example.librarymanagementsystem.model.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreateRequest {

    @NotBlank(message = "User first name is required")
    private String userFirstName;

    @NotBlank(message = "User last name is required")
    private String userLastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "User email is required")
    private String userEmail;
}
