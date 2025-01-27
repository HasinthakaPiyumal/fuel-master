package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.VehicleTypeDTO;
import com.uokse.fuelmaster.model.VehicleType;
import com.uokse.fuelmaster.service.AdminService;
import com.uokse.fuelmaster.service.VehicleTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle-types")
public class VehicleTypeController {

    @Autowired
    private VehicleTypeService vehicleTypeService;

    @Autowired
    private AdminService adminService;

    // Add a new vehicle type (Admin-only)
    @PostMapping("/save")
    public ResponseEntity<?> addVehicleType(
            @RequestBody VehicleTypeDTO vehicleType) {

        System.out.println("Vehicle type: " + vehicleType);
        try {
            VehicleType createdVehicleType = vehicleTypeService.addVehicleType(vehicleType);
            return ResponseEntity.ok(createdVehicleType);
        } catch (IllegalArgumentException e) {
            System.err.println("Vehicle type registration failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get all vehicle types
    @GetMapping("/view")
    public ResponseEntity<List<VehicleType>> getAllVehicleTypes() {
        return ResponseEntity.ok(vehicleTypeService.getAllVehicleTypes());
    }

    //Update vehicle types
    @PutMapping("/update/{id}")
    public ResponseEntity<VehicleType> updateVehicleType(
            @PathVariable Long id,
            @RequestBody VehicleType updatedVehicleType) {
        VehicleType vehicleType = vehicleTypeService.updateVehicleType(id, updatedVehicleType);
        return ResponseEntity.ok(vehicleType);
    }



}