package com.wasp.onlinestore.web.security;

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
import java.util.List;

public class SecurityFilter implements jakarta.servlet.Filter {
    private List<String> tokens;

    public SecurityFilter(List<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpServletRequest.getCookies();

        boolean isAuthorized = cookies != null && Arrays.stream(cookies)
            .filter(cookie -> "user-token".equals(cookie.getName()))
            .anyMatch(cookie -> tokens.contains(cookie.getValue()));

        if (!isAuthorized) {
            httpServletResponse.sendRedirect("/login");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
