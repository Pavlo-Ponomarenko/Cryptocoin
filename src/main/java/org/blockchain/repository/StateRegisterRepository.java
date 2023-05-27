package org.blockchain.repository;

import org.blockchain.entities.StateRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRegisterRepository extends JpaRepository<StateRegister, String> {
}
