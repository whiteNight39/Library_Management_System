package com.example.librarymanagementsystem.repository.database.interfaces;

import com.example.librarymanagementsystem.model.entity.Users;

import java.util.List;

public interface UserRepository {

    int createUser(Users user);
    int updateUser(Users user);
    int deleteUser(String userId);
    Users getUserById(String userId);
    List<Users> getAllUsers();
    List<Users> getAllUsersByQuery(String query);
    int banUser(String userId);
    int unBanUser(String userId);
    List<Users> getUsersByStatus(String status);
    List<Users> getUsersWithLowCredit();
    int decrementUserCredit(String userId, int amount);
}
