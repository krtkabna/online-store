package com.wasp.onlineStore.servlet;

import com.wasp.onlineStore.model.Product;
import com.wasp.onlineStore.service.PageGenerator;
import com.wasp.onlineStore.service.ProductService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class ProductServlet extends HttpServlet {
    private final ProductService productService;

    public ProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.getAll();

        resp.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", products);

        String page = PageGenerator.getPage("products.html", pageVariables);
        resp.getWriter().println(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", id);
        pageVariables.put("name", name);
        pageVariables.put("price", price);

        boolean success = productService.insert(parseInt(id), name, parseDouble(price));

        resp.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        resp.getWriter().println(PageGenerator.getPage("product_post.html", pageVariables));
    }
}
