package org.blockchain.view;

import org.blockchain.dtos.Transaction;

public class SigningForm {
    private String privateKey;
    private Transaction transaction;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getTransactionHash() {
        return transaction.genHash();
    }
}
