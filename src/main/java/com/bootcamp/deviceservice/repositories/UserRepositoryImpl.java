package com.bootcamp.deviceservice.repositories;

import com.bootcamp.deviceservice.domain.User;
import com.bootcamp.deviceservice.exceptions.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private static final String SQL_CREATE = "INSERT INTO USERS(USERNAME, PASSWORD) VALUES(?, ?)";
    private static final String SQL_COUNT_BY_USERNAME = "SELECT COUNT(*) FROM USERS WHERE USERNAME = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, USERNAME, PASSWORD, CREATED_AT FROM USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_USERNAME = "SELECT USER_ID, USERNAME, PASSWORD, CREATED_AT FROM USERS WHERE USERNAME = ?";


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String username, String password) throws AuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, username);
                ps.setString(2, password);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        } catch(Exception e) {
            throw new AuthException("Invalid details. ");
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws AuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_USERNAME, new Object[]{username}, userRowMapper);
            if(!password.equals(user.getPassword()))
                throw new AuthException("Invalid username or password");
            return user;
        } catch(EmptyResultDataAccessException e) {
            throw new AuthException("Invalid username or password");
        }

    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }

    @Override
    public Integer countByUsername(String username) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_USERNAME, new Object[]{username}, Integer.class);
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("USER_ID"),
                rs.getString("USERNAME"),
                rs.getString("PASSWORD"),
                rs.getTimestamp("CREATED_AT"));
    });
}
