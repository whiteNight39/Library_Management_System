package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.model.entity.BorrowRecords;
import com.example.librarymanagementsystem.model.requests.BorrowBookRequest;
import com.example.librarymanagementsystem.model.requests.ReturnBookRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BorrowRecordsRequestMapper {
    BorrowRecords toEntity(BorrowBookRequest request);
    BorrowRecords toEntity(ReturnBookRequest request);
}
