package com.wasp.onlinestore.service.security;

import com.wasp.onlinestore.entity.User;
import com.wasp.onlinestore.exception.PasswordMismatchException;
import com.wasp.onlinestore.service.UserService;
import com.wasp.onlinestore.service.security.entity.Role;
import com.wasp.onlinestore.service.security.entity.Session;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@PropertySource("classpath:/properties/application.properties")
public class SecurityService {
    private final UserService userService;
    private final Map<String, Session> sessions;
    private final PasswordEncoder passwordEncoder;

    @Getter
    @Value("${session.ttl.seconds}")
    private int sessionTtlSeconds;

    @Autowired
    public SecurityService(UserService userService) {
        this.userService = userService;
        this.sessions = new ConcurrentHashMap<>();
        this.passwordEncoder = new PasswordEncoder();
    }

    public String login(String login, String password) {
        User user = userService.getUserByName(login);
        checkPasswordMatchesName(user, password);
        String token = generateToken();
        sessions.put(token, new Session(token, LocalDateTime.now().plusMinutes(getCookieTtlMinutes()), user));
        return token;
    }

    public String register(String login, String password) {
        saveUser(login, password, passwordEncoder.getSalt());
        User user = userService.getUserByName(login);
        String token = generateToken();
        sessions.put(token, new Session(token, LocalDateTime.now().plusMinutes(getCookieTtlMinutes()), user));
        return token;
    }

    public Session getSessionByToken(String token) {
        if (isTokenValid(token)) {
            return sessions.get(token);
        } else {
            return null;
        }
    }

    private long getCookieTtlMinutes() {
        return sessionTtlSeconds / 60;
    }

    private String generateToken() {
        return String.valueOf(UUID.randomUUID());
    }

    //check if it's not expired too
    private boolean isTokenValid(String token) {
        return sessions.containsKey(token) && sessions.get(token).getExpireDateTime().isAfter(LocalDateTime.now());
    }

    private void saveUser(String login, String password, String salt) {
        String saltedHashedPassword = passwordEncoder.hashPassword(password + salt);
        User user = User.builder()
            .name(login)
            .password(saltedHashedPassword)
            .salt(salt)
            .role(Role.USER)
            .build();
        userService.saveUser(user);
    }

    private void checkPasswordMatchesName(User user, String password) {
        String saltedHashedPassword = passwordEncoder.hashPassword(password + user.getSalt());
        if (!saltedHashedPassword.equals(user.getPassword())) {
            throw new PasswordMismatchException("Provided password doesn't match user{name=" + user.getName() + "}");
        }
    }
}
