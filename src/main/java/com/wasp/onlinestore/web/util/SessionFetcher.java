package com.wasp.onlinestore.web.util;

import com.wasp.onlinestore.service.security.entity.Session;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import java.util.Arrays;
import java.util.stream.Collectors;

@UtilityClass
public class SessionFetcher {
    public static final int EXPIRE_IN_SECONDS = 900;
    public static final long EXPIRE_IN_MINUTES = EXPIRE_IN_SECONDS / 60;

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
