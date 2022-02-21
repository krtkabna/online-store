package com.wasp.onlinestore.dao.jdbc;

import com.wasp.onlinestore.dao.ProductDao;
import com.wasp.onlinestore.dao.jdbc.mapper.ProductRowMapper;
import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.DataAccessException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private final static ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
    private static final String SELECT_ALL = "SELECT * FROM products ORDER BY id ASC;";
    private static final String SELECT_BY_ID = "SELECT * FROM products WHERE id=?;";
    private static final String INSERT_ALL_FIELDS = "INSERT INTO products (name, price, id) VALUES (?, ?, ?);";
    private static final String INSERT_NAME_PRICE = "INSERT INTO products (name, price) VALUES (?, ?);";
    private static final String DELETE_BY_ID = "DELETE FROM products WHERE id=?;";
    private static final String UPDATE_NAME_AND_PRICE = "UPDATE products SET name=?, price=? WHERE id=?;";
    private DataSource dataSource;

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            List<Product> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRowWithDate(resultSet);
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new DataAccessException("Could not get all products", e);
        }
    }

    @Override
    public Product findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                Product product = null;
                if (resultSet.next()) {
                    product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                }
                return product;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Could not get product by id: " + id, e);
        }
    }

    @Override
    public boolean save(Product product) {
        boolean hasId = product.getId() != 0;
        String query = hasId ? INSERT_ALL_FIELDS : INSERT_NAME_PRICE;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            if (hasId) {
                statement.setInt(3, product.getId());
            }
            return statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("SQL error occurred on INSERT: " + query, e);
        }
    }

    @Override
    public int update(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_NAME_AND_PRICE)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getId());
            int res = statement.executeUpdate();
            return res;
        } catch (
            SQLException e) {
            throw new DataAccessException("SQL error occurred on UPDATE: " + UPDATE_NAME_AND_PRICE, e);
        }

    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, id);
            return statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("SQL error occurred on DELETE: id=" + id, e);
        }
    }
}
