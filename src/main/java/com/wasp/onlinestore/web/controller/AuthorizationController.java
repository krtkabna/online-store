package com.wasp.onlinestore.web.controller;

import com.wasp.onlinestore.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class AuthorizationController {
    private static final String LOGIN_VIEW = "login";
    private static final String REGISTER_VIEW = "signup";
    private static final String REDIRECT_PRODUCTS = "redirect:/";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String USER_TOKEN = "user-token";
    private final SecurityService securityService;

    @GetMapping("/login")
    public String getLoginPage() {
        return LOGIN_VIEW;
    }

    @PostMapping("/login")
    public String login(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String token = securityService.login(login, password);
        Cookie cookie = new Cookie(USER_TOKEN, token);
        cookie.setMaxAge(securityService.getSessionTtlSeconds());
        resp.addCookie(cookie);
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return REGISTER_VIEW;
    }

    @PostMapping("/register")
    public String register(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String token = securityService.register(login, password);
        Cookie cookie = new Cookie(USER_TOKEN, token);
        cookie.setMaxAge(securityService.getSessionTtlSeconds());
        resp.addCookie(cookie);
        return REDIRECT_PRODUCTS;
    }
}
