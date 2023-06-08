package org.blockchain.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "transactions")
public class TransactionRecord {
    @Id
    private String hash;
    @OneToMany(mappedBy = "hash", fetch = FetchType.EAGER)
    private Set<VINRecord> vins;
    @OneToMany(mappedBy = "transactionHash", fetch = FetchType.EAGER)
    private List<VOUTRecord> vouts;
    private String blockHash;

    public TransactionRecord() {
    }

    public TransactionRecord(String hash, Set<VINRecord> vins, List<VOUTRecord> vouts) {
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

    public Set<VINRecord> getVins() {
        return vins;
    }

    public void setVins(Set<VINRecord> vins) {
        this.vins = vins;
    }

    public List<VOUTRecord> getVouts() {
        return vouts;
    }

    public void setVouts(List<VOUTRecord> vouts) {
        this.vouts = vouts;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "hash='" + hash + '\'' +
                ", vins=" + vins +
                ", vouts=" + vouts +
                ", blockHash='" + blockHash + '\'' +
                '}';
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }
}
