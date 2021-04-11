package com.bootcamp.deviceservice.repositories;


import com.bootcamp.deviceservice.domain.Device;
import com.bootcamp.deviceservice.exceptions.BadRequestException;
import com.bootcamp.deviceservice.exceptions.DeviceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private static final String SQL_FIND_BY_ID = "SELECT DEVICE_ID, USER_ID, OS, CREATED_AT, UPDATED_AT FROM DEVICES WHERE DEVICE_ID = ? AND USER_ID = ?";
    private static final String SQL_CREATE = "INSERT INTO DEVICES(USER_ID, OS) VALUES(?, ?)";
    private static final String SQL_GET_ALL = "SELECT * FROM DEVICES";
    private static final String SQL_FIND_ALL_BY_ID = "SELECT DEVICE_ID, USER_ID, OS, CREATED_AT, UPDATED_AT FROM DEVICES WHERE USER_ID = ?";
    private static final String SQL_UPDATE = "UPDATE DEVICES SET OS = ?, UPDATED_AT = ? WHERE USER_ID = ? AND DEVICE_ID = ?";
    private static final String SQL_DELETE = "DELETE FROM DEVICES WHERE USER_ID = ? AND DEVICE_ID = ?";
    private static final String SQL_DELETE_BY_ADMIN = "DELETE FROM DEVICES WHERE DEVICE_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Device> findAll(Integer userId) throws DeviceNotFoundException {
        if(userId == 0) {
            return jdbcTemplate.query(SQL_GET_ALL, new Object[]{}, deviceRowMapper);
        } else {
            return jdbcTemplate.query(SQL_FIND_ALL_BY_ID, new Object[]{userId}, deviceRowMapper);
        }
    }

    @Override
    public Device findById(Integer userId, Integer deviceId) throws DeviceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{deviceId, userId}, deviceRowMapper);
        } catch(Exception e) {
            throw new DeviceNotFoundException("Not found");
        }
    }

    @Override
    public Integer create(Integer userId, String os) throws BadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, os);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("DEVICE_ID");
        } catch(Exception e) {
            throw new BadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer userId, Integer deviceId, Device device) throws BadRequestException {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{device.getOs(), time, userId, deviceId});
        } catch(Exception e) {
            throw new BadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer deviceId) {
        if(userId == 0) {
            jdbcTemplate.update(SQL_DELETE_BY_ADMIN, new Object[]{deviceId});
        } else {
            jdbcTemplate.update(SQL_DELETE, new Object[]{userId, deviceId});
        }
    }

    private RowMapper<Device> deviceRowMapper = ((rs, rowNum) -> {
        return new Device(rs.getInt("DEVICE_ID"),
                rs.getInt("USER_ID"),
                rs.getString("OS"),
                rs.getTimestamp("CREATED_AT"),
                rs.getTimestamp("UPDATED_AT"));
    });
}
