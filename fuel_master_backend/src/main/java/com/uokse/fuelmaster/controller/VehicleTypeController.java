package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.VehicleTypeDTO;
import com.uokse.fuelmaster.model.VehicleType;
import com.uokse.fuelmaster.response.SuccessResponse;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle-types")
public class VehicleTypeController {

    @Autowired
    private VehicleTypeService vehicleTypeService;

    @PostMapping("/add")
    public ResponseEntity<?> addVehicleType(@RequestBody VehicleTypeDTO vehicleTypeDTO) {
        try {
            VehicleType savedVehicleType = vehicleTypeService.addVehicleType(vehicleTypeDTO);
            return ResponseEntity.ok(new SuccessResponse("Vehicle type added successfully", true, savedVehicleType));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add vehicle type"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVehicleType(@PathVariable Long id, @RequestBody VehicleType vehicleType) {
        try {
            VehicleType updatedVehicleType = vehicleTypeService.updateVehicleType(id, vehicleType);
            return ResponseEntity.ok(new SuccessResponse("Vehicle type updated successfully", true, updatedVehicleType));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping("/view")
    public ResponseEntity<?> getAllVehicleTypes() {
        List<VehicleType> vehicleTypes = vehicleTypeService.getAllVehicleTypes();
        return ResponseEntity.ok(new SuccessResponse("Vehicle types retrieved successfully", true, vehicleTypes));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> getVehicleTypeById(@PathVariable Long id) {
        try {
            VehicleType vehicleType = vehicleTypeService.getVehicleTypeById(id);
            return ResponseEntity.ok(new SuccessResponse("Vehicle type retrieved successfully", true, vehicleType));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVehicleType(@PathVariable Long id) {
        try {
            vehicleTypeService.deleteVehicleType(id);
            return ResponseEntity.ok(new SuccessResponse("Vehicle type deleted successfully", true, null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
