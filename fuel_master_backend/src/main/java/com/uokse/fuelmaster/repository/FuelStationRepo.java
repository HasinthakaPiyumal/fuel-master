package com.uokse.fuelmaster.repository;

import com.uokse.fuelmaster.model.FuelStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuelStationRepo extends JpaRepository<FuelStation, Long> {
    boolean existsByRegNo(String regNo);

    Optional<FuelStation> findById(Long id);
}