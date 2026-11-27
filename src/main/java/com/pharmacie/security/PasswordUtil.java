package com.pharmacie.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private static final int WORK_FACTOR = 12;

    // Hash a password
    public static String hash(String password) {
        return BCrypt.hashpw(
            password,
            BCrypt.gensalt(WORK_FACTOR)
        );
    }

    // Verify a password
    public static boolean verify(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}