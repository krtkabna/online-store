package com.wasp.onlinestore.web;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.web.util.PageGenerator;
import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.web.util.ProductMapper;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.wasp.onlinestore.web.util.ProductPageVariablesMapper.createPageVariablesMap;
import static com.wasp.onlinestore.web.util.ProductPageVariablesMapper.getPageVariables;

public class ProductUpdateServlet extends HttpServlet {
    private final ProductService productService;

    public ProductUpdateServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = createPageVariablesMap(getPageVariables(req));

        resp.getWriter().println(PageGenerator.getPage("update.html", data));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = ProductMapper.map(getPageVariables(req));
        int rows = productService.update(product);
        if (rows > 0) {
            resp.setStatus(HttpServletResponse.SC_OK);
            RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/");
            dispatcher.forward(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
