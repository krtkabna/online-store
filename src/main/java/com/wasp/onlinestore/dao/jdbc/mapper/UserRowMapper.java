package com.wasp.onlinestore.dao.jdbc.mapper;

import com.wasp.onlinestore.entity.User;
import com.wasp.onlinestore.service.security.entity.Role;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        String salt = resultSet.getString("salt");
        Role role = Role.valueOf(resultSet.getString("role"));

        return User.builder()
            .name(name)
            .password(password)
            .salt(salt)
            .role(role)
            .build();
    }
}
