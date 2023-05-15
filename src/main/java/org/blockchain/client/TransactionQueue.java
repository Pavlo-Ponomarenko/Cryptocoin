package org.blockchain.client;

import org.blockchain.dtos.Transaction;
import org.blockchain.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class TransactionQueue {

    private final Queue<Transaction> mempool = new ConcurrentLinkedDeque<>();
    @Autowired
    private TransactionService transactionService;

    public synchronized void addTransaction(Transaction transaction) {
        if (!mempool.contains(transaction) && transactionService.validate(transaction)) {
            mempool.add(transaction);
        }
    }

    public int getSize() {
        return mempool.size();
    }

    private List<Transaction> getBatch(int n) {
        List<Transaction> batch = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            batch.add(mempool.poll());
        }
        return batch;
    }

    public synchronized List<Transaction> getTransactions(int n) {
        Transaction[] array = mempool.toArray(new Transaction[mempool.size()]);
        Arrays.sort(array);
        List<Transaction> newContent = Arrays.asList(array);
        Collections.reverse(newContent);
        mempool.clear();
        mempool.addAll(newContent);
        return getBatch(n);
    }
}
