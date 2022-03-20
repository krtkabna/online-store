package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.SessionService;
import com.wasp.onlinestore.service.security.entity.Role;
import com.wasp.onlinestore.service.security.entity.Session;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class SecurityFilter implements Filter {
    private final SessionService sessionService;

    protected SecurityFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpServletRequest.getCookies();
        try {
            Session session = sessionService.getSession(cookies);

            boolean isAuthorized = (session != null) && isRoleAllowed(session.getUser().getRole());

            if (!isAuthorized) {
                httpServletResponse.sendRedirect("/login");
                return;
            }

            httpServletRequest.setAttribute("session", session);
            chain.doFilter(request, response);
        } catch (UserNotFoundException e) {
            httpServletResponse.sendRedirect("/login");
        }
    }

    public abstract boolean isRoleAllowed(Role role);
}
