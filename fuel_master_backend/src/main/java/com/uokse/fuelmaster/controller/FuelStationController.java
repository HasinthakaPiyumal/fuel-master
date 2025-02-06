package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.FuelStationDTO;
import com.uokse.fuelmaster.dto.Response.OwnerUpdateDTO;
import com.uokse.fuelmaster.model.FuelStation;
import com.uokse.fuelmaster.response.SuccessResponse;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.service.FuelStationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/fuelstation")
@Tag(name = "Fuel Station", description = "Fuel Station API")
@SecurityRequirement(name = "bearerAuth")
public class FuelStationController {

    @Autowired
    private FuelStationService fuelStationService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('STATION_MANAGER','SUPER_ADMIN')")
    public ResponseEntity<?> addFuelStation(@Valid @RequestBody FuelStationDTO fuelStationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Extract validation error messages
            HashMap<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            ErrorResponse errorResponse = new ErrorResponse(400, errors.get(errors.keySet().toArray()[0]));

            return ResponseEntity.badRequest().body(errorResponse);
        }
        try {
            FuelStation fuelStation = fuelStationService.addFuelStation(fuelStationDTO);
            return ResponseEntity.ok(new SuccessResponse("Fuel station added successfully", true, fuelStation));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add fuel station"));
        }
    }

    @PutMapping("/change-owner/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> changeFuelStationOwner(@PathVariable Long id, @Valid @RequestBody OwnerUpdateDTO ownerUpdateDTO, BindingResult bindingResult) {

        // Validate request body
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage));
        }

        try {
            // Call the service layer to update the fuel station owner
            FuelStation updatedFuelStation = fuelStationService.updateFuelStationOwner(id, ownerUpdateDTO.getOwnerId());

            // Return success response
            return ResponseEntity.ok(new SuccessResponse("Fuel station owner updated successfully", true, updatedFuelStation));
        } catch (NoSuchElementException e) {  // If fuel station or owner ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {  // Handle any unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update fuel station owner"));
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('STATION_MANAGER','SUPER_ADMIN')")
    public ResponseEntity<?> getAllFuelStations() {
        List<FuelStation> fuelStations = fuelStationService.getAllFuelStations();
        return ResponseEntity.ok(new SuccessResponse("Fuel stations retrieved successfully", true, fuelStations));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STATION_MANAGER','SUPER_ADMIN')")
    public ResponseEntity<?> getFuelStationById(@PathVariable Long id) {
        try {
            FuelStation fuelStation = fuelStationService.getFuelStationById(id);
            return ResponseEntity.ok(new SuccessResponse("Fuel station retrieved successfully", true, fuelStation));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Fuel station not found"));
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('STATION_MANAGER','SUPER_ADMIN')")
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
