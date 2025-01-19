package com.uokse.fuelmaster.Repo;

import com.uokse.fuelmaster.Entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepo extends JpaRepository<VehicleType, Long> {


}
