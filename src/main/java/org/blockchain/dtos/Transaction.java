package org.blockchain.dtos;

import org.blockchain.utils.HashGenerator;

import java.util.List;

public class Transaction implements Hashable {
    private List<VIN> vins;
    private List<VOUT> vouts;

    public Transaction() {}

    public Transaction(List<VIN> vins, List<VOUT> vouts) {
        this.vins = vins;
        this.vouts = vouts;
    }

    public List<VIN> getVins() {
        return vins;
    }

    public void setVins(List<VIN> vins) {
        this.vins = vins;
    }

    public List<VOUT> getVouts() {
        return vouts;
    }

    public void setVouts(List<VOUT> vouts) {
        this.vouts = vouts;
    }

    @Override
    public String genHash() {
        StringBuilder result = new StringBuilder();
        vins.forEach(item -> result.append(item.genHash()));
        vouts.forEach(item -> result.append(item.genHash()));
        return HashGenerator.genHash256(result.toString());
    }
}
