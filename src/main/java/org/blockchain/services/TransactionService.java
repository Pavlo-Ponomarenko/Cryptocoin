package org.blockchain.services;

import org.blockchain.converters.TransactionConverter;
import org.blockchain.dtos.SignatureDTO;
import org.blockchain.dtos.Transaction;
import org.blockchain.dtos.VIN;
import org.blockchain.dtos.VOUT;
import org.blockchain.entities.TransactionRecord;
import org.blockchain.entities.VOUTRecord;
import org.blockchain.repository.FreeVOUTRepository;
import org.blockchain.repository.TransactionRepository;
import org.blockchain.repository.VINRepository;
import org.blockchain.repository.VOUTRepository;
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
    private VINRepository vinRepository;
    @Autowired
    private VOUTRepository voutRepository;
    @Autowired
    private FreeVOUTRepository freeVOUTRepository;
    @Autowired
    private TransactionConverter transactionConverter;

    public boolean validate(Transaction transaction) {
        long vinSum = 0L;
        for (VIN vin : transaction.getVins()) {
            SignatureDTO signature = vin.getSignature();
            if (!Signing.verify(signature.getSignature(), transaction.genHash(), signature.getKey())) {
                System.out.println("Verification signature failed");
                return false;
            }
            Optional<TransactionRecord> previousTransactionRecord = transactionRepository.findById(vin.getHash());
            if (previousTransactionRecord.isEmpty()) {
                System.out.println("Previous transaction was not found");
                return false;
            }
            TransactionRecord previousTransaction = previousTransactionRecord.get();
            List<VOUTRecord> previousVouts = previousTransaction.getVouts();
            Integer previousVoutIndex = vin.getIndex();
            if (previousVouts.size() <= previousVoutIndex) {
                System.out.println("Fault vin index: " + previousVouts.size() + " " + previousVoutIndex);
                return false;
            }
            VOUTRecord previousVout = previousVouts.get(previousVoutIndex);
            if (!previousVout.getAddress().equals(signature.getKey())) {
                System.out.println("Fault address");
                return false;
            }
            if (!freeVOUTRepository.existsById(previousVout.getId())) {
                System.out.println("Coin cannot be used");
                return false;
            }
            vinSum += previousVout.getValue();
        }
        long voutSum = 0l;
        for (VOUT vout : transaction.getVouts()) {
            voutSum += vout.getValue();
        }
        if (voutSum > vinSum) {
            return false;
        }
        System.out.println("Transaction is correct");
        transaction.setCommission(vinSum - voutSum);
        return true;
    }

    public void addTransaction(TransactionRecord transactionRecord, String blockHash) {
        vinRepository.saveAll(transactionRecord.getVins());
        for (VOUTRecord voutRecord : transactionRecord.getVouts()) {
            voutRecord.setTransactionHash(transactionRecord.getHash());
            voutRepository.save(voutRecord);
        }
        transactionRecord.setBlockHash(blockHash);
        transactionRepository.save(transactionRecord);
    }

    public void addAll(List<TransactionRecord> transactionRecords, String blockHash) {
        for (TransactionRecord transactionRecord : transactionRecords) {
            addTransaction(transactionRecord, blockHash);
        }
    }

    public void addTransaction(Transaction transaction, String blockHash) {
        TransactionRecord transactionRecord = transactionConverter.fromDTOtoEntity(transaction);
        addTransaction(transactionRecord, blockHash);
    }

    public TransactionRecord getTransactionRecord(String hash) {
        return transactionRepository.getById(hash);
    }
}
