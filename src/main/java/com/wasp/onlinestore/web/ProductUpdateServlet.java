package com.wasp.onlinestore.web;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.ProductNotFoundException;
import com.wasp.onlinestore.exception.ProductParseException;
import com.wasp.onlinestore.main.ServiceLocator;
import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.web.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.wasp.onlinestore.web.util.ProductMapper.getProductFromRequestBody;

public class ProductUpdateServlet extends HttpServlet {
    private final ProductService productService = ServiceLocator.get(ProductService.class);
    private final PageGenerator pageGenerator = ServiceLocator.get(PageGenerator.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            Product product = productService.getById(id);
            Map<String, Object> data = new HashMap<>();
            data.put("product", product);
            pageGenerator.writePage("update.html", resp.getWriter(), data);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (ProductNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Product product = getProductFromRequestBody(req);
            int rows = productService.update(product);
            if (rows > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (ProductParseException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
