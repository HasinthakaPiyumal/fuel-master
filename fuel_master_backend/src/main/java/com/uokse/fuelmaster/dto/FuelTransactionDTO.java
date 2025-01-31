package com.uokse.fuelmaster.dto;

public class FuelTransactionDTO {
    private Long vehicleId;
    private double pumpedQuantity;

    public FuelTransactionDTO(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public double getPumpedQuantity() {
        return pumpedQuantity;
    }

    public void setPumpedQuantity(double pumpedQuantity) {
        this.pumpedQuantity = pumpedQuantity;
    }
}
