package com.wasp.onlinestore.service;

import com.wasp.onlinestore.dao.ProductDao;
import com.wasp.onlinestore.entity.Product;

public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Iterable<Product> getAll() {
        return productDao.findAll();
    }

    public boolean save(Product product) {
        return productDao.save(product);
    }

    public boolean delete(int id) {
        return productDao.delete(id);
    }

    public int update(Product product) {
        return productDao.update(product);
    }
}
