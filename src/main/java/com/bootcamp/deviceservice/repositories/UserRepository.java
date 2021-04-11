package com.bootcamp.deviceservice.repositories;

import com.bootcamp.deviceservice.domain.User;
import com.bootcamp.deviceservice.exceptions.AuthException;

public interface UserRepository {

    Integer create(String username, String password) throws AuthException;

    User findByUsernameAndPassword (String username, String password) throws AuthException;

    User findById(Integer userId);

    Integer countByUsername(String username);
}
