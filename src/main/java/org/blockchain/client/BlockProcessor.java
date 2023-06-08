package org.blockchain.client;

import org.blockchain.config.ClientConfig;
import org.blockchain.converters.DateConverter;
import org.blockchain.dtos.Block;
import org.blockchain.dtos.Transaction;
import org.blockchain.services.AccountService;
import org.blockchain.services.BlockService;
import org.blockchain.services.TransactionService;
import org.blockchain.utils.HashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BlockProcessor {

    private final int MAX_NONCE = Integer.MAX_VALUE - 1;
    private final BigInteger maxNum = new BigInteger(ClientConfig.mining_difficulty, 16);
    private volatile boolean blockIsMined = true;
    private volatile List<Transaction> transactionsProcessing;

    @Autowired
    private TransactionQueue transactionQueue;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BlockService blockService;
    @Autowired
    private DateConverter dateConverter;
    @Autowired
    private ClientConfig clientConfig;

    public boolean verifyTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            if (!transactionService.validate(transaction)) {
                return false;
            }
        }
        return true;
    }

    public String createBaseHash(Block block) {
        return HashGenerator.genHash256(block.getPreviousHash() + block.getMerkleRoot() + block.getTimeStamp());
    }

    public void validateForeignBlock(Block block) {
        if (!verifyTransactions(block.getTransactions())) {
            return;
        }
        blockIsMined = false;
        if (transactionsProcessing != null) {
            transactionQueue.returnTransactions(transactionsProcessing);
        }
        transactionQueue.removeAll(block.getTransactions());
        acceptBlock(block);
        blockIsMined = true;
    }

    private void acceptBlock(Block block) {
        for (Client client : clientConfig.getOtherClients()) {
            client.sendBlock(block);
        }
        blockService.addBlock(block);
        accountService.updateAccounts();
    }

    public void run() {
        System.out.println("Blocks processing starts");
        while (true) {
            if (transactionQueue.getSize() >= ClientConfig.transactionsInBlock && blockIsMined) {
                List<Transaction> transactions = transactionQueue.getTransactions(ClientConfig.transactionsInBlock);
                transactionsProcessing = transactions;
                Block block = new Block();
                block.setPreviousHash(blockService.getPreviousBlock().getHash());
                block.setMerkleRoot(transactions);
                block.setTransactions(transactions);
                block.setTimeStamp(dateConverter.fromEntityToDTO(LocalDateTime.now()));
                String baseHash = createBaseHash(block);
                int miningResult = -1;
                System.out.println("Mining started");
                for (int nonce = 0; nonce <= MAX_NONCE && blockIsMined; nonce++) {
                    String hash = HashGenerator.genHash256(baseHash + nonce);
                    BigInteger numRepr = new BigInteger(hash, 16).abs();
                    if (numRepr.compareTo(maxNum) < 0) {
                        miningResult = nonce;
                        break;
                    }
                }
                if (blockIsMined) {
                    transactionsProcessing = null;
                    block.setNonce(miningResult);
                    block.setHash(block.genHash());
                    acceptBlock(block);
                }
            }
        }
    }
}
