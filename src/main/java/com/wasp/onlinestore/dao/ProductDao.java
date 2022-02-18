package com.wasp.onlinestore.dao;

import com.wasp.onlinestore.entity.Product;

public interface ProductDao {
    Iterable<Product> findAll();

    Product findById(int id);

    boolean save(Product product);

    boolean delete(int id);

    int update(Product product);
}
