package com.uokse.fuelmaster.dto;

import com.uokse.fuelmaster.model.FuelType;

public class VehicleTypeDTO {
    private Long id;
    private String vehicleType;
    private FuelType fuelType;
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
