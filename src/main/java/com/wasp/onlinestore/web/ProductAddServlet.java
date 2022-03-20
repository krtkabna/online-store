package com.wasp.onlinestore.web;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.ProductParseException;
import com.wasp.onlinestore.main.ServiceLocator;
import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.web.util.PageGenerator;
import com.wasp.onlinestore.web.util.ProductMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductAddServlet extends HttpServlet {
    private final ProductService productService = ServiceLocator.get(ProductService.class);
    private final PageGenerator pageGenerator = ServiceLocator.get(PageGenerator.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        pageGenerator.writePage("add.html", resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Product product = ProductMapper.getProductFromFields(req);
            boolean success = false;
            if (product != null) {
                success = productService.save(product);
            }

            resp.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
            resp.sendRedirect("/");
        } catch (ProductParseException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
