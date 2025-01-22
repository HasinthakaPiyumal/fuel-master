package com.uokse.fuelmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uokse.fuelmaster.model.VehicleType;

import java.util.Optional;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    Optional<VehicleType> findByVehicleTypeAndFuelType(String vehicleType, String fuelType);
}
