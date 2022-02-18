package com.wasp.onlinestore.dao.jdbc.mapper;

import com.wasp.onlinestore.entity.Product;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");

        return Product.builder()
            .id(id)
            .name(name)
            .price(price)
            .build();
    }
}
