package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.service.security.entity.Role;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.SessionFetcher;
import lombok.Setter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Setter
public abstract class SecurityFilter extends AbstractFilter {
    private SecurityService securityService;

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