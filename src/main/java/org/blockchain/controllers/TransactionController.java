package org.blockchain.controllers;

import org.blockchain.client.TransactionQueue;
import org.blockchain.config.ClientConfig;
import org.blockchain.dtos.Transaction;
import org.blockchain.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TransactionController {

    @Autowired
    private ClientConfig clientConfig;
    @Autowired
    private TransactionQueue transactionQueue;

    @PostMapping("send_transaction")
    public ResponseEntity<Void> becomeTransaction(@RequestBody Transaction transaction) {
        transactionQueue.addTransaction(transaction);
        for (Client client : clientConfig.getOtherClients()) {
            client.sendTransaction(transaction);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
