package com.wasp.onlinestore.service;

import com.wasp.onlinestore.dao.ProductDao;
import com.wasp.onlinestore.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;

    public Iterable<Product> getAll() {
        log.info("start: productService.getAll()");
        Iterable<Product> products = productDao.findAll();
        log.info("found products: {}", products);
        log.info("end: productService.getAll()");
        return products;
    }

    public Product getById(int id) {
        log.info("start: productService.getById({})", id);
        //TODO exception handler
        Product product = productDao.findById(id);
        log.info("found product: {}", product);
        log.info("end: productService.getById({})", id);
        return product;
    }

    public void save(Product product) {
        log.info("start: productService.save({})", product);
        boolean success = productDao.save(product);
        log.info("save success: {}", success);
        log.info("end: productService.save({})", product);
    }

    public void delete(int id) {
        log.info("start: productService.delete({})", id);
        boolean success = productDao.delete(id);
        log.info("delete success: {}", success);
        log.info("end: productService.delete({})", id);
    }

    public void update(Product product) {
        log.info("start: productService.update({})", product);
        int rows = productDao.update(product);
        log.info("rows modified: {}", rows);
        log.info("end: productService.update({})", product);
    }
}
