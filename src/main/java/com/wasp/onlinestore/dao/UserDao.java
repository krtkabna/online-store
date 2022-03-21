package com.wasp.onlinestore.dao;

import com.wasp.onlinestore.entity.User;
import java.util.Optional;

public interface UserDao {
    Optional<User> getUserByName(String name);

    void saveUser(User user);
}
