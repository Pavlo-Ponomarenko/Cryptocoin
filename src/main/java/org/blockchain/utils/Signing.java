package org.blockchain.utils;

import java.math.BigInteger;
import java.security.Signature;

public class Signing {

    private static final String ALGORITHM = "SHA256withECDSA";

    public static String sign(String message, String privateKey) {
        try {
            Signature s = Signature.getInstance(ALGORITHM,"SunEC");
            byte[] keyBytes = (new BigInteger(privateKey, 16)).toByteArray();
            PrivateKeyImpl key = new PrivateKeyImpl(keyBytes);
            s.initSign(key);
            s.update(message.getBytes());
            byte[] sig = s.sign();
            return (new BigInteger(sig)).toString(16);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean verify(String signature, String message, String publicKey) {
        try {
            Signature s = Signature.getInstance(ALGORITHM,"SunEC");
            byte[] keyBytes = (new BigInteger(publicKey, 16)).toByteArray();
            PublicKeyImpl key = new PublicKeyImpl(keyBytes);
            s.initVerify(key);
            s.update(message.getBytes());
            return s.verify((new BigInteger(signature, 16)).toByteArray());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
