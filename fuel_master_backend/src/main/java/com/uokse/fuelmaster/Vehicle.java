package com.uokse.fuelmaster;

import jakarta.persistence.*;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String vehicleRegistrationPart1;

    @Column(nullable = false)
    private Long vehicleRegistrationPart2;

    @Column(nullable = false)
    private String vehicleType;

    @Column(nullable = false, unique = true)
    private String chassisNumber;

    @Column(nullable = false)
    private String fuelType;

    // Getters and Setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleRegistrationPart1() {
        return vehicleRegistrationPart1;
    }

    public void setVehicleRegistrationPart1(String vehicleRegistrationPart1) {
        this.vehicleRegistrationPart1 = vehicleRegistrationPart1;
    }

    public Long getVehicleRegistrationPart2() {
        return vehicleRegistrationPart2;
    }

    public void setVehicleRegistrationPart2(Long vehicleRegistrationPart2) {
        this.vehicleRegistrationPart2 = vehicleRegistrationPart2;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
