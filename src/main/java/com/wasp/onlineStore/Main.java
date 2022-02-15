package com.wasp.onlineStore;

import com.wasp.onlineStore.dao.ProductDao;
import com.wasp.onlineStore.service.ProductService;
import com.wasp.onlineStore.servlet.ProductServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        //config dao
        ProductDao productDao = new ProductDao();

        //config service
        ProductService productService = new ProductService(productDao);

        //config servlet
        ProductServlet productServlet = new ProductServlet(productService);

        //servlet mapping
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(productServlet), "/products");

        //start server
        Server server = new Server(8080);
        server.setHandler(servletContextHandler);
        server.start();
    }
}
