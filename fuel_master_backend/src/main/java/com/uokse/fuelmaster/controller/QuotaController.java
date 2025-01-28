package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.QuotaSummaryDTO;
import com.uokse.fuelmaster.model.FuelQuota;
import com.uokse.fuelmaster.repository.FuelQuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/quota")
public class QuotaController {

    @Autowired
    private FuelQuotaRepository fuelQuotaRepository;

    @GetMapping("/summary/{vehicleId}")
    public ResponseEntity<QuotaSummaryDTO> getQuotaSummary(@PathVariable Long vehicleId) {
        // Validation
        if (vehicleId == null || vehicleId <= 0) {
            return ResponseEntity.badRequest().body(new QuotaSummaryDTO("Invalid vehicle ID"));
        }

        Optional<FuelQuota> quota = Optional.ofNullable(fuelQuotaRepository.findByVehicle_Id(vehicleId));

        if (quota.isPresent()) {
            FuelQuota fuelQuota = quota.get();
            QuotaSummaryDTO summary = new QuotaSummaryDTO(
                    fuelQuota.getDefaultQuota(),
                    fuelQuota.getUsedQuota(),
                    fuelQuota.getAvailableQuota(),
                    fuelQuota.getQuotaDate().plusDays(7) // Example: Assuming weekly renewal
            );
            return ResponseEntity.ok(summary);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new QuotaSummaryDTO("Fuel quota not found for vehicle ID: " + vehicleId));
    }
}
