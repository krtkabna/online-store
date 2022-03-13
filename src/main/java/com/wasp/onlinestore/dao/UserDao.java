package com.wasp.onlinestore.dao;

public interface UserDao {
    boolean userExists(String name, String password);
    boolean saveUser(String name, String password);
}
