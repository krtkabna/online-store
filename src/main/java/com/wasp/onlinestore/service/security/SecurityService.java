package com.wasp.onlinestore.service.security;

import com.wasp.onlinestore.dao.UserDao;
import com.wasp.onlinestore.entity.User;
import com.wasp.onlinestore.exception.UserAlreadyExistsException;
import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.security.entity.Session;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SecurityService {
    private final UserDao userDao;
    private final List<Session> sessions;
    private final PasswordEncoder passwordEncoder;

    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
        this.sessions = new ArrayList<>();
        this.passwordEncoder = new PasswordEncoder();
    }

    public Optional<User> getUser(String name, String password) {
        Optional<String> optionalSalt = userDao.getSalt(name);
        if (optionalSalt.isEmpty()) {
            return Optional.empty();
        }
        String saltedHashedPassword = passwordEncoder.hashPassword(password + optionalSalt.get());
        return userDao.getUserByNameAndPassword(name, saltedHashedPassword);
    }

    public boolean isTokenValid(String value) {
        return sessions.stream()
            .map(Session::getToken)
            .anyMatch(value::equals);
    }

    public String login(String login, String password) {
        Optional<User> optionalUser = getUser(login, password);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("Could not find user by name: " + login);
        }
        User user = optionalUser.get();
        String token = generateToken();
        sessions.add(new Session(token, LocalDateTime.now(), user));
        return token;
    }

    public String register(String login, String password, boolean isAdmin) {
        User user;
        if (getUser(login, password).isEmpty()) {
            saveUser(login, password, isAdmin, passwordEncoder.getSalt());
            user = getUser(login, password).get();
        } else {
            throw new UserAlreadyExistsException("User already exists by name: " + login);
        }
        String token = generateToken();
        sessions.add(new Session(token, LocalDateTime.now(), user));
        return token;
    }

    private String generateToken() {
        return String.valueOf(UUID.randomUUID());
    }

    private void saveUser(String login, String password, boolean isAdmin, String salt) {
        String saltedHashedPassword = passwordEncoder.hashPassword(password + salt);
        userDao.saveUser(login, saltedHashedPassword, isAdmin, salt);
    }

    public Session getSessionByToken(String token) {
        Optional<Session> optionalSession = sessions.stream()
            .filter(session -> token.equals(session.getToken()))
            .findFirst();

        if (optionalSession.isPresent()) {
            return optionalSession.get();
        } else {
            throw new UserNotFoundException("No authorized users!");
        }
    }
}
