package org.blockchain.repository;

import org.blockchain.entities.BlockRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<BlockRecord, String> {
}
