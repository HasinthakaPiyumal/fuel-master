package com.uokse.fuelmaster.service.impl;

import com.uokse.fuelmaster.dto.VehicleDTO;
import com.uokse.fuelmaster.model.User;
import com.uokse.fuelmaster.model.Vehicle;
import com.uokse.fuelmaster.model.VehicleType;
import com.uokse.fuelmaster.repository.MotorTrafficMockRepo;
import com.uokse.fuelmaster.repository.UserRepo;
import com.uokse.fuelmaster.repository.VehicleRepo;
import com.uokse.fuelmaster.repository.VehicleTypeRepository;
import com.uokse.fuelmaster.service.VehicleService;

import jakarta.transaction.Transactional;
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
    private VehicleTypeRepository vehicleTypeRepo;

    @Autowired
    private MotorTrafficMockRepo motorTrafficMockRepo;


    private String generateUniqueQRId(String chassisNumber, String registrationNumber) {
        // Combine chassis number and registration number with timestamp for uniqueness
        String timestamp = String.valueOf(System.currentTimeMillis());
        String combined = chassisNumber + registrationNumber + timestamp;
        
        // Generate a hash of the combined string
        return "QR" + Math.abs(combined.hashCode());
    }

    @Transactional
    public String registerVehicle(VehicleDTO vehicleDTO) {
        logger.info("Starting vehicle registration...");

        if (!motorTrafficMockRepo.isVehicleRegistered(vehicleDTO.getChassisNumber())) {
            logger.error("Vehicle not registered in Motor Traffic Department.");
            return "Error: This vehicle is not registered with the Motor Traffic Department.";
        }

        if (vehicleRepo.findByChassisNumber(vehicleDTO.getChassisNumber()).isPresent()) {
            logger.error("Chassis number already exists: {}", vehicleDTO.getChassisNumber());
            return "Error: Chassis number already exists in the system.";
        }

        Optional<Vehicle> existingVehicle = vehicleRepo.findByVehicleRegistrationPart1AndVehicleRegistrationPart2(
                vehicleDTO.getVehicleRegistrationPart1(),
                vehicleDTO.getVehicleRegistrationPart2()
        );

        if (existingVehicle.isPresent()) {
            logger.error("Vehicle registration already exists: {}{}", vehicleDTO.getVehicleRegistrationPart1(), vehicleDTO.getVehicleRegistrationPart2());
            return "Error: Vehicle registration already exists in the system.";
        }

        User user = userRepo.findById(vehicleDTO.getUserId()).orElse(null);
        VehicleType vehicleType = vehicleTypeRepo.findByVehicleTypeAndFuelType(vehicleDTO.getVehicleType(), vehicleDTO.getFuelType()).orElse(null);

        if (user == null || vehicleType == null) {
            logger.error("Invalid user or vehicle type information. User: {}, VehicleType: {}", user, vehicleType);
            return "Error: Invalid user or vehicle type information.";
        }

        String qrId = generateUniqueQRId(
            vehicleDTO.getChassisNumber(), 
            vehicleDTO.getVehicleRegistrationPart1() + vehicleDTO.getVehicleRegistrationPart2()
        );

        Vehicle vehicle = new Vehicle(
                vehicleDTO.getId(),
                user,
                vehicleType,
                vehicleDTO.getVehicleRegistrationPart1(),
                vehicleDTO.getVehicleRegistrationPart2(),
                vehicleDTO.getChassisNumber(),
                qrId
        );

        logger.info("Saving vehicle to the database: {}", vehicle);
        vehicleRepo.save(vehicle);

        logger.info("Vehicle registered successfully: {}", vehicle.getChassisNumber());
        return vehicle.getVehicleRegistrationPart1() + vehicle.getVehicleRegistrationPart2();
    }

}