package com.wasp.online_store.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/shop";
    private static final String DB_USER = "wasp";
    private static final String DB_PSWD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PSWD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
