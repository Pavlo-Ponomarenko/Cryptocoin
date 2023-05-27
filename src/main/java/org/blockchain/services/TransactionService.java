package org.blockchain.services;

import org.blockchain.converters.TransactionConverter;
import org.blockchain.dtos.SignatureDTO;
import org.blockchain.dtos.Transaction;
import org.blockchain.dtos.VIN;
import org.blockchain.dtos.VOUT;
import org.blockchain.entities.TransactionRecord;
import org.blockchain.entities.VOUTRecord;
import org.blockchain.repository.AccountRepository;
import org.blockchain.repository.FreeVOUTRepository;
import org.blockchain.repository.TransactionRepository;
import org.blockchain.utils.Signing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private FreeVOUTRepository freeVOUTRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionConverter transactionConverter;

    public boolean validate(Transaction transaction) {
        long vinSum = 0L;
        for (VIN vin : transaction.getVins()) {
            SignatureDTO signature = vin.getSignature();
            if (!Signing.verify(signature.getSignature(), transaction.genHash(), signature.getKey())) {
                return false;
            }
            Optional<TransactionRecord> previousTransactionRecord = transactionRepository.findById(vin.getHash());
            if (previousTransactionRecord.isEmpty()) {
                return false;
            }
            TransactionRecord previousTransaction = previousTransactionRecord.get();
            List<VOUTRecord> previousVouts = previousTransaction.getVouts();
            Integer previousVoutIndex = vin.getIndex();
            if (previousVouts.size() >= previousVoutIndex) {
                return false;
            }
            if (!previousVouts.get(previousVoutIndex).getAddress().equals(signature.getKey())) {
                return false;
            }
            if (!freeVOUTRepository.existsById(previousVoutIndex.longValue())) {
                return false;
            }
            vinSum += previousVouts.get(vin.getIndex()).getValue();
        }
        long voutSum = 0l;
        for (VOUT vout : transaction.getVouts()) {
            if (!accountRepository.existsById(vout.getAddress())) {
                return false;
            }
            voutSum += vout.getValue();
        }
        if (voutSum > vinSum) {
            return false;
        }
        transaction.setCommission(vinSum - voutSum);
        return true;
    }

    public void addTransaction(Transaction transaction) {
        TransactionRecord transactionRecord = transactionConverter.fromDTOtoEntity(transaction);
        transactionRepository.save(transactionRecord);
    }

    public TransactionRecord getTransactionRecord(String hash) {
        return transactionRepository.getById(hash);
    }
}
