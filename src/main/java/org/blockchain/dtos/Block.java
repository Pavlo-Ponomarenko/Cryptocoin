package org.blockchain.dtos;

import java.util.List;

public class Block {
    private String previousHash;
    private String merkleRoot;
    private String timeStamp;
    private Integer nonce;
    private List<Transaction> transactions;

    public Block(String previousHash, String merkleRoot, String timeStamp, Integer nonce, List<Transaction> transactions) {
        this.previousHash = previousHash;
        this.merkleRoot = merkleRoot;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
        this.transactions = transactions;
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
