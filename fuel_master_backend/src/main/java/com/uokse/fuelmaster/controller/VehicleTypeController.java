package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.Response.DefaultQuotaDTO;
import com.uokse.fuelmaster.dto.VehicleTypeDTO;
import com.uokse.fuelmaster.model.VehicleType;
import com.uokse.fuelmaster.response.SuccessResponse;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.service.VehicleTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/vehicle-types")
@Tag(name = "Vehicle Type", description = "Vehicle Type API")
@SecurityRequirement(name = "bearerAuth")
public class VehicleTypeController {

    @Autowired
    private VehicleTypeService vehicleTypeService;


    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> addVehicleType(@Valid @RequestBody VehicleTypeDTO vehicleTypeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Define the expected field order
            List<String> fieldOrder = Arrays.asList("vehicleType", "fuelType", "defaultQuota");

            // Get the first occurring field error based on the expected order
            for (String field : fieldOrder) {
                Optional<String> errorMessage = bindingResult.getFieldErrors().stream()
                        .filter(error -> error.getField().equals(field))
                        .map(FieldError::getDefaultMessage)
                        .findFirst();

                if (errorMessage.isPresent()) {
                    ErrorResponse errorResponse = new ErrorResponse(400, errorMessage.get());
                    return ResponseEntity.badRequest().body(errorResponse);
                }
            }
        }

        try {
            VehicleType savedVehicleType = vehicleTypeService.addVehicleType(vehicleTypeDTO);
            return ResponseEntity.ok(new SuccessResponse("Vehicle type added successfully", true, savedVehicleType));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add vehicle type"));
        }
    }

    @PutMapping("/change-quota/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> changeQuota(@PathVariable Long id, @Valid @RequestBody DefaultQuotaDTO defaultQuotaDTO, BindingResult bindingResult) {

        // Validate request body
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage));
        }

        try {
            // Call the service layer to update the default quota
            VehicleType updatedVehicleType = vehicleTypeService.updateDefaultQuota(id, defaultQuotaDTO.getDefaultQuota());

            // Return success response
            return ResponseEntity.ok(new SuccessResponse("Default quota updated successfully", true, updatedVehicleType));
        } catch (NoSuchElementException e) {  // If vehicle type ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Vehicle type not found"));
        } catch (Exception e) {  // Handle any unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update default quota"));
        }
    }


    @GetMapping("/view")
    public ResponseEntity<?> getAllVehicleTypes() {
        List<VehicleType> vehicleTypes = vehicleTypeService.getAllVehicleTypes();
        return ResponseEntity.ok(new SuccessResponse("Vehicle types retrieved successfully", true, vehicleTypes));
    }

    @GetMapping("/view/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
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
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
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
