package org.blockchain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "blocks")
public class BlockRecord {

    @Id
    private String hash;
    private String previousHash;
    private String merkleRoot;
    private LocalDateTime timeStamp;
    private Integer nonce;
    @OneToMany(mappedBy = "blockHash", fetch = FetchType.EAGER)
    private List<TransactionRecord> transactions;

    public BlockRecord() {
    }

    public BlockRecord(String hash, String previousHash, String merkleRoot, Integer nonce, List<TransactionRecord> transactions) {
        this.hash = hash;
        this.previousHash = previousHash;
        this.merkleRoot = merkleRoot;
        this.nonce = nonce;
        this.transactions = transactions;
    }

    public BlockRecord(String hash, String previousHash, String merkleRoot, LocalDateTime timeStamp, Integer nonce, List<TransactionRecord> transactions) {
        this(hash, previousHash, merkleRoot, nonce, transactions);
        this.timeStamp = timeStamp;
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

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
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

    @Override
    public String toString() {
        return "BlockRecord{" +
                "hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", merkleRoot='" + merkleRoot + '\'' +
                ", timeStamp=" + timeStamp +
                ", nonce=" + nonce +
                ", transactions=" + transactions +
                '}';
    }
}
