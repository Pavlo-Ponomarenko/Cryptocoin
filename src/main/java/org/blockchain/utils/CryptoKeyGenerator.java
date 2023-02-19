package org.blockchain.utils;

import org.blockchain.dtos.CryptoKeyPair;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;
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
            Base64.Encoder encoder = Base64.getEncoder();
            return new CryptoKeyPair(encoder.encodeToString(privKey.getEncoded()), encoder.encodeToString(pubKey.getEncoded()));
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
