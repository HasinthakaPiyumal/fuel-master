package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.FuelStationDTO;
import com.uokse.fuelmaster.model.FuelStation;
import com.uokse.fuelmaster.response.SuccessResponse;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.service.FuelStationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/fuelstation")
public class FuelStationController {

    @Autowired
    private FuelStationService fuelStationService;

    // Add Fuel Station
    @PostMapping("/save")
    public ResponseEntity<?> addFuelStation(@Valid @RequestBody FuelStationDTO fuelStationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Extract validation error messages
            HashMap<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errors);
        }
        try {
            FuelStation fuelStation = fuelStationService.addFuelStation(fuelStationDTO);
            return ResponseEntity.ok(new SuccessResponse("Fuel station added successfully", true, fuelStation));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add fuel station"));
        }
    }

    // Update Fuel Station
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFuelStation(@PathVariable Long id, @RequestBody FuelStationDTO fuelStationDTO) {
        try {
            FuelStation updatedFuelStation = fuelStationService.updateFuelStation(id, fuelStationDTO);
            return ResponseEntity.ok(new SuccessResponse("Fuel station updated successfully", true, updatedFuelStation));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Fuel station not found"));
        }
    }

    // View all Fuel Stations
    @GetMapping("/all")
    public ResponseEntity<?> getAllFuelStations() {
        List<FuelStation> fuelStations = fuelStationService.getAllFuelStations();
        return ResponseEntity.ok(new SuccessResponse("Fuel stations retrieved successfully", true, fuelStations));
    }

    // View a specific Fuel Station by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFuelStationById(@PathVariable Long id) {
        try {
            FuelStation fuelStation = fuelStationService.getFuelStationById(id);
            return ResponseEntity.ok(new SuccessResponse("Fuel station retrieved successfully", true, fuelStation));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Fuel station not found"));
        }
    }
    // Delete Fuel Station
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFuelStation(@PathVariable Long id) {
        try {
            fuelStationService.deleteFuelStation(id);
            return ResponseEntity.ok(new SuccessResponse("Fuel station deleted successfully", true, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Fuel station not found"));
        }
    }
}
