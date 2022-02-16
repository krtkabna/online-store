package com.wasp.online_store;

import com.wasp.online_store.dao.ProductDao;
import com.wasp.online_store.service.ProductService;
import com.wasp.online_store.servlet.ProductAddServlet;
import com.wasp.online_store.servlet.ProductDeleteServlet;
import com.wasp.online_store.servlet.ProductsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        //config dao
        ProductDao productDao = new ProductDao();

        //config service
        ProductService productService = new ProductService(productDao);

        //config servlet(s)
        ProductsServlet productsServlet = new ProductsServlet(productService);
        ProductAddServlet productAddServlet = new ProductAddServlet(productService);
        ProductDeleteServlet productDeleteServlet = new ProductDeleteServlet(productService);

        //servlet mapping
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(productsServlet), "/products");
        servletContextHandler.addServlet(new ServletHolder(productAddServlet), "/products/add");
        servletContextHandler.addServlet(new ServletHolder(productDeleteServlet), "/products/delete");

        //start server
        Server server = new Server(8080);
        server.setHandler(servletContextHandler);
        server.start();
    }
}
