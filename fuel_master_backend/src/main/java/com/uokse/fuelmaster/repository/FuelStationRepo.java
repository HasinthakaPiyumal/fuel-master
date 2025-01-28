package com.uokse.fuelmaster.repository;

import com.uokse.fuelmaster.model.fuelStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuelStationRepo extends JpaRepository<fuelStation, Long> {
    boolean existsByRegNo(String regNo);

    Optional<fuelStation> findById(Long id);
}