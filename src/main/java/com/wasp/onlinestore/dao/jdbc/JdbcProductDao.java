package com.wasp.onlinestore.dao.jdbc;

import com.wasp.onlinestore.dao.ProductDao;
import com.wasp.onlinestore.dao.jdbc.mapper.ProductRowMapper;
import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProductDao implements ProductDao {
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
    private static final String SELECT_ALL = "SELECT id, name, price, creation_date FROM products ORDER BY id ASC;";
    private static final String SELECT_BY_ID = "SELECT id, name, price FROM products WHERE id=?;";
    private static final String INSERT_NAME_PRICE = "INSERT INTO products (name, price) VALUES (?, ?);";
    private static final String DELETE_BY_ID = "DELETE FROM products WHERE id=?;";
    private static final String UPDATE_NAME_AND_PRICE = "UPDATE products SET name=?, price=? WHERE id=?;";
    private final DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
            List<Product> result = new ArrayList<>();
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
    public Optional<Product> findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(PRODUCT_ROW_MAPPER.mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Could not get product by id: " + id, e);
        }
    }

    @Override
    public boolean save(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NAME_PRICE)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataAccessException("SQL error occurred on INSERT: " + INSERT_NAME_PRICE, e);
        }
    }

    @Override
    public int update(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_NAME_AND_PRICE)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getId());
            return statement.executeUpdate();
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
