package org.blockchain.converters;

import org.blockchain.dtos.Block;
import org.blockchain.dtos.Transaction;
import org.blockchain.entities.BlockRecord;
import org.blockchain.entities.TransactionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockConverterImpl implements BlockConverter {

    @Autowired
    private TransactionConverter transactionConverter;
    @Autowired
    private DateConverter dateConverter;

    @Override
    public BlockRecord fromDTOtoEntity(Block block) {
        BlockRecord blockRecord = new BlockRecord(block.getHash(), block.getPreviousHash(), block.getMerkleRoot(), block.getNonce(), null);
        List<TransactionRecord> transactionRecords = new ArrayList<>();
        for (Transaction transaction : block.getTransactions()) {
            transactionRecords.add(transactionConverter.fromDTOtoEntity(transaction));
        }
        blockRecord.setTransactions(transactionRecords);
        if (block.getTimeStamp() != null) {
            blockRecord.setTimeStamp(dateConverter.fromDTOtoEntity(block.getTimeStamp()));
        }
        blockRecord.setHash(block.getHash());
        blockRecord.setPreviousHash(block.getPreviousHash());
        blockRecord.setMerkleRoot(block.getMerkleRoot());
        blockRecord.setNonce(block.getNonce());
        return blockRecord;
    }

    @Override
    public Block fromEntityToDTO(BlockRecord blockRecord) {
        Block block = new Block(blockRecord.getHash(), blockRecord.getPreviousHash(), blockRecord.getMerkleRoot(), blockRecord.getNonce(), null);
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionRecord transactionRecord : blockRecord.getTransactions()) {
            transactions.add(transactionConverter.fromEntityToDTO(transactionRecord));
        }
        block.setTransactions(transactions);
        block.setTimeStamp(dateConverter.fromEntityToDTO(blockRecord.getTimeStamp()));
        block.setHash(blockRecord.getHash());
        block.setPreviousHash(blockRecord.getPreviousHash());
        block.setMerkleRoot(blockRecord.getMerkleRoot());
        block.setNonce(blockRecord.getNonce());
        return block;
    }
}
