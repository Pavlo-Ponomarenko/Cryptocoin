package org.blockchain.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashGenerator {

    public static String genHash256(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes());
            return (new BigInteger(hash)).toString(16);
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String keyToAddress(String key) {
        byte[] bytes = (new BigInteger(key, 16)).toByteArray();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(bytes);
            return (new BigInteger(hashBytes)).toString(16);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
