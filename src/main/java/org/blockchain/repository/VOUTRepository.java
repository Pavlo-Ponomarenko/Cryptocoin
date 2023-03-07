package org.blockchain.repository;

import org.blockchain.entities.VOUTRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VOUTRepository extends JpaRepository<VOUTRecord, Long> {
}
