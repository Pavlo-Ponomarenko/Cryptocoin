package org.blockchain.dtos;

import org.blockchain.utils.HashGenerator;

public class VIN implements Hashable {
    private String hash;
    private Integer index;
    private SignatureDTO signature;

    public VIN() {}

    public VIN(String hash, Integer index, SignatureDTO signature) {
        this.hash = hash;
        this.index = index;
        this.signature = signature;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public SignatureDTO getSignature() {
        return signature;
    }

    public void setSignature(SignatureDTO signature) {
        this.signature = signature;
    }

    @Override
    public String genHash() {
        return HashGenerator.genHash256(hash + index.toString());
    }

    @Override
    public boolean equals(Object o) {
        VIN vin2 = (VIN) o;
        return hash.equals(vin2.getHash()) && index.equals(vin2.getIndex()) && signature.equals(vin2.getSignature());
    }
}
