package com.uokse.fuelmaster.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FuelStationDTO {
    private Long id;
    @NotBlank(message = "FuelStation registration Number  is mandatory")
    private String regNo;
    @NotBlank(message = "FuelStation location is mandatory")
    private String location;
    @NotBlank(message = "FuelStation owner name is mandatory")
    private String owner;
    @NotNull(message = "FuelStation employee count is mandatory")
    @Min(value = 1, message = "Employee count must be at least 1")
    private Integer employeeCount;

    public FuelStationDTO(Long id, String regNo, String location, String owner, Integer employeeCount) {
        this.id = id;
        this.regNo = regNo;
        this.location = location;
        this.owner = owner;
        this.employeeCount = employeeCount;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }
}