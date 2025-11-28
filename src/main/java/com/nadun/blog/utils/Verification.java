package com.nadun.blog.utils;

public class Verification {

    /**
     * Generate a simple verification token
     * 
     * @param email {String}
     * @param date  {Date}
     * @return Generated token {String}
     */
    public static String generateToken() {
        byte[] bytes = new byte[32];
        new java.security.SecureRandom().nextBytes(bytes);
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * Verify the token
     * 
     * @param token {String}
     * @return true if valid, false otherwise
     */
    public static boolean verifyToken(String storedToken, String providedToken) {
        return storedToken.equals(providedToken);
    }

}
