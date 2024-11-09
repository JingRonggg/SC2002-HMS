package src.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class for hashing and verifying passwords using SHA-256 algorithm with a fixed salt.
 */
public class PasswordHasher {
    /** Fixed salt value used in password hashing */
    private static final String FIXED_SALT = "SC2002";

    /**
     * Gets the fixed salt value used for password hashing.
     * @return The salt value as a String
     */
    public static String getSalt() {
        return FIXED_SALT;
    }

    /**
     * Hashes a password using SHA-256 algorithm with a fixed salt.
     * @param password The plain text password to hash
     * @return The Base64 encoded string of the hashed password
     * @throws RuntimeException If the SHA-256 algorithm is not available
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(FIXED_SALT.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Verifies if a plain text password matches a hashed password.
     * @param password The plain text password to verify
     * @param hashedPassword The hashed password to compare against
     * @return true if the passwords match, false otherwise
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        String computedHash = hashPassword(password);
        return computedHash.equals(hashedPassword);
    }
}
