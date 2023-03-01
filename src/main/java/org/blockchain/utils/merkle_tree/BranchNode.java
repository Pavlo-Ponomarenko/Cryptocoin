package org.blockchain.utils.merkle_tree;

public class BranchNode implements Node {

    private String hash;

    public BranchNode(String hash) {
        this.hash = hash;
    }

    @Override
    public String getHash() {
        return hash;
    }
}
