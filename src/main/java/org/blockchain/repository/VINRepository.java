package org.blockchain.repository;

import org.blockchain.entities.VINRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VINRepository extends JpaRepository<VINRecord, Long> {
}
