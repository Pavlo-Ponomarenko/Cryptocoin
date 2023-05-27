package org.blockchain.services;

import org.blockchain.converters.BlockConverter;
import org.blockchain.dtos.Block;
import org.blockchain.entities.BlockRecord;
import org.blockchain.entities.StateRegister;
import org.blockchain.entities.TransactionRecord;
import org.blockchain.entities.VOUTRecord;
import org.blockchain.repository.BlockRepository;
import org.blockchain.repository.StateRegisterRepository;
import org.blockchain.utils.conflict_tree.ConflictTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private StateRegisterRepository stateRegisterRepository;
    @Autowired
    private BlockConverter blockConverter;

    public void addBlock(Block block) {
        blockRepository.save(blockConverter.fromDTOtoEntity(block));
    }

    private List<Block> getBlocksSequence() {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("time_stamp").descending());
        Page<BlockRecord> page = blockRepository.findAll(pageRequest);
        List<Block> blocks = new ArrayList<>(page.stream().map(x -> blockConverter.fromEntityToDTO(x)).toList());
        Collections.reverse(blocks);
        return blocks;
    }

    public Block getPreviousBlock() {
        List<Block> blocks = getBlocksSequence();
        ConflictTree conflictTree = new ConflictTree(blocks);
        return conflictTree.getFinalLeaf().getValue();
    }

    public BlockRecord getNewActualBlock() {
        List<Block> blocks = getBlocksSequence();
        ConflictTree conflictTree = new ConflictTree(blocks);
        if (conflictTree.getFinalLeaf().getKey() > 1) {
            String oldHash = stateRegisterRepository.findAll().get(0).getHash();
            String newHash = conflictTree.getValidatedBlock().getHash();
            if (!newHash.equals(oldHash)) {
                stateRegisterRepository.deleteAll();
                stateRegisterRepository.save(new StateRegister(newHash));
                return blockRepository.getById(newHash);
            }
        }
        return null;
    }
}
