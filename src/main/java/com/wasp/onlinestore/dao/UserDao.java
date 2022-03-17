package com.wasp.onlinestore.dao;

import com.wasp.onlinestore.entity.User;
import java.util.Optional;

public interface UserDao {
    Optional<User> getUserByNameAndPassword(String name, String password);

    void saveUser(String name, String password, boolean isAdmin, String salt);

    Optional<String> getSalt(String name);

    boolean isAdmin(String login);
}
