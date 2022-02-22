package com.wasp.onlinestore.dao;

import com.wasp.onlinestore.entity.Product;
import java.util.Optional;

public interface ProductDao {
    Iterable<Product> findAll();

    Optional<Product> findById(int id);

    boolean save(Product product);

    boolean delete(int id);

    int update(Product product);
}
