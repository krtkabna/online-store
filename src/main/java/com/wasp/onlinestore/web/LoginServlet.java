package com.wasp.onlinestore.web;

import com.wasp.onlinestore.exception.DataAccessException;
import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.PageGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final SecurityService securityService;
    private final PageGenerator pageGenerator;

    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
        this.pageGenerator = new PageGenerator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        pageGenerator.writePage("login.html", resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            String token = securityService.login(login, password);
            Cookie cookie = new Cookie("user-token", token);
            resp.addCookie(cookie);
            resp.sendRedirect("/");
        } catch (DataAccessException e) {
            pageGenerator.writePage("login_failed.html", resp.getWriter());
        } catch (UserNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
