package com.uokse.fuelmaster.Service.impl;

import com.uokse.fuelmaster.DTO.VehicleDTO;
import com.uokse.fuelmaster.Entity.User;
import com.uokse.fuelmaster.Entity.Vehicle;
import com.uokse.fuelmaster.Entity.VehicleType;
import com.uokse.fuelmaster.Repo.MotorTrafficMockRepo;
import com.uokse.fuelmaster.Repo.UserRepo;
import com.uokse.fuelmaster.Repo.VehicleRepo;
import com.uokse.fuelmaster.Repo.VehicleTypeRepo;
import com.uokse.fuelmaster.Service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleIMPL implements VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleIMPL.class);

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VehicleTypeRepo vehicleTypeRepo;

    @Autowired
    private MotorTrafficMockRepo motorTrafficMockRepo;

    @Override
    public String registerVehicle(VehicleDTO vehicleDTO) {
        logger.info("Starting vehicle registration...");

        // Check if chassis number exists in Motor Traffic Department
        if (!motorTrafficMockRepo.isVehicleRegistered(vehicleDTO.getChassisNumber())) {
            return "Error: This vehicle is not registered with the Motor Traffic Department.";
        }

        // Check if chassis number already exists in your system
        if (vehicleRepo.findByChassisNumber(vehicleDTO.getChassisNumber()).isPresent()) {
            return "Error: Chassis number already exists in the system.";
        }

        // Check if the vehicle registration (Part1 + Part2) already exists
        Optional<Vehicle> existingVehicle = vehicleRepo.findByVehicleRegistrationPart1AndVehicleRegistrationPart2(
                vehicleDTO.getVehicleRegistrationPart1(),
                vehicleDTO.getVehicleRegistrationPart2()
        );

        if (existingVehicle.isPresent()) {
            return "Error: Vehicle registration already exists in the system.";
        }

        // Retrieve User and VehicleType
        User user = userRepo.findById(vehicleDTO.getUserId()).orElse(null);
        VehicleType vehicleType = vehicleTypeRepo.findById(vehicleDTO.getVehicleTypeId()).orElse(null);

        // If either User or VehicleType is null, return failure message
        if (user == null || vehicleType == null) {
            return "Error: Invalid user or vehicle type information.";
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

        return  vehicle.getVehicleRegistrationPart1() + vehicle.getVehicleRegistrationPart2();
    }
}
