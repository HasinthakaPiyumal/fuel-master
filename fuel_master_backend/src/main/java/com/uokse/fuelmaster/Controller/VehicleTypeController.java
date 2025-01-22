package com.uokse.fuelmaster.Controller;

import com.uokse.fuelmaster.Entity.VehicleType;
import com.uokse.fuelmaster.Service.impl.AdminService;
import com.uokse.fuelmaster.Service.impl.VehicleTypeService;
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
            @RequestHeader("admin-id") Long adminId, // Pass Admin ID in the header
            @RequestBody VehicleType vehicleType) {

        // Validate admin existence
        if (!adminService.isAdmin(adminId)) {
            return ResponseEntity.status(403).body("Forbidden: Admin privileges required.");
        }

        try {
            VehicleType createdVehicleType = vehicleTypeService.addVehicleType(vehicleType);
            return ResponseEntity.ok(createdVehicleType);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get all vehicle types
    @GetMapping("/view")
    public ResponseEntity<List<VehicleType>> getAllVehicleTypes() {
        return ResponseEntity.ok(vehicleTypeService.getAllVehicleTypes());
    }
}