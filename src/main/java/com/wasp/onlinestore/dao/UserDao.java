package com.wasp.onlinestore.dao;

import java.util.Optional;

public interface UserDao {
    boolean userExists(String name, String password);
    boolean saveUser(String name, String password, String salt);
    Optional<String> getSalt(String name);
}
