package com.uokse.fuelmaster.Service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uokse.fuelmaster.DTO.VehicleDTO;
import com.uokse.fuelmaster.Entity.User;
import com.uokse.fuelmaster.Entity.Vehicle;
import com.uokse.fuelmaster.Entity.VehicleType;
import com.uokse.fuelmaster.Repo.UserRepo;
import com.uokse.fuelmaster.Repo.VehicleRepo;
import com.uokse.fuelmaster.Repo.VehicleTypeRepo;
import com.uokse.fuelmaster.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleIMPL implements VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleIMPL.class);

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VehicleTypeRepo vehicleTypeRepo;

    @Override
    public String registerVehicle(VehicleDTO vehicleDTO) {
        logger.info("Starting vehicle registration...");

        // Check if chassis number exists
        if (vehicleRepo.findByChassisNumber(vehicleDTO.getChassisNumber()).isPresent()) {
            return "Chassis number already exists";
        }

        // Retrieve User and VehicleType
        User user = userRepo.findById(vehicleDTO.getUserId()).orElse(null);
        VehicleType vehicleType = vehicleTypeRepo.findById(vehicleDTO.getVehicleTypeId()).orElse(null);

        // If either User or VehicleType is null, return failure message
        if (user == null || vehicleType == null) {
            return "Invalid user or vehicle type information";
        }

        // Create and save the vehicle
        Vehicle vehicle = new Vehicle(
                vehicleDTO.getId(),
                user,
                vehicleType,
                vehicleDTO.getVehicleRegistrationPart1(),
                vehicleDTO.getVehicleRegistrationPart2(),
                vehicleDTO.getChassisNumber(),
                vehicleDTO.getFuelType()
        );

        vehicleRepo.save(vehicle);
        logger.info("Vehicle registered successfully: {}", vehicle.getChassisNumber());

        return vehicle.getVehicleRegistrationPart1() + vehicle.getVehicleRegistrationPart2();
    }
}
