package com.wasp.online_store.service;

import com.wasp.online_store.dao.ProductDao;
import com.wasp.online_store.model.Product;
import java.util.List;

public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAll() {
        return productDao.findAll();
    }

    public boolean insert(Product product) {
        return productDao.insert(product);
    }

    public int deleteById(int id) {
        return productDao.deleteById(id);
    }

    public int update(Product product) {
        return productDao.update(product);
    }

    public Product createProduct(String id, String name, String price) {
        Product product = new Product();
        if (id != null) product.setId(Integer.parseInt(id));
        if (name != null) product.setName(name);
        if (price != null) product.setPrice(Double.parseDouble(price));
        return product;
    }
}
