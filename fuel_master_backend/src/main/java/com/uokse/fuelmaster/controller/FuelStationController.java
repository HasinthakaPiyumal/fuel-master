package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.FuelStationDTO;
import com.uokse.fuelmaster.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fuelstation")
public class FuelStationController {

    @Autowired
    private FuelStationService fuelStationService;

    @PostMapping("/save")
    public ResponseEntity<String> registerFuelStation(@RequestBody FuelStationDTO fuelStationDTO) {
        String response = fuelStationService.registerFuelStation(fuelStationDTO);
        if (response.contains("Error")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    // New endpoint to get all fuel stations
    @GetMapping("/all")
    public ResponseEntity<List<FuelStationDTO>> getAllFuelStations() {
        return ResponseEntity.ok(fuelStationService.getAllFuelStations());
    }

    // New endpoint to get a single fuel station by ID
    @GetMapping("/{id}")
    public ResponseEntity<FuelStationDTO> getFuelStationById(@PathVariable Long id) {
        return ResponseEntity.ok(fuelStationService.getFuelStationById(id));
    }
}
