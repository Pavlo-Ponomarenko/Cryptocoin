package org.blockchain.utils;

import java.security.PrivateKey;

public class PrivateKeyImpl implements PrivateKey {

    private byte[] key;

    public PrivateKeyImpl(byte[] key) {
        this.key = key;
    }

    @Override
    public String getAlgorithm() {
        return "EC";
    }

    @Override
    public String getFormat() {
        return "PKCS#8";
    }

    @Override
    public byte[] getEncoded() {
        return key;
    }
}
