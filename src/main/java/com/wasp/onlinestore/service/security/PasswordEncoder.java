package com.wasp.onlinestore.service.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class PasswordEncoder {
    private final MessageDigest MESSAGE_DIGEST;

    public PasswordEncoder() {
        try {
            MESSAGE_DIGEST = MessageDigest.getInstance("SHA-256");
        } catch (
            NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }

    public String hashPassword(String saltedPassword) {
        MESSAGE_DIGEST.update(saltedPassword.getBytes());
        byte[] hash = MESSAGE_DIGEST.digest();
        return new String(hash, StandardCharsets.UTF_8);
    }

    public String getSalt() {
        return UUID.randomUUID().toString();
    }
}
