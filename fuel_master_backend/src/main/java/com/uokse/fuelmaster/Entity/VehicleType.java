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
    private String fuelType;

    @Column(nullable = false)
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

    public Long getFuelQuantity() {
        return defaultQuota;
    }

    public void setFuelQuantity(Long fuelQuantity) {
        this.defaultQuota = fuelQuantity;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
