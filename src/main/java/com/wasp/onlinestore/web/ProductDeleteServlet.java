package com.wasp.onlinestore.web;

import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.web.util.PageGenerator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductDeleteServlet extends HttpServlet {
    private final ProductService productService;

    public ProductDeleteServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "");

        resp.getWriter().println(PageGenerator.getPage("delete.html", data));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean deleted = productService.delete(id);
        if (deleted) {
            resp.setStatus(HttpServletResponse.SC_OK);
            RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/");
            dispatcher.forward(req, resp);
        } else {
            resp.getWriter().println("Did not delete product by id: " + id);
        }
    }
}