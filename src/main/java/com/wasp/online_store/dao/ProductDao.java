package com.wasp.online_store.dao;

import com.wasp.online_store.controller.ConnectionUtil;
import com.wasp.online_store.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private static final String SELECT_ALL = "SELECT * FROM products ORDER BY id ASC;";
    private static final String INSERT_ALL_FIELDS = "INSERT INTO products (name, price, id) VALUES (?, ?, ?);";
    private static final String INSERT_NAME_PRICE = "INSERT INTO products (name, price) VALUES (?, ?);";
    private static final String DELETE_BY_ID = "DELETE FROM products WHERE id=?;";
    private static final String UPDATE_NAME = "UPDATE products SET name=? WHERE id=?;";
    private static final String UPDATE_NAME_AND_PRICE = "UPDATE products SET name=?, price=? WHERE id=?;";
    private static final String UPDATE_PRICE = "UPDATE products SET price=? WHERE id=?;";

    public List<Product> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {
            List<Product> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setCreationDate(resultSet.getDate("creation_date"));
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Could not execute SELECT: ", e);
        }
    }

    public boolean insert(Product product) {
        boolean hasId = product.getId() != 0;
        String query = hasId ? INSERT_ALL_FIELDS : INSERT_NAME_PRICE;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            if (hasId) {
                statement.setInt(3, product.getId());
            }
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("SQL error occurred on INSERT: ", e);
        }
    }

    public int deleteById(int id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("SQL error occurred on DELETE: ", e);
        }
    }

    public int update(Product product) {
        String query = getUpdateQuery(product);
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            switch (query) {
                case UPDATE_NAME:
                    statement.setString(1, product.getName());
                    break;
                case UPDATE_NAME_AND_PRICE:
                    statement.setString(1, product.getName());
                    statement.setDouble(2, product.getPrice());
                    break;
                case UPDATE_PRICE:
                    statement.setDouble(1, product.getPrice());
                    break;
                default:
                    throw new IllegalArgumentException("Illegal argument for UPDATE: " + product);
            }

            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("SQL error occurred on UPDATE: ", e);
        }
    }

    private String getUpdateQuery(Product product) {
        return (product.getName() != null)
            ? (product.getPrice() != 0.0)
                ? UPDATE_NAME_AND_PRICE
                : UPDATE_NAME
            : UPDATE_PRICE;
    }
}
