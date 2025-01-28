package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.service.FuelTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class FuelTransactionController {


        private final FuelTransactionService fuelTransactionService;

        public FuelTransactionController(FuelTransactionService fuelTransactionService) {
            this.fuelTransactionService = fuelTransactionService;
        }

        @PostMapping("/add")
        public ResponseEntity<String> addTransaction(@RequestParam Long vehicleId,
                                                     @RequestParam Long employeeId,
                                                     @RequestParam Long fuelStationId,
                                                     @RequestParam Double pumpedQuantity) {
            try {
                String result = fuelTransactionService.addFuelTransaction(vehicleId, employeeId, fuelStationId, pumpedQuantity);
                return ResponseEntity.ok(result);
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }


