package org.blockchain.dtos;

import org.blockchain.utils.HashGenerator;

public class SignatureDTO implements Hashable {

    private String message;
    private String signature;
    private String key;

    public SignatureDTO() {}

    public SignatureDTO(String message, String signature, String key) {
        this.message = message;
        this.signature = signature;
        this.key = key;
    }

    @Override
    public String genHash() {
        return HashGenerator.genHash256(message + signature + key);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
