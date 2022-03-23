package com.wasp.onlinestore.main;

import com.wasp.onlinestore.config.PropertyReader;
import com.wasp.onlinestore.dao.ProductDao;
import com.wasp.onlinestore.dao.jdbc.JdbcProductDao;
import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.web.util.PageGenerator;
import org.postgresql.ds.PGSimpleDataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceLocator {
    private static final Map<Class<?>, Object> CONTEXT = new HashMap<>();

    static {
        //config config
        PropertyReader propertyReader = new PropertyReader("/properties/application.properties");
        Properties properties = propertyReader.getProperties();

        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(properties.getProperty("db_url"));
        pgSimpleDataSource.setUser(properties.getProperty("username"));
        pgSimpleDataSource.setPassword(properties.getProperty("password"));

        //config dao
        ProductDao productDao = new JdbcProductDao(pgSimpleDataSource);

        //config service
        PageGenerator pageGenerator = new PageGenerator();
        ProductService productService = new ProductService(productDao);

        CONTEXT.put(PageGenerator.class, pageGenerator);
        CONTEXT.put(ProductService.class, productService);
    }

    public static <T> T get(Class<T> clazz) {
        return clazz.cast(CONTEXT.get(clazz));
    }
}
