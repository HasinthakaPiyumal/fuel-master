package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.FuelStationDTO;
import com.uokse.fuelmaster.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
