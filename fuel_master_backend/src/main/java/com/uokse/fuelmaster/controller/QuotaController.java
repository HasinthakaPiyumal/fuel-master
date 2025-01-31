package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.FuelTransactionDTO;
import com.uokse.fuelmaster.dto.QuotaSummaryDTO;
import com.uokse.fuelmaster.model.FuelQuota;
import com.uokse.fuelmaster.model.Vehicle;
import com.uokse.fuelmaster.repository.FuelQuotaRepository;
import com.uokse.fuelmaster.repository.VehicleRepo;
import com.uokse.fuelmaster.repository.VehicleTypeRepository;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/quota")
public class QuotaController {

    @Autowired
    private FuelQuotaRepository fuelQuotaRepository;

    @Autowired
    private VehicleRepo vehicleRepository;

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

    @GetMapping("/vehicle/{qrId}")
    public ResponseEntity getQuotaSummaryByQRId(@PathVariable String qrId) {
        if (qrId == null || qrId.isEmpty()) {
            return ResponseEntity.badRequest().body(new QuotaSummaryDTO("Invalid QR ID"));
        }
        Vehicle vehicle = vehicleRepository.findByQrId(qrId);
        if(vehicle == null) {
            ErrorResponse errorResponse = new ErrorResponse(404,"The vehicle with the given QR ID does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
        Optional<FuelQuota> quota = fuelQuotaRepository.findById(vehicle.getId());
        if (quota.isPresent()) {
            FuelQuota fuelQuota = quota.get();
            System.out.println(fuelQuota);
            SuccessResponse successResponse = new SuccessResponse("Fuel Quota Found",true,  fuelQuota);
            return ResponseEntity.ok(successResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new QuotaSummaryDTO("Fuel quota not found for QR ID: " + qrId));
    }

    @PostMapping("/use")
    public ResponseEntity useQuota(@RequestBody FuelTransactionDTO fuelTransactionDTO) {

        Optional<FuelQuota> quota = Optional.ofNullable(fuelQuotaRepository.findByVehicle_Id(fuelTransactionDTO.getVehicleId()));

        if (quota.isPresent()) {
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new QuotaSummaryDTO("Fuel quota not found for vehicle ID: " + fuelTransactionDTO.getVehicleId()));
    }
}
