package com.uokse.fuelmaster.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FuelStationDTO {
    @Schema(hidden = true)
    private Long id;
    @NotBlank(message = "FuelStation registration Number  is mandatory")
    private String regNo;
    @NotBlank(message = "FuelStation location is mandatory")
    private String location;
    @NotBlank(message = "FuelStation owner Id is mandatory")
    private Long ownerId;


    public FuelStationDTO(Long id, String regNo, String location, Long ownerId) {
        this.id = id;
        this.regNo = regNo;
        this.location = location;
        this.ownerId = ownerId;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}