package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.VehicleDTO;
import com.uokse.fuelmaster.dto.VehicleInfoDTO;
import com.uokse.fuelmaster.model.User;
import com.uokse.fuelmaster.model.Vehicle;
import com.uokse.fuelmaster.model.VehicleType;
import com.uokse.fuelmaster.service.impl.VehicleIMPL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/vehicle")
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleIMPL vehicleIMPL;


    @PostMapping("/save")
    public ResponseEntity<String> saveVehicle( @RequestBody VehicleDTO vehicleDTO) {
        logger.info("Received request to save vehicle: {}", vehicleDTO);

        try {
            // Call the service to register the vehicle
            String registrationMessage = vehicleIMPL.registerVehicle(vehicleDTO);

            // Handle error messages returned by the service
            if (registrationMessage.startsWith("Error:")) {
                logger.error("Vehicle registration failed: {}", registrationMessage);
                return ResponseEntity.badRequest().body(registrationMessage); // Return 400 with error message
            }

            // Return success message
            logger.info("Vehicle registered successfully: {}", registrationMessage);
            return ResponseEntity.ok("Vehicle registered successfully with " + registrationMessage);
        } catch (Exception e) {
            // Log and handle unexpected errors
            logger.error("An error occurred while saving the vehicle: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }


    }

    @GetMapping("/{vehicleId}/info")
    public ResponseEntity<VehicleInfoDTO> getVehicleInfo(@PathVariable Long vehicleId) {
        logger.info("Received request to get vehicle info for vehicle ID: {}", vehicleId);
        Vehicle vehicle = vehicleIMPL.getVehicleInfo(vehicleId);

        if (vehicle == null) {
            logger.error("No vehicle found for ID: {}", vehicleId);
            return ResponseEntity.notFound().build();
        }

        User user = vehicle.getUser ();
        VehicleType vehicleType = vehicle.getVehicleType();

        VehicleInfoDTO vehicleInfo = new VehicleInfoDTO();
        vehicleInfo.setUserFullName(user.getFirstName() + " " + user.getLastName());
        vehicleInfo.setUserPhone(user.getPhone());
        vehicleInfo.setUserNic(user.getNic());
        vehicleInfo.setVehicleNumber(vehicle.getVehicleRegistrationPart1() + vehicle.getVehicleRegistrationPart2());
        vehicleInfo.setVehicleType(vehicleType.getVehicleType());
        vehicleInfo.setFuelType(vehicleType.getFuelType().name()); // Assuming FuelType is an enum
        vehicleInfo.setChassisNumber(vehicle.getChassisNumber());

        logger.info("Vehicle info retrieved successfully for vehicle ID: {}", vehicleId);
        return ResponseEntity.ok(vehicleInfo);
    }


}

