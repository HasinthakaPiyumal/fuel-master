package com.uokse.fuelmaster.repository;

import com.uokse.fuelmaster.model.FuelTransaction;
import com.uokse.fuelmaster.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTransactionRepository extends JpaRepository<FuelTransaction, Long> {

    ScopedValue<Object> findFirstByVehicleOrderByTransactionDateDesc(Vehicle vehicle);
}
