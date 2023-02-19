package org.blockchain.utils;

import java.security.PublicKey;

public class PublicKeyImpl implements PublicKey {

    private byte[] key;

    public PublicKeyImpl(byte[] key) {
        this.key = key;
    }

    @Override
    public String getAlgorithm() {
        return "EC";
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    @Override
    public byte[] getEncoded() {
        return key;
    }
}
