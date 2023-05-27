package org.blockchain.dtos;

import org.blockchain.utils.HashGenerator;
import org.blockchain.utils.merkle_tree.MerkleTree;

import java.util.List;

public class Block implements Hashable {

    private String hash;
    private String previousHash;
    private String merkleRoot;
    private String timeStamp;
    private Integer nonce;
    private List<Transaction> transactions;


    public Block() {
    }

    public Block(String hash, String previousHash, String merkleRoot, String timeStamp, Integer nonce, List<Transaction> transactions) {
        this.hash = hash;
        this.previousHash = previousHash;
        this.merkleRoot = merkleRoot;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
        this.transactions = transactions;
    }

    public Block(String hash, String previousHash, String timeStamp, Integer nonce, List<Transaction> transactions) {
        this(hash, previousHash, new MerkleTree<>(transactions).getRootHash(), timeStamp, nonce, transactions);
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setHash() {

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

    public void setMerkleRoot(List<Transaction> transactions) {
        merkleRoot = new MerkleTree<>(transactions).getRootHash();
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

    @Override
    public String genHash() {
        StringBuilder result = new StringBuilder();
        result.append(getPreviousHash());
        result.append(getMerkleRoot());
        result.append(getTimeStamp());
        result.append(getNonce());
        return HashGenerator.genHash256(result.toString());
    }
}
