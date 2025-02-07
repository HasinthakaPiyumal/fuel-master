package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.dto.VehicleDTO;
import com.uokse.fuelmaster.model.User;
import com.uokse.fuelmaster.model.Vehicle;

public interface VehicleService {
    String registerVehicle(VehicleDTO vehicleDTO);
    Vehicle get(Long vehicleId);
    Vehicle getByQRId(String qrId);

    Vehicle getByUser(User user);
}