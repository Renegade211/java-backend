package com.bootcamp.deviceservice.repositories;

import com.bootcamp.deviceservice.domain.Device;
import com.bootcamp.deviceservice.exceptions.BadRequestException;
import com.bootcamp.deviceservice.exceptions.DeviceNotFoundException;

import java.util.List;

public interface DeviceRepository {

    List<Device> findAll(Integer userId) throws DeviceNotFoundException;

    Device findById(Integer userId, Integer deviceId) throws DeviceNotFoundException;

    Integer create(Integer userId, String os) throws BadRequestException;

    void update(Integer userId, Integer deviceId, Device device) throws BadRequestException;

    void removeById(Integer userId, Integer deviceId);
}
