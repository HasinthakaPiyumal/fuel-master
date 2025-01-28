package com.uokse.fuelmaster.repository;

import com.uokse.fuelmaster.model.FuelTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTransactionRepository extends JpaRepository<FuelTransaction, Long> {

}
