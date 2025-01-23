package com.uokse.fuelmaster.repository;

import com.uokse.fuelmaster.model.fuelStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelStationRepo extends JpaRepository<fuelStation, Long> {
    boolean existsByRegNo(String regNo);
}