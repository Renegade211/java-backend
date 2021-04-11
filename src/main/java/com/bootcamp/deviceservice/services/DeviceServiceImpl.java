package com.bootcamp.deviceservice.services;

import com.bootcamp.deviceservice.domain.Device;
import com.bootcamp.deviceservice.exceptions.BadRequestException;
import com.bootcamp.deviceservice.exceptions.DeviceNotFoundException;
import com.bootcamp.deviceservice.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public List<Device> fetchAllDevices(Integer userId) {
        return deviceRepository.findAll(userId);
    }

    @Override
    public Device fetchDeviceById(Integer userId, Integer deviceId) throws DeviceNotFoundException {
        return deviceRepository.findById(userId, deviceId);
    }

    @Override
    public Device addDevice(Integer userId, String os) throws BadRequestException {
        int deviceId = deviceRepository.create(userId, os);
        return deviceRepository.findById(userId, deviceId);
    }

    @Override
    public void updateDevice(Integer userId, Integer deviceId, Device device) throws BadRequestException {
        deviceRepository.update(userId, deviceId, device);
    }

    @Override
    public void removeDevice(Integer userId, Integer deviceId) throws DeviceNotFoundException {
        if(userId == 0) {
            deviceRepository.removeById(userId, deviceId);
        } else {
            this.fetchDeviceById(userId, deviceId);
            deviceRepository.removeById(userId, deviceId);
        }
    }
}
