package com.uokse.fuelmaster.Entity;

import jakarta.persistence.*;

@Entity
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vehicleType;

    @Column(nullable = false)
    private Long fuelQuantity;

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

    public Long getFuelQuantity() {
        return fuelQuantity;
    }

    public void setFuelQuantity(Long fuelQuantity) {
        this.fuelQuantity = fuelQuantity;
    }
}
