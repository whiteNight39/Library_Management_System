package com.example.librarymanagementsystem.repository.database.implementations;

import com.example.librarymanagementsystem.mapper.UserMapper;
import com.example.librarymanagementsystem.model.entity.Books;
import com.example.librarymanagementsystem.model.entity.Users;
import com.example.librarymanagementsystem.repository.database.interfaces.UserRepository;
import com.example.librarymanagementsystem.repository.database.query.UserQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int createUser(Users user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userFirstName", user.getUserFirstName())
                .addValue("userLastName", user.getUserLastName())
                .addValue("userEmail", user.getUserEmail());
        
        return jdbcTemplate.update(UserQuery.INSERT_USER, params);
    }

    @Override
    public int updateUser(Users user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("userFirstName", user.getUserFirstName())
                .addValue("userLastName", user.getUserLastName())
                .addValue("userEmail", user.getUserEmail());
        
        return jdbcTemplate.update(UserQuery.UPDATE_USER, params);
    }

    @Override
    public int deleteUser(String userId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);
        
        return jdbcTemplate.update(UserQuery.DELETE_USER_BY_ID, params);
    }

    @Override
    public Users getUserById(String userId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);
        
        return jdbcTemplate.queryForObject(
                UserQuery.GET_USERS_BY_ID, params,
                new UserMapper()
        );
    }

    @Override
    public List<Users> getAllUsers() {

        return jdbcTemplate.query(
                UserQuery.GET_ALL_USERS,
                new UserMapper()
        );
    }

    @Override
    public List<Users> getAllUsersByQuery(String query) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("query", "%"+query+"%");

        return jdbcTemplate.query(
                UserQuery.GET_USERS_BY_QUERY, params,
                new UserMapper()
        );
    }

    @Override
    public int banUser(String userId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        return jdbcTemplate.update(UserQuery.BAN_USER_BY_ID, params);
    }

    @Override
    public int unBanUser(String userId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        return jdbcTemplate.update(UserQuery.UNBAN_USER_BY_ID, params);
    }

    @Override
    public List<Users> getUsersByStatus(String status) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("status", status);

        return jdbcTemplate.query(
                UserQuery.GET_USERS_BY_STATUS, params,
                new UserMapper()
        );
    }

    @Override
    public List<Users> getUsersWithLowCredit() {

        return jdbcTemplate.query(
                UserQuery.GET_USERS_WITH_LOW_CREDIT,
                new UserMapper()
        );
    }

    @Override
    public int decrementUserCredit(String userId, int amount) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("amount", amount);

        return jdbcTemplate.update(UserQuery.DECREMENT_USER_CREDIT, params);
    }


}
