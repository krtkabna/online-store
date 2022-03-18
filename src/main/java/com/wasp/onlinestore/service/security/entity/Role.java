package com.wasp.onlinestore.service.security.entity;

public enum Role {
    ADMIN, USER;

    public static Role getUserRole(boolean isAdmin) {
        return isAdmin ? ADMIN : USER;
    }
}
