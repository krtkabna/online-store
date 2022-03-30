package com.wasp.onlinestore.dao.jdbc;

import com.wasp.onlinestore.dao.ProductDao;
import com.wasp.onlinestore.dao.jdbc.mapper.ProductRowMapper;
import com.wasp.onlinestore.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcProductDao implements ProductDao {
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
    private static final String SELECT_ALL = "SELECT id, name, price, creation_date FROM products ORDER BY id ASC;";
    private static final String SELECT_BY_ID = "SELECT id, name, price FROM products WHERE id=:id;";
    private static final String INSERT_NAME_PRICE = "INSERT INTO products (name, price) VALUES (:name, :price);";
    private static final String DELETE_BY_ID = "DELETE FROM products WHERE id=:id;";
    private static final String UPDATE_NAME_AND_PRICE = "UPDATE products SET name=:name, price=:price WHERE id=:id;";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> findAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL, PRODUCT_ROW_MAPPER);
    }

    @Override
    public Product findById(int id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, PRODUCT_ROW_MAPPER);
    }

    @Override
    public boolean save(Product product) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue("name", product.getName())
            .addValue("price", product.getPrice());
        return namedParameterJdbcTemplate.update(INSERT_NAME_PRICE, namedParameters) == 1;
    }

    @Override
    public int update(Product product) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue("id", product.getId())
            .addValue("name", product.getName())
            .addValue("price", product.getPrice());
        return namedParameterJdbcTemplate.update(UPDATE_NAME_AND_PRICE, namedParameters);
    }

    @Override
    public boolean delete(int id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.update(DELETE_BY_ID, namedParameters) == 1;
    }
}
