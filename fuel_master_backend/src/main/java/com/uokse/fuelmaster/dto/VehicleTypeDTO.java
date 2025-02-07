package com.uokse.fuelmaster.dto;

import com.uokse.fuelmaster.model.FuelType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleTypeDTO {
    @Schema(hidden = true)
    private Long id;

    @NotBlank(message = "Vehicle type is mandatory")
    private String vehicleType;
    @NotNull(message = "Fuel type is mandatory")
    private FuelType fuelType;
    @NotNull(message = "Default quota is required")
    private Long defaultQuota;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    public FuelType getFuelType() {
        return fuelType;
    }
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
    public Long getDefaultQuota() {
        return defaultQuota;
    }
    public void setDefaultQuota(Long defaultQuota) {
        this.defaultQuota = defaultQuota;
    }
    public VehicleTypeDTO(Long id, String vehicleType, FuelType fuelType, Long defaultQuota) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.defaultQuota = defaultQuota;
    }
}
