package org.blockchain.dtos;

import org.blockchain.utils.HashGenerator;

public class SignatureDTO implements Hashable {

    private String signature;
    private String key;

    public SignatureDTO() {}

    public SignatureDTO(String signature, String key) {
        this.signature = signature;
        this.key = key;
    }

    @Override
    public String genHash() {
        return HashGenerator.genHash256(signature + key);
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
