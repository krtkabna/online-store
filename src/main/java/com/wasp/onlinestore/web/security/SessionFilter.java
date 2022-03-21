package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.SessionFetcher;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SessionFilter implements Filter {
    public static final String SESSION = "session";
    private final SecurityService securityService;

    public SessionFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        Cookie[] cookies = httpServletRequest.getCookies();
        Session session = securityService.getSessionByToken(SessionFetcher.getUserToken(cookies));

        if (session != null) {
            httpServletRequest.setAttribute(SESSION, session);
        }
        chain.doFilter(request, response);
    }
}
