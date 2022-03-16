package com.wasp.onlinestore.dao.jdbc.mapper;

import com.wasp.onlinestore.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        boolean isAdmin = resultSet.getBoolean("admin");

        return User.builder()
            .id(id)
            .name(name)
            .isAdmin(isAdmin)
            .build();
    }
}
