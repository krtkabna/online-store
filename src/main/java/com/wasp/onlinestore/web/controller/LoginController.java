package com.wasp.onlinestore.web.controller;

import com.wasp.onlinestore.entity.CartItem;
import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.web.util.SessionFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class LoginController {
    private static final String REDIRECT_PRODUCTS = "redirect:/";
    private static final String LOGIN = "login";
    private final SecurityService securityService;

    @Autowired
    public LoginController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return LOGIN;
    }

    @PostMapping("/login")
    public String login(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter("password");
        String token = securityService.login(login, password);
        Cookie cookie = new Cookie("user-token", token);
        cookie.setMaxAge(securityService.getCookieTtlSeconds());
        resp.addCookie(cookie);
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/cart")
    public String cart(HttpServletRequest req, ModelMap model) {
        try {
            List<CartItem> cart = SessionFetcher.getSession(req).getCart();
            model.addAttribute("cart", cart);
            return "cart";
        } catch (UserNotFoundException e) {
            return LOGIN;
        }
    }
}
