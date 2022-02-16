package com.wasp.online_store;

import com.wasp.online_store.dao.ProductDao;
import com.wasp.online_store.model.Product;
import com.wasp.online_store.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTests {
    private Product product;
    private ProductService productService;
    private ProductDao mockedDao;

    @BeforeEach
    void init() {
        product = new Product(1, "chair", 10, new Date());
        mockedDao = mock(ProductDao.class);
        productService = new ProductService(mockedDao);
    }

    @Test
    void productServiceGetAll() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(mockedDao.findAll()).thenReturn(new ArrayList<>(products));

        List<Product> actualProducts = productService.getAll();
        for (Product expectedProduct : products) {
            assertTrue(actualProducts.remove(expectedProduct));
        }

        assertEquals(0, actualProducts.size());
    }

    @Test
    void productServiceInsert() {
        ProductDao mockedDao = mock(ProductDao.class);
        when(mockedDao.insert(product)).thenReturn(true);

        ProductService productService = new ProductService(mockedDao);

        assertTrue(productService.insert(product));
    }
}
