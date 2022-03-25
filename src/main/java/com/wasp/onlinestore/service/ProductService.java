package com.wasp.onlinestore.service;

import com.wasp.onlinestore.dao.ProductDao;
import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Iterable<Product> getAll() {
        return productDao.findAll();
    }

    public Product getById(int id) {
        return productDao.findById(id).orElseThrow(() -> new ProductNotFoundException("Could not find product by id: " + id));
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
