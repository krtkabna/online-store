package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.service.security.entity.Role;
import com.wasp.onlinestore.service.security.entity.Session;
import jakarta.servlet.Filter;
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
import java.util.stream.Collectors;

public abstract class SecurityFilter implements Filter {
    private final SecurityService securityService;

    protected SecurityFilter(SecurityService securityService) {
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
        try {
            Session session = getSession(cookies);

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

    @Override
    public void destroy() {

    }

    public abstract boolean isRoleAllowed(Role role);

    private Session getSession(Cookie[] cookies) {
        return securityService.getSessionByToken(getUserToken(cookies));
    }

    private String getUserToken(Cookie[] cookies) {
        return Arrays.stream(cookies)
            .filter(cookie -> "user-token".equals(cookie.getName()))
            .map(Cookie::getValue)
            .collect(Collectors.joining());
    }
}
