package com.wasp.onlineStore;

import com.wasp.onlineStore.dao.ProductDao;
import com.wasp.onlineStore.model.Product;
import com.wasp.onlineStore.service.ProductService;
import com.wasp.onlineStore.servlet.ProductServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGmoney;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OnlineStoreTests {
    private Product product;

    @BeforeEach
    void initProduct() {
        product = new Product(1, "chair", new PGmoney(1000), new Date());
    }

    @Test
    void start() {
    }

    @Test
    void productServiceGetAll() {
        List<Product> list = new ArrayList<>();
        list.add(product);
        ProductDao mockedDao = mock(ProductDao.class);
        when(mockedDao.findAll()).thenReturn(list);

        ProductService productService = new ProductService(mockedDao);

        assertEquals(list, productService.getAll());
    }

    @Test
    void productServiceInsert() {
        ProductDao mockedDao = mock(ProductDao.class);
        when(mockedDao.insert(product.getId(), product.getName(), product.getPrice().val)).thenReturn(true);

        ProductService productService = new ProductService(mockedDao);

        assertTrue(productService.insert(product.getId(), product.getName(), product.getPrice().val));
    }
}
