package org.blockchain.repository;

import org.blockchain.entities.FreeVOUTRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeVOUTRepository extends JpaRepository<FreeVOUTRecord, Long> {
}
