package com.wasp.onlinestore;

import com.wasp.onlinestore.dao.jdbc.JdbcProductDao;
import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTests {
    private final JdbcProductDao mockedDao = mock(JdbcProductDao.class);
    private final ProductService productService = new ProductService(mockedDao);
    private Product chair;

    @BeforeEach
    void init() {
        Date date = new Date();
        chair = Product.builder().id(1).name("chair").price(10).creationDate(date).build();
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
    void getById() {
        when(mockedDao.findById(1)).thenReturn(Optional.of(chair));

        Product product = productService.getById(1);
        assertEquals("chair", product.getName());
        assertEquals(10, product.getPrice());
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
