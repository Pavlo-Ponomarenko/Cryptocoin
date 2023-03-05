package org.blockchain.entities;

import org.blockchain.dtos.Transaction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "blocks")
public class BlockRecord {

    @Id
    private String hash;
    private String previousHash;
    private String merkleRoot;
    private String timeStamp;
    private Integer nonce;
    @OneToMany
    private List<TransactionRecord> transactions;

    public BlockRecord() {
    }

    public BlockRecord(String hash, String previousHash, String merkleRoot, String timeStamp, Integer nonce, List<TransactionRecord> transactions) {
        this.hash = hash;
        this.previousHash = previousHash;
        this.merkleRoot = merkleRoot;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
        this.transactions = transactions;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getNonce() {
        return nonce;
    }

    public void setNonce(Integer nonce) {
        this.nonce = nonce;
    }

    public List<TransactionRecord> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionRecord> transactions) {
        this.transactions = transactions;
    }
}
