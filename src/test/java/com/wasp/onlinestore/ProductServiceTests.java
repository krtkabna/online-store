package com.wasp.onlinestore;

import com.wasp.onlinestore.dao.jdbc.JdbcProductDao;
import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTests {
    private Product chair;
    private ProductService productService;
    private JdbcProductDao mockedDao;

    @BeforeEach
    void init() {
        chair = Product.builder().id(1).name("chair").price(10).build();
        mockedDao = mock(JdbcProductDao.class);
        productService = new ProductService(mockedDao);
    }

    @Test
    void getAll() {
        List<Product> products = new ArrayList<>();
        products.add(chair);
        when(mockedDao.findAll()).thenReturn(new ArrayList<>(products));

        List<Product> actualProducts = (List<Product>) productService.getAll();
        for (Product expectedProduct : products) {
            assertTrue(actualProducts.remove(expectedProduct));
        }

        assertEquals(0, actualProducts.size());
    }

    @Test
    void save() {
        when(mockedDao.save(chair)).thenReturn(true);

        assertTrue(productService.save(chair));
    }

    @Test
    void delete() {
        when(mockedDao.delete(chair.getId())).thenReturn(true);

        assertTrue(productService.delete(chair.getId()));
    }
}
