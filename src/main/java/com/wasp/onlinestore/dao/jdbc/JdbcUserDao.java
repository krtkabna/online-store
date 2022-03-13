package com.wasp.onlinestore.dao.jdbc;

import com.wasp.onlinestore.dao.UserDao;
import com.wasp.onlinestore.exception.DataAccessException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserDao implements UserDao {
    private static final String SELECT_BY_USERNAME = "SELECT id FROM users WHERE name=? AND password=?;";
    private static final String INSERT = "INSERT INTO users (name, password) VALUES(?, ?);";
    private final DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean userExists(String name, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USERNAME)) {
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
    public boolean saveUser(String name, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, name);
            statement.setString(2, password);
            return statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Could not save user by name: " + name, e);
        }
    }
}
