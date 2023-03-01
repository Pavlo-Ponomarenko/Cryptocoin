package org.blockchain.utils.merkle_tree;

import org.blockchain.dtos.Hashable;

public class Leaf implements Node {

    private Hashable obj;

    public Leaf(Hashable obj) {
        this.obj = obj;
    }

    @Override
    public String getHash() {
        return obj.genHash();
    }
}
