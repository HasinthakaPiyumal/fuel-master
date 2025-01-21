package com.uokse.fuelmaster.Repo;

import com.uokse.fuelmaster.Entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    Optional<VehicleType> findByVehicleTypeAndFuelType(String vehicleType, String fuelType);
}
