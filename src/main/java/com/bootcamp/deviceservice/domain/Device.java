package com.bootcamp.deviceservice.domain;

import java.sql.Time;
import java.sql.Timestamp;

public class Device {

    private Integer deviceId;
    private Integer userId;
    private String os;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Device(Integer deviceId, Integer userId, String os, Timestamp createdAt, Timestamp updatedAt) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.os = os;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getOs() {
        return os;
    }
}
