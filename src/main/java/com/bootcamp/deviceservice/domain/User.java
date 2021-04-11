package com.bootcamp.deviceservice.domain;

import org.apache.tomcat.jni.Local;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class User {
    private Integer userId;
    private String username;
    private String password;
    private Timestamp createdAt;

    public User(Integer userId, String username, String password, Timestamp createdAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getPassword() {
        return password;
    }
}
