package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.model.entity.Books;
import com.example.librarymanagementsystem.model.requests.BookCreateRequest;
import com.example.librarymanagementsystem.model.requests.BookUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookRequestMapper {
    Books toEntity(BookCreateRequest request);
    Books toEntity(BookUpdateRequest request);
}