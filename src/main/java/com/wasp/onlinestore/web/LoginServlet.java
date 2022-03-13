package com.wasp.onlinestore.web;

import com.wasp.onlinestore.exception.DataAccessException;
import com.wasp.onlinestore.service.SecurityService;
import com.wasp.onlinestore.web.util.PageGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final SecurityService securityService;

    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator.writePage("login.html", resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            if (!securityService.userExists(login, password)) {
                securityService.saveUser(login, password);
            }
            Cookie cookie = new Cookie("user-token", securityService.generateToken());
            resp.addCookie(cookie);
            resp.sendRedirect("/");
        } catch (DataAccessException e) {
            PageGenerator.writePage("login_failed.html", resp.getWriter());
        }
    }
}
