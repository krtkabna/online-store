package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.service.security.entity.Role;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.SessionFetcher;
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
    private final SecurityService securityService;

    protected SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpServletRequest.getCookies();
        try {
            Session session = securityService.getSessionByToken(SessionFetcher.getUserToken(cookies));

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
