package org.blockchain.converters;

import org.blockchain.dtos.SignatureDTO;
import org.blockchain.dtos.Transaction;
import org.blockchain.dtos.VIN;
import org.blockchain.dtos.VOUT;
import org.blockchain.entities.TransactionRecord;
import org.blockchain.entities.VINRecord;
import org.blockchain.entities.VOUTRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionConverterImpl implements TransactionConverter {

    @Override
    public TransactionRecord fromDTOtoEntity(Transaction transaction) {
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setHash(transaction.genHash());
        List<VINRecord> vinRecords = new ArrayList<>();
        for (VIN vin : transaction.getVins()) {
            SignatureDTO signatureDTO = vin.getSignature();
            vinRecords.add(new VINRecord(null, vin.getHash(), vin.getIndex(), signatureDTO.getSignature(), signatureDTO.getKey()));
        }
        transactionRecord.setVins(vinRecords);
        List<VOUTRecord>  voutRecords = new ArrayList<>();
        for (VOUT vout : transaction.getVouts()) {
            voutRecords.add(new VOUTRecord(null, vout.getValue(), vout.getAddress()));
        }
        transactionRecord.setVouts(voutRecords);
        return transactionRecord;
    }

    @Override
    public Transaction fromEntityToDTO(TransactionRecord transactionRecord) {
        Transaction transaction = new Transaction();
        List<VIN> vins = new ArrayList<>();
        for (VINRecord vinRecord : transactionRecord.getVins()) {
            vins.add(new VIN(vinRecord.getHash(), vinRecord.getIndex(), new SignatureDTO(vinRecord.getSignature(), vinRecord.getKey())));
        }
        transaction.setVins(vins);
        List<VOUT> vouts = new ArrayList<>();
        for (VOUTRecord voutRecord : transactionRecord.getVouts()) {
            vouts.add(new VOUT(voutRecord.getValue(), voutRecord.getAddress()));
        }
        transaction.setVouts(vouts);
        return transaction;
    }
}
