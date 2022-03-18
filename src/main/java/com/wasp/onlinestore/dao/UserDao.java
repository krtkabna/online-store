package com.wasp.onlinestore.dao;

import com.wasp.onlinestore.entity.User;
import java.util.Optional;

public interface UserDao {
    Optional<User> getUserByNameAndPassword(String name, String password);

    void saveUser(User user, String salt);

    Optional<String> getSalt(String name);
}
