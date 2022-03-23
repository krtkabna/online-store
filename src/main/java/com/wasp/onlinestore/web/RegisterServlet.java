package com.wasp.onlinestore.web;

import com.wasp.onlinestore.exception.DataAccessException;
import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.web.util.PageGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private final SecurityService securityService;
    private final PageGenerator pageGenerator;
    private final int cookieTtlSeconds;

    public RegisterServlet(SecurityService securityService, int cookieTtlSeconds) {
        this.securityService = securityService;
        this.pageGenerator = new PageGenerator();
        this.cookieTtlSeconds = cookieTtlSeconds;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        pageGenerator.writePage("signup.html", resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            String token = securityService.register(login, password);
            Cookie cookie = new Cookie("user-token", token);
            cookie.setMaxAge(cookieTtlSeconds);
            resp.addCookie(cookie);
            resp.sendRedirect("/");
        } catch (DataAccessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
