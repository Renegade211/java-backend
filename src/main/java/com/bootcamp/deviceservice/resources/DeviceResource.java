package com.bootcamp.deviceservice.resources;

import com.bootcamp.deviceservice.domain.Device;
import com.bootcamp.deviceservice.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/devices")
public class DeviceResource {

    @Autowired
    DeviceService deviceService;

    @GetMapping("/{deviceId}")
    public ResponseEntity<Device> getDeviceById(HttpServletRequest request, @PathVariable("deviceId") Integer deviceId) {
        Integer userId = (Integer) request.getAttribute("userId");
        Device device = deviceService.fetchDeviceById(userId, deviceId);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Device> addDevice(HttpServletRequest request, @RequestBody Map<String, String> deviceMap) {
        Integer userId = (Integer) request.getAttribute("userId");
        String os = (String) deviceMap.get("os");
        Device device = deviceService.addDevice(userId, os);
        return new ResponseEntity<>(device, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Device>> getAllDevices(HttpServletRequest request) {
        int  userId = (Integer) request.getAttribute("userId");
        List<Device> devices = deviceService.fetchAllDevices(userId);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @PutMapping("/update/{deviceId}")
    public ResponseEntity<Map<String, Boolean>> updateDevice(HttpServletRequest request,
                                                             @PathVariable("deviceId") Integer deviceId,
                                                             @RequestBody Device device) {
        Integer userId = (Integer) request.getAttribute("userId");
        deviceService.updateDevice(userId, deviceId, device);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{deviceId}")
    public ResponseEntity<Map<String, Boolean>> deleteDevice(HttpServletRequest request, @PathVariable("deviceId") Integer deviceId) {
        int userId = (Integer) request.getAttribute("userId");
        deviceService.removeDevice(userId, deviceId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
