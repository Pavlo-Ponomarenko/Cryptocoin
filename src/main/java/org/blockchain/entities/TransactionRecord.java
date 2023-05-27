package org.blockchain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transactions")
public class TransactionRecord {
    @Id
    private String hash;
    @OneToMany
    private List<VINRecord> vins;
    @OneToMany
    private List<VOUTRecord> vouts;

    public TransactionRecord() {
    }

    public TransactionRecord(String hash, List<VINRecord> vins, List<VOUTRecord> vouts) {
        this.hash = hash;
        this.vins = vins;
        this.vouts = vouts;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public List<VINRecord> getVins() {
        return vins;
    }

    public void setVins(List<VINRecord> vins) {
        this.vins = vins;
    }

    public List<VOUTRecord> getVouts() {
        return vouts;
    }

    public void setVouts(List<VOUTRecord> vouts) {
        this.vouts = vouts;
    }
}
