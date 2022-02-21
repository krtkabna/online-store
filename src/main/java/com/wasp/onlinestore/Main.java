package com.wasp.onlinestore;

import com.wasp.onlinestore.config.ConnectionFactory;
import com.wasp.onlinestore.config.PropertyReader;
import com.wasp.onlinestore.dao.ProductDao;
import com.wasp.onlinestore.dao.jdbc.JdbcProductDao;
import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.web.ProductAddServlet;
import com.wasp.onlinestore.web.ProductDeleteServlet;
import com.wasp.onlinestore.web.ProductUpdateServlet;
import com.wasp.onlinestore.web.ProductsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        //config config
        PropertyReader propertyReader = new PropertyReader("/properties/application.properties");
        Properties properties = propertyReader.getProperties();
        ConnectionFactory connectionFactory = new ConnectionFactory(properties);

        //config dao
        ProductDao productDao = new JdbcProductDao(connectionFactory);

        //config service
        ProductService productService = new ProductService(productDao);

        //config servlet(s)
        ProductsServlet productsServlet = new ProductsServlet(productService);
        ProductAddServlet productAddServlet = new ProductAddServlet(productService);
        ProductUpdateServlet productUpdateServlet = new ProductUpdateServlet(productService);
        ProductDeleteServlet productDeleteServlet = new ProductDeleteServlet(productService);

        //servlet mapping
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(productsServlet), "/");
        contextHandler.addServlet(new ServletHolder(productAddServlet), "/add");
        contextHandler.addServlet(new ServletHolder(productUpdateServlet), "/update/*");
        contextHandler.addServlet(new ServletHolder(productDeleteServlet), "/delete");

        //start server
        Server server = new Server(8080);
        server.setHandler(contextHandler);
        server.start();
    }
}
