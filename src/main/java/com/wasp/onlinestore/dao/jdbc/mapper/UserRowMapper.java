package com.wasp.onlinestore.dao.jdbc.mapper;

import com.wasp.onlinestore.entity.User;
import com.wasp.onlinestore.service.security.entity.Role;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Role role = Role.valueOf(resultSet.getString("role"));

        return User.builder()
            .id(id)
            .name(name)
            .role(role)
            .build();
    }
}
