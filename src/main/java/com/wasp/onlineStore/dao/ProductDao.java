package com.wasp.onlineStore.dao;

import com.wasp.onlineStore.controller.ConnectionUtil;
import com.wasp.onlineStore.model.Product;
import org.postgresql.util.PGmoney;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private static final String SELECT_ALL = "SELECT * FROM products;";
    private static final String INSERT = "INSERT INTO products VALUES (INSERT INTO products (id, name, price, creation_date) VALUES (?, ?, ?, ?);";

    public List<Product> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {
            List<Product> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(new PGmoney(resultSet.getString("price")));
                product.setCreationDate(resultSet.getDate("creation_date"));
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Could not execute SELECT: ", e);
        }
    }

    public boolean insert(int id, String name, double price) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setDouble(3, price);
            statement.setDate(4, new Date(System.currentTimeMillis()));
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("SQL error occurred on INSERT: ", e);
        }
    }

}
