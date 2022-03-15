package com.wasp.onlinestore.dao;

import java.util.Optional;

public interface UserDao {
    boolean userExists(String name, String password);
    void saveUser(String name, String password, String salt);
    Optional<String> getSalt(String name);
    boolean isAdmin(String login);
}
