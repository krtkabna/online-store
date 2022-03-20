package com.wasp.onlinestore.service;

import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.service.security.entity.Session;
import jakarta.servlet.http.Cookie;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SessionService {
    private final SecurityService securityService;

    public SessionService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public Session getSession(Cookie[] cookies) {
        return securityService.getSessionByToken(getUserToken(cookies));
    }

    private String getUserToken(Cookie[] cookies) {
        return Arrays.stream(cookies)
            .filter(cookie -> "user-token".equals(cookie.getName()))
            .map(Cookie::getValue)
            .collect(Collectors.joining());
    }
}
