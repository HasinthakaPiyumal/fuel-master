package com.uokse.fuelmaster.dto;

public class VehicleDTO {
    private Long id;
    private Long userId;  // This should be the User's ID
    private String vehicleType;  // This should be the VehicleType's ID
    private String vehicleRegistrationPart1;
    private Long vehicleRegistrationPart2;
    private String chassisNumber;
    private String fuelType;

    // Getters and setters for each field


    public VehicleDTO(Long id, Long userId, String vehicleTypeName, String vehicleRegistrationPart1, Long vehicleRegistrationPart2, String chassisNumber, String fuelType) {
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

    public void setVehicleTypeId(String vehicleType) {
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