package org.blockchain.repository;

import org.blockchain.entities.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionRecord, String> {
}
