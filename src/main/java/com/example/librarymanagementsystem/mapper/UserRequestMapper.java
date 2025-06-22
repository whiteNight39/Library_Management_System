package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.model.entity.Users;
import com.example.librarymanagementsystem.model.requests.UserCreateRequest;
import com.example.librarymanagementsystem.model.requests.UserUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {
    Users toEntity(UserCreateRequest request);
    Users toEntity(UserUpdateRequest request);
}
