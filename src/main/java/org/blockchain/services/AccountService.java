package org.blockchain.services;

import org.blockchain.entities.*;
import org.blockchain.repository.AccountRepository;
import org.blockchain.repository.FreeVOUTRepository;
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
        if (newActualBlock != null) {
            List<FreeVOUTRecord> newFreeVOUTs = getNewFreeVOUTs(newActualBlock).stream().map(FreeVOUTRecord::new).toList();
            freeVOUTRepository.saveAll(newFreeVOUTs);
            List<FreeVOUTRecord> usedVOUTs = getUsedVOUTs(newActualBlock).stream().map(FreeVOUTRecord::new).toList();
            freeVOUTRepository.deleteAll(usedVOUTs);

        }
    }
}
