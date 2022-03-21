package com.wasp.onlinestore.web.util;

import com.wasp.onlinestore.service.security.entity.Session;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import java.util.Arrays;
import java.util.stream.Collectors;

@UtilityClass
public class SessionFetcher {
    public Session getSession(HttpServletRequest req) {
        return (Session) req.getAttribute("session");
    }

    public String getUserToken(Cookie[] cookies) {
        return Arrays.stream(cookies)
            .filter(cookie -> "user-token".equals(cookie.getName()))
            .map(Cookie::getValue)
            .collect(Collectors.joining());
    }
}
