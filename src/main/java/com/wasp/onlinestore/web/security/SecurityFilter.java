package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.service.security.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class SecurityFilter implements jakarta.servlet.Filter {
    private SecurityService securityService;

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpServletRequest.getCookies();

        boolean isAuthorized = (cookies != null) && isTokenValid(cookies);

        if (!isAuthorized) {
            httpServletResponse.sendRedirect("/login");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isTokenValid(Cookie[] cookies) {
        return Arrays.stream(cookies)
            .filter(cookie -> "user-token".equals(cookie.getName()))
            .anyMatch(cookie -> securityService.isTokenValid(cookie.getValue()));
    }

    @Override
    public void destroy() {

    }
}
