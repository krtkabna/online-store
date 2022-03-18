package com.wasp.onlinestore.service.security;

import com.wasp.onlinestore.entity.User;
import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.UserService;
import com.wasp.onlinestore.service.security.entity.Role;
import com.wasp.onlinestore.service.security.entity.Session;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityService {
    private final UserService userService;
    private final Map<String, Session> sessions;
    private final PasswordEncoder passwordEncoder;

    public SecurityService(UserService userService) {
        this.userService = userService;
        this.sessions = new ConcurrentHashMap<>();
        this.passwordEncoder = new PasswordEncoder();
    }

    public User getUser(String name, String password) {
        String salt = userService.getSalt(name);
        String saltedHashedPassword = passwordEncoder.hashPassword(password + salt);
        return userService.getUserByNameAndPassword(name, saltedHashedPassword)
            .orElseThrow(() -> new UserNotFoundException("Could not find user by name: " + name));
    }

    public String login(String login, String password) {
        User user = getUser(login, password);
        String token = generateToken();
        sessions.put(token, new Session(token, LocalDateTime.now(), user));
        return token;
    }

    public String register(String login, String password, boolean isAdmin) {
        saveUser(login, password, isAdmin, passwordEncoder.getSalt());
        User user = getUser(login, password);
        String token = generateToken();
        sessions.put(token, new Session(token, LocalDateTime.now(), user));
        return token;
    }

    public Session getSessionByToken(String token) {
        if (isTokenValid(token)) {
            return sessions.get(token);
        } else {
            throw new UserNotFoundException("No authorized users!");
        }
    }

    private String generateToken() {
        return String.valueOf(UUID.randomUUID());
    }

    private boolean isTokenValid(String token) {
        return sessions.containsKey(token);
    }

    private void saveUser(String login, String password, boolean isAdmin, String salt) {
        String saltedHashedPassword = passwordEncoder.hashPassword(password + salt);
        User user = User.builder().name(login).password(saltedHashedPassword).role(Role.getUserRole(isAdmin)).build();
        userService.saveUser(user, salt);
    }
}
