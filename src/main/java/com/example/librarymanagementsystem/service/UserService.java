package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.mapper.UserRequestMapper;
import com.example.librarymanagementsystem.model.entity.Users;
import com.example.librarymanagementsystem.model.requests.UserCreateRequest;
import com.example.librarymanagementsystem.model.requests.UserUpdateRequest;
import com.example.librarymanagementsystem.repository.database.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserRequestMapper userRequestMapper) {
        this.userRepository = userRepository;
        this.userRequestMapper = userRequestMapper;
    }

    public void createUser(UserCreateRequest request) {
        Users user = userRequestMapper.toEntity(request);
        userRepository.createUser(user);
    }

    public void updateUser(UserUpdateRequest request) {
        Users user = userRequestMapper.toEntity(request);
        userRepository.updateUser(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }

    public Users getUserById(String userId) {
        return userRepository.getUserById(userId);
    }

    public List<Users> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public List<Users> getAllUsersByQuery(String query) {
        return userRepository.getAllUsersByQuery(query);
    }

    public void banUser(String userId) {
        userRepository.banUser(userId);
    }

    public void unBanUser(String userId) {
        userRepository.unBanUser(userId);
    }

    public List<Users> getUsersByStatus(String status) {
        return userRepository.getUsersByStatus(status);
    }

    public List<Users> getUsersWithLowCredit() {
        return userRepository.getUsersWithLowCredit();
    }
}
