package com.uokse.fuelmaster.DTO;

import jakarta.persistence.Column;


public class VehicleDTO {

    private Long id;

    @Column(nullable = false)
    private Long userId;

   @Column(nullable = false)
    private String vehicleType;

  @Column(nullable = false)
    private String vehicleRegistrationPart1;

   @Column(nullable = false)
    private Long vehicleRegistrationPart2;

   @Column(nullable = false,unique = true)
    private String chassisNumber;

   @Column(nullable = false)
    private String fuelType;

    // Constructors, Getters, and Setters...

    public VehicleDTO() {
    }

    public VehicleDTO(Long id, Long userId, String vehicleType, String vehicleRegistrationPart1,
                      Long vehicleRegistrationPart2, String chassisNumber, String fuelType) {
        this.id = id;
        this.userId = userId;
        this.vehicleType = vehicleType;
        this.vehicleRegistrationPart1 = vehicleRegistrationPart1;
        this.vehicleRegistrationPart2 = vehicleRegistrationPart2;
        this.chassisNumber = chassisNumber;
        this.fuelType = fuelType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
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
