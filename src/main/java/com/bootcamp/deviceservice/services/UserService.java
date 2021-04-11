package com.bootcamp.deviceservice.services;

import com.bootcamp.deviceservice.domain.User;
import com.bootcamp.deviceservice.exceptions.AuthException;

public interface UserService {

    User registerUser(String username, String password) throws AuthException;

    User validateUser(String username, String password) throws AuthException;
}
