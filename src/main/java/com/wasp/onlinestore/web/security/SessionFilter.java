package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.SessionFetcher;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SessionFilter implements Filter {
    public static final String SESSION = "session";
    private SecurityService securityService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        autowireSecurityService(httpServletRequest);

        Cookie[] cookies = httpServletRequest.getCookies();
        Session session = securityService.getSessionByToken(SessionFetcher.getUserToken(cookies));

        if (session != null) {
            httpServletRequest.setAttribute(SESSION, session);
        }
        chain.doFilter(request, response);
    }

    private void autowireSecurityService(HttpServletRequest httpServletRequest) {
        ServletContext servletContext = httpServletRequest.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        if (webApplicationContext != null) {
            securityService = webApplicationContext.getBean(SecurityService.class);
        } else throw new ApplicationContextException("Could not get application context");
    }
}