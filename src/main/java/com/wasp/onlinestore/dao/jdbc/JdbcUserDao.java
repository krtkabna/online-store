package com.wasp.onlinestore.dao.jdbc;

import com.wasp.onlinestore.dao.UserDao;
import com.wasp.onlinestore.dao.jdbc.mapper.UserRowMapper;
import com.wasp.onlinestore.entity.User;
import com.wasp.onlinestore.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String SELECT_USER_BY_NAME = "SELECT name, password, salt, role FROM users WHERE name=?;";
    private static final String INSERT = "INSERT INTO users (name, password, salt, role) VALUES(?, ?, ?, ?);";
    private final DataSource dataSource;

    @Autowired
    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getSalt());
            statement.setObject(4, user.getRole().name().toUpperCase(), Types.OTHER);
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Could not save user by name: " + user.getName(), e);
        }
    }

    @Override
    public Optional<User> getUserByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(USER_ROW_MAPPER.mapRow(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Could not find user by name: " + name, e);
        }
    }
}
