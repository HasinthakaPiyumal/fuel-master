package com.uokse.fuelmaster.Controller;

import com.uokse.fuelmaster.DTO.VehicleDTO;
import com.uokse.fuelmaster.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/save")
    public ResponseEntity<String> saveVehicle(@RequestBody VehicleDTO vehicleDTO) {
        // Validate required fields
        if (vehicleDTO.getChassisNumber() == null || vehicleDTO.getChassisNumber().isEmpty()) {
            return ResponseEntity.badRequest().body("Chassis number cannot be null or empty.");
        }

        // Register vehicle and return response
        String registrationMessage = vehicleService.registerVehicle(vehicleDTO);

        // If the service returns an error message, return that as the response
        if (registrationMessage.startsWith("Error:")) {
            return ResponseEntity.badRequest().body(registrationMessage);  // Return error message with 400
        }

        // Otherwise, return success message
        return ResponseEntity.ok("Vehicle registered successfully with " + registrationMessage);  // Success
    }
}