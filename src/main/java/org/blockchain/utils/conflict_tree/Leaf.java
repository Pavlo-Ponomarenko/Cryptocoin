package org.blockchain.utils.conflict_tree;

import org.blockchain.dtos.Block;

import java.util.AbstractMap;
import java.util.Map;

public class Leaf {

    private final Block block;
    private Leaf leftLeaf;
    private Leaf rightLeaf;

    public Leaf(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public boolean isRelatedTo(Leaf leaf) {
        return block.getPreviousHash().equals(leaf.block.getHash());
    }

    public void connect(Leaf leaf) {
        if (rightLeaf != null) {
            rightLeaf = leaf;
        } else if (leftLeaf != null) {
            leftLeaf = leaf;
        }
    }

    public Map.Entry<Integer, Block> getLongestBranchEnd() {
        if (rightLeaf != null) {
            Map.Entry<Integer, Block> rightPair = rightLeaf.getLongestBranchEnd();
            if (leftLeaf != null) {
                Map.Entry<Integer, Block> leftPair = leftLeaf.getLongestBranchEnd();
                if (rightPair.getKey() < leftPair.getKey()) {
                    return new AbstractMap.SimpleEntry<>(leftPair.getKey() + 1, leftPair.getValue());
                }
            }
            return new AbstractMap.SimpleEntry<>(rightPair.getKey() + 1, rightPair.getValue());
        }
        if (leftLeaf != null) {
            Map.Entry<Integer, Block> leftPair = leftLeaf.getLongestBranchEnd();
            return new AbstractMap.SimpleEntry<>(leftPair.getKey() + 1, leftPair.getValue());
        }
        return new AbstractMap.SimpleEntry<>(1, block);
    }

    private Map.Entry<Integer, Block> lookForValidatedLeaf(Leaf leaf) {
        Map.Entry<Integer, Block> result = leaf.getValidatedLeaf();
        if (result.getKey() == 2) {
            return new AbstractMap.SimpleEntry<>(2, block);
        } else {
            return new AbstractMap.SimpleEntry<>(result.getKey() + 1, null);
        }
    }

    public Map.Entry<Integer, Block> getValidatedLeaf() {
        if (rightLeaf != null) {
            return lookForValidatedLeaf(rightLeaf);
        }
        if (leftLeaf != null) {
            return lookForValidatedLeaf(leftLeaf);
        }
        return new AbstractMap.SimpleEntry<>(0, null);
    }
}
