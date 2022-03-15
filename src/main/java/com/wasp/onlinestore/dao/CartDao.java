package com.wasp.onlinestore.dao;

import com.wasp.onlinestore.entity.Product;

public interface CartDao {
    Iterable<Product> findAll(String token);
    boolean add(int productId);
    boolean remove(int productId);
}
