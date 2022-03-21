package com.wasp.onlinestore.service.security;

import com.wasp.onlinestore.entity.User;
import com.wasp.onlinestore.exception.PasswordMismatchException;
import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.UserService;
import com.wasp.onlinestore.service.security.entity.Role;
import com.wasp.onlinestore.service.security.entity.Session;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.wasp.onlinestore.web.util.SessionFetcher.EXPIRE_IN_MINUTES;

public class SecurityService {
    private final UserService userService;
    private final Map<String, Session> sessions;
    private final PasswordEncoder passwordEncoder;

    public SecurityService(UserService userService) {
        this.userService = userService;
        this.sessions = new ConcurrentHashMap<>();
        this.passwordEncoder = new PasswordEncoder();
    }

    //get the whole user with one sql query
    public User getUser(String name) {
        return userService.getUserByName(name)
            .orElseThrow(() -> new UserNotFoundException("Could not find user by name: " + name));
    }

    public String login(String login, String password) {
        User user = getUser(login);
        checkPasswordMatchesName(user, password);
        String token = generateToken();
        sessions.put(token, new Session(token, LocalDateTime.now().plusMinutes(EXPIRE_IN_MINUTES), user));
        return token;
    }

    public String register(String login, String password) {
        saveUser(login, password, passwordEncoder.getSalt());
        User user = getUser(login);
        String token = generateToken();
        sessions.put(token, new Session(token, LocalDateTime.now().plusMinutes(EXPIRE_IN_MINUTES), user));
        return token;
    }

    public Session getSessionByToken(String token) {
        if (isTokenValid(token)) {
            return sessions.get(token);
        } else {
            return null;
        }
    }

    private String generateToken() {
        return String.valueOf(UUID.randomUUID());
    }

    private boolean isTokenValid(String token) {
        return sessions.containsKey(token);
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
