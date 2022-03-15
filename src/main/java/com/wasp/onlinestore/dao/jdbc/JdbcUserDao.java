package com.wasp.onlinestore.dao.jdbc;

import com.wasp.onlinestore.dao.UserDao;
import com.wasp.onlinestore.exception.DataAccessException;
import com.wasp.onlinestore.exception.UserNotFoundException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcUserDao implements UserDao {
    private static final String SELECT_BY_NAME_AND_PASSWORD = "SELECT id FROM users WHERE name=? AND password=?;";
    private static final String SELECT_SALT_BY_NAME = "SELECT salt FROM users WHERE name=?;";
    private static final String SELECT_ROLE_BY_NAME = "SELECT admin FROM users WHERE name=?;";
    private static final String INSERT = "INSERT INTO users (name, password, salt) VALUES(?, ?, ?);";
    private final DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean userExists(String name, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAME_AND_PASSWORD)) {
            statement.setString(1, name);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException(
                String.format("Could not find user with credentials: username=%s, password=%s", name, password), e);
        }
    }

    @Override
    public void saveUser(String name, String password, String salt) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, name);
            statement.setString(2, password);
            statement.setString(3, salt);
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Could not save user by name: " + name, e);
        }
    }

    @Override
    public Optional<String> getSalt(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ROLE_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getString("salt"));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new UserNotFoundException("Could not find user by name: " + name, e);
        }
    }

    @Override
    public boolean isAdmin(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_SALT_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("admin");
                }
                return false;
            }
        } catch (SQLException e) {
            throw new UserNotFoundException("Could not find user by name: " + name, e);
        }
    }
}
