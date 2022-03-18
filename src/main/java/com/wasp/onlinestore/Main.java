package com.wasp.onlinestore;

import com.wasp.onlinestore.config.PropertyReader;
import com.wasp.onlinestore.dao.ProductDao;
import com.wasp.onlinestore.dao.UserDao;
import com.wasp.onlinestore.dao.jdbc.JdbcProductDao;
import com.wasp.onlinestore.dao.jdbc.JdbcUserDao;
import com.wasp.onlinestore.service.CartService;
import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.service.UserService;
import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.web.CartServlet;
import com.wasp.onlinestore.web.LoginServlet;
import com.wasp.onlinestore.web.ProductAddServlet;
import com.wasp.onlinestore.web.ProductDeleteServlet;
import com.wasp.onlinestore.web.ProductUpdateServlet;
import com.wasp.onlinestore.web.ProductsServlet;
import com.wasp.onlinestore.web.RegisterServlet;
import com.wasp.onlinestore.web.security.AdminFilter;
import com.wasp.onlinestore.web.security.UserFilter;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.postgresql.ds.PGSimpleDataSource;
import java.util.EnumSet;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        //config config
        PropertyReader propertyReader = new PropertyReader("/properties/application.properties");
        Properties properties = propertyReader.getProperties();

        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(properties.getProperty("db_url"));
        pgSimpleDataSource.setUser(properties.getProperty("username"));
        pgSimpleDataSource.setPassword(properties.getProperty("password"));

        //config dao
        ProductDao productDao = new JdbcProductDao(pgSimpleDataSource);
        UserDao userDao = new JdbcUserDao(pgSimpleDataSource);

        //config service
        UserService userService = new UserService(userDao);
        ProductService productService = new ProductService(productDao);
        CartService cartService = new CartService(productService);
        SecurityService securityService = new SecurityService(userService);

        //config servlet
        RegisterServlet registerServlet = new RegisterServlet(securityService);
        LoginServlet loginServlet = new LoginServlet(securityService);
        CartServlet cartServlet = new CartServlet(cartService);
        ProductsServlet productsServlet = new ProductsServlet(productService);
        ProductAddServlet productAddServlet = new ProductAddServlet(productService);
        ProductUpdateServlet productUpdateServlet = new ProductUpdateServlet(productService);
        ProductDeleteServlet productDeleteServlet = new ProductDeleteServlet(productService);

        //servlet mapping
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(registerServlet), "/register");
        contextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        contextHandler.addServlet(new ServletHolder(cartServlet), "/cart");
        contextHandler.addServlet(new ServletHolder(productsServlet), "/");
        contextHandler.addServlet(new ServletHolder(productAddServlet), "/product/add");
        contextHandler.addServlet(new ServletHolder(productUpdateServlet), "/product/update/*");
        contextHandler.addServlet(new ServletHolder(productDeleteServlet), "/product/delete");

        //config filter
        AdminFilter adminFilter = new AdminFilter(securityService);
        UserFilter userFilter = new UserFilter(securityService);

        //filter mapping
        contextHandler.addFilter(new FilterHolder(adminFilter), "/product/*", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter(new FilterHolder(userFilter), "/cart", EnumSet.of(DispatcherType.REQUEST));

        //start server
        Server server = new Server(8080);
        server.setHandler(contextHandler);
        server.start();
    }
}
