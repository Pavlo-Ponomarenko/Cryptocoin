package org.blockchain.utils.merkle_tree;

import org.blockchain.dtos.Hashable;
import org.blockchain.utils.HashGenerator;

import java.util.ArrayList;
import java.util.List;

public class MerkleTree<T extends Hashable> {

    private BranchNode root;

    public MerkleTree(List<T> hashableItems) {
        if (hashableItems == null) {
            root = new BranchNode("");
        } else {
            buildTree(hashableItems);
        }
    }

    public String buildTree(List<T> hashableItems) {
        List<T> items = hashableItems.stream().toList();
        List<Node> nodes = new ArrayList<Node>();
        for (Hashable i : items) {
            nodes.add(new Leaf(i));
        }
        do {
            if (nodes.size() % 2 != 0) {
                nodes.add(null);
            }
            List<Node> newLevel = new ArrayList<Node>();
            for (int i = 0; i < nodes.size(); i += 2) {
                Node v1 = nodes.get(i);
                Node v2 = nodes.get(i + 1);
                String newHash;
                if (v2 == null) {
                    newHash = v1.getHash();
                } else {
                    newHash = HashGenerator.genHash256(v1.getHash() + v2.getHash());
                }
                newLevel.add(new BranchNode(newHash));
            }
            nodes = newLevel;
        } while (nodes.size() > 1);
        root = (BranchNode) nodes.get(0);
        return root.getHash();
    }

    public String getRootHash() {
        return root.getHash();
    }
}
