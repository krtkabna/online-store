package com.wasp.onlinestore.service;

import com.wasp.onlinestore.dao.ProductDao;
import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j //TODO logs
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;

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
        log.info("Entered productService.update()");
        return productDao.update(product);
    }
}
