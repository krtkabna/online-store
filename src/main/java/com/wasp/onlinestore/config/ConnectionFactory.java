package com.wasp.onlinestore.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private String url;
    private String username;
    private String password;

    public ConnectionFactory(Properties properties) {
        this.url = properties.getProperty("db_url");
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Could not establish SQL connection", e);
        }
    }
}
