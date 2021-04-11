package com.bootcamp.deviceservice.services;

import com.bootcamp.deviceservice.domain.User;
import com.bootcamp.deviceservice.exceptions.AuthException;
import com.bootcamp.deviceservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User registerUser(String username, String password) throws AuthException {
        Integer count = userRepository.countByUsername(username);
        if (count > 0)
            throw new AuthException("Username is already taken");
        Integer userId = userRepository.create(username, password);
        return userRepository.findById(userId);
    }

    @Override
    public User validateUser(String username, String password) throws AuthException {
        return userRepository.findByUsernameAndPassword(username, password);
    }

}
