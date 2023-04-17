package org.blockchain.client;

import org.blockchain.dtos.Transaction;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class TransactionQueue {

    private Queue<Transaction> queue = new LinkedList<>();

    public void addTransaction(Transaction transaction) {
        if (!queue.contains(transaction)) {
            queue.add(transaction);
        }
    }
}
