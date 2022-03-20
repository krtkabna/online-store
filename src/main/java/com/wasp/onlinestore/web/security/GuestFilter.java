package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.service.SessionService;
import com.wasp.onlinestore.service.security.entity.Session;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class GuestFilter implements Filter {
    public static final String SESSION = "session";
    private final SessionService sessionService;

    public GuestFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        Cookie[] cookies = httpServletRequest.getCookies();
        Session session = sessionService.getSession(cookies);

        if (session != null) {
            httpServletRequest.setAttribute(SESSION, session);
        }
        chain.doFilter(request, response);
    }
}
