package com.wasp.onlinestore.dao.jdbc;

import com.wasp.onlinestore.dao.UserDao;
import com.wasp.onlinestore.dao.jdbc.mapper.UserRowMapper;
import com.wasp.onlinestore.entity.User;
import com.wasp.onlinestore.exception.DataAccessException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String SELECT_BY_NAME_AND_PASSWORD = "SELECT id, name, role FROM users WHERE name=? AND password=?;";
    private static final String SELECT_SALT_BY_NAME = "SELECT salt FROM users WHERE name=?;";
    private static final String INSERT = "INSERT INTO users (name, password, salt, role) VALUES(?, ?, ?, ?);";
    private final DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> getUserByNameAndPassword(String name, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_AND_PASSWORD)) {
            statement.setString(1, name);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(USER_ROW_MAPPER.mapRow(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(
                String.format("Could not find user with credentials: username=%s, password=%s", name, password), e);
        }
    }

    @Override
    public void saveUser(User user, String salt) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, salt);
            statement.setObject(4, user.getRole().name().toUpperCase(), Types.OTHER);
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Could not save user by name: " + user.getName(), e);
        }
    }

    @Override
    public Optional<String> getSalt(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_SALT_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getString("salt"));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Could not find user by name: " + name, e);
        }
    }
}
