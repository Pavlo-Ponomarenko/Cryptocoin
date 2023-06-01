package org.blockchain.utils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class Signing {

    private static final String ALGORITHM = "SHA256withECDSA";

    public static String sign(String message, String privateKey) {
        try {
            Signature s = Signature.getInstance(ALGORITHM,"SunEC");
            PrivateKey key = CryptoKeyGenerator.stringToPrivateKey(privateKey);
            s.initSign(key);
            s.update(message.getBytes());
            byte[] sig = s.sign();
            return Base64.getEncoder().encodeToString(sig);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean verify(String signature, String message, String publicKey) {
        try {
            Signature s = Signature.getInstance(ALGORITHM,"SunEC");
            PublicKey key = CryptoKeyGenerator.stringToPublicKey(publicKey);
            s.initVerify(key);
            s.update(message.getBytes());
            return s.verify(Base64.getDecoder().decode(signature));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
