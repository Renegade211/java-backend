package com.bootcamp.deviceservice.services;

import com.bootcamp.deviceservice.domain.Device;
import com.bootcamp.deviceservice.exceptions.BadRequestException;
import com.bootcamp.deviceservice.exceptions.DeviceNotFoundException;

import java.util.*;

public interface DeviceService {

    List<Device> fetchAllDevices(Integer userId);

    Device fetchDeviceById(Integer userId, Integer deviceId) throws DeviceNotFoundException;

    Device addDevice(Integer userId, String os) throws BadRequestException;

    void updateDevice(Integer userId, Integer deviceId, Device device) throws BadRequestException;

    void removeDevice(Integer userId, Integer deviceId) throws DeviceNotFoundException;
}
