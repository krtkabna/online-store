package com.wasp.onlinestore.web;

import com.wasp.onlinestore.web.util.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private final List<String> tokens;

    public LoginServlet(List<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator.writePage("login.html", resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login != null && password != null) {
            String token = UUID.randomUUID().toString();
            tokens.add(token);
            Cookie cookie = new Cookie("user-token", token);
            resp.addCookie(cookie);
        }
        resp.sendRedirect("/products");
    }
}
