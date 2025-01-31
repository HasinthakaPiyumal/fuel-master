package com.uokse.fuelmaster.repository;

import com.uokse.fuelmaster.model.FuelQuota;
import com.uokse.fuelmaster.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelQuotaRepository extends JpaRepository<FuelQuota, Long> {

    // Find a FuelQuota by Vehicle
    FuelQuota findByVehicle_Id(Long vehicleId);

    // Find all FuelQuotas for a specific Vehicle
    List<FuelQuota> findByVehicle(Vehicle vehicle);

    // Find all FuelQuotas for a specific User
    List<FuelQuota> findByVehicle_User_Id(Long userId);

    // Delete FuelQuotas by Vehicle ID
     void deleteByVehicleId(Long vehicleId);

    // Find a FuelQuota by Vehicle QR ID
    FuelQuota findByVehicleQrId(String qrId);
}
