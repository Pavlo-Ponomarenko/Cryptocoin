package org.blockchain.dtos;

import org.blockchain.utils.HashGenerator;

public class VOUT implements Hashable {
    private Long value;
    private String address;

    public VOUT() {}

    public VOUT(Long value, String address) {
        this.value = value;
        this.address = address;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String genHash() {
        return HashGenerator.genHash256(value.toString() + address);
    }

    @Override
    public boolean equals(Object o) {
        VOUT vout2 = (VOUT) o;
        return value.equals(vout2.value) && address.equals(vout2.address);
    }
}
