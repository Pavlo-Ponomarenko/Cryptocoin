package org.blockchain.utils;

import org.blockchain.dtos.CryptoKeyPair;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptoKeyGenerator {

    public static CryptoKeyPair genNewPair() {
        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("EC", "SunEC");
            ECGenParameterSpec ecsp = new ECGenParameterSpec("secp256r1");
            g.initialize(ecsp);
            KeyPair kp = g.genKeyPair();
            PrivateKey privKey = kp.getPrivate();
            PublicKey pubKey = kp.getPublic();
            return new CryptoKeyPair(privateKeyToString(privKey), publicKeyToString(pubKey));
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String privateKeyToString(PrivateKey privateKey) {
        byte[] encodedPrivateKey = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(encodedPrivateKey);
    }

    public static PrivateKey stringToPrivateKey(String privateKeyString) throws Exception {
        byte[] encodedPrivateKey = Base64.getDecoder().decode(privateKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        return keyFactory.generatePrivate(privateKeySpec);
    }

    public static String publicKeyToString(PublicKey publicKey) {
        byte[] encodedPublicKey = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(encodedPublicKey);
    }

    public static PublicKey stringToPublicKey(String publicKeyString) throws Exception {
        byte[] encodedPublicKey = Base64.getDecoder().decode(publicKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        return keyFactory.generatePublic(publicKeySpec);
    }
}
