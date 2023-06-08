package org.blockchain.services;

import org.blockchain.entities.*;
import org.blockchain.repository.AccountRepository;
import org.blockchain.repository.FreeVOUTRepository;
import org.blockchain.repository.VOUTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private FreeVOUTRepository freeVOUTRepository;
    @Autowired
    private VOUTRepository voutRepository;
    @Autowired
    private BlockService blockService;
    @Autowired
    private TransactionService transactionService;

    private List<Long> getNewFreeVOUTs(BlockRecord newActualBlock) {
        List<Long> result = new ArrayList<>();
        for (TransactionRecord transaction: newActualBlock.getTransactions()) {
            for (VOUTRecord vout : transaction.getVouts()) {
                result.add(vout.getId());
            }
        }
        return result;
    }

    private List<Long> getUsedVOUTs(BlockRecord newActualBlock) {
        List<Long> result = new ArrayList<>();
        for (TransactionRecord transaction: newActualBlock.getTransactions()) {
            for (VINRecord vin : transaction.getVins()) {
                String previousHash = vin.getHash();
                result.add(transactionService.getTransactionRecord(previousHash).getVouts().get(vin.getIndex()).getId());
            }
        }
        return result;
    }

    public void updateAccounts() {
        BlockRecord newActualBlock = blockService.getNewActualBlock();
        System.out.println("Actual block: " + newActualBlock);
        if (newActualBlock != null) {
            List<FreeVOUTRecord> newFreeVOUTs = getNewFreeVOUTs(newActualBlock).stream().map(FreeVOUTRecord::new).toList();
            freeVOUTRepository.saveAll(newFreeVOUTs);
            for (FreeVOUTRecord freeVOUT : newFreeVOUTs) {
                VOUTRecord vout = voutRepository.findById(freeVOUT.getId()).get();
                String address = vout.getAddress();
                AccountRecord accountRecord = new AccountRecord(address, 0l);
                if (accountRepository.existsById(address)) {
                    accountRecord.setValue(accountRepository.findById(address).get().getValue());
                }
                accountRecord.setValue(accountRecord.getValue() + vout.getValue());
                accountRepository.save(accountRecord);
            }
            System.out.println("New vouts are saved");
            List<FreeVOUTRecord> usedVOUTs = getUsedVOUTs(newActualBlock).stream().map(FreeVOUTRecord::new).toList();
            freeVOUTRepository.deleteAll(usedVOUTs);
            System.out.println("Used vouts are removed");
            for (FreeVOUTRecord usedVOUT : usedVOUTs) {
                VOUTRecord vout = voutRepository.findById(usedVOUT.getId()).get();
                String address = vout.getAddress();
                AccountRecord accountRecord = accountRepository.findById(address).get();
                accountRecord.setValue(accountRecord.getValue() - vout.getValue());
                accountRepository.save(accountRecord);
            }
            System.out.println("OOOOK");
        }
    }
}
