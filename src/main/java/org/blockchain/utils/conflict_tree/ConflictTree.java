package org.blockchain.utils.conflict_tree;

import org.blockchain.dtos.Block;

import java.util.List;
import java.util.Map;

public class ConflictTree {

    private Leaf root;

    public ConflictTree(List<Block> blocks) {
        build(blocks);
    }

    public void build(List<Block> blocks) {
        List<Leaf> leafs = blocks.stream().map(Leaf::new).toList();
        for (int i = 0; i < leafs.size(); i++) {
            Leaf currentLeaf = leafs.get(i);
            for (int m = i + 1; m < leafs.size(); m++) {
                Leaf nextLeaf = leafs.get(m);
                if (nextLeaf.isRelatedTo(currentLeaf)) {
                    currentLeaf.connect(nextLeaf);
                }
            }
        }
        root = leafs.get(0);
    }

    public Map.Entry<Integer, Block> getFinalLeaf() {
        return root.getLongestBranchEnd();
    }

    public Block getValidatedBlock() {
        return root.getValidatedLeaf().getValue();
    }

    public Block getRootBlock() {
        return root.getBlock();
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
