package com.wasp.onlinestore.dao.jdbc.mapper;

import com.wasp.onlinestore.entity.Product;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapRow(resultSet);
    }

    public Product mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");

        return Product.builder()
            .id(id)
            .name(name)
            .price(price)
            .creationDate(getCreationDateIfPresent(resultSet))
            .build();
    }

    private LocalDateTime getCreationDateIfPresent(ResultSet resultSet) {
        LocalDateTime creationDate;
        try {
            creationDate = resultSet.getTimestamp("creation_date").toLocalDateTime();
        } catch (SQLException e) {
            return null;
        }
        return creationDate;
    }
}
