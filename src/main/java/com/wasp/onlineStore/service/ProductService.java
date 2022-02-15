package com.wasp.onlineStore.service;

import com.wasp.onlineStore.dao.ProductDao;
import com.wasp.onlineStore.model.Product;
import java.util.List;

public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAll() {
        return productDao.findAll();
    }

    public boolean insert(int id, String name, double price) {
        return productDao.insert(id, name, price);
    }
}
