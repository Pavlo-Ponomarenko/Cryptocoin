package org.blockchain.dtos;

import org.blockchain.utils.HashGenerator;

import java.util.List;

public class Transaction implements Hashable, Comparable<Transaction> {
    private List<VIN> vins;
    private List<VOUT> vouts;

    private Long commission = 0l;

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

    public Long getCommission() {
        return commission;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }

    @Override
    public String genHash() {
        StringBuilder result = new StringBuilder();
        vins.forEach(item -> result.append(item.genHash()));
        vouts.forEach(item -> result.append(item.genHash()));
        return HashGenerator.genHash256(result.toString());
    }

    @Override
    public int compareTo(Transaction o) {
        return commission.compareTo(o.commission);
    }

    @Override
    public boolean equals(Object o) {
        Transaction transaction2 = (Transaction) o;
        return vins.equals(transaction2.vins) && vouts.equals(transaction2.vouts);
    }
}
