package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.SessionFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
@Component
public class SessionFilter implements Filter {
    public static final String SESSION = "session";
    private final SecurityService securityService;

    @Autowired
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