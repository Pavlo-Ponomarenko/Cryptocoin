package org.blockchain.client;

import org.blockchain.config.ClientConfig;
import org.blockchain.dtos.Block;
import org.blockchain.dtos.Transaction;
import org.blockchain.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BlockProcessor {

    @Autowired
    private TransactionQueue transactionQueue;
    @Autowired
    private TransactionService transactionService;

    public Block createBlock(List<Transaction> transactions) {
        return null;
    }

    public void run() {
        while (true) {
            if (transactionQueue.getSize() >= ClientConfig.transactionsInBlock) {
                List<Transaction> transactions = transactionQueue.getTransactions(ClientConfig.transactionsInBlock);
            }
        }
    }
}
