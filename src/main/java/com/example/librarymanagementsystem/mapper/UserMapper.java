package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.model.entity.Users;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<Users> {

    @Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Users.builder()
                .userId(rs.getString("userId"))
                .userFirstName(rs.getString("userFirstName"))
                .userLastName(rs.getString("userLastName"))
                .userEmail(rs.getString("userEmail"))
                .userCreditScore(rs.getInt("userCreditScore"))
                .userStatus(rs.getString("userStatus"))
                .build();
    }
}
