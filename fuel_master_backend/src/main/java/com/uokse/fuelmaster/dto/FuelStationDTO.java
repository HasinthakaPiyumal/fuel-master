package com.uokse.fuelmaster.dto;

public class FuelStationDTO {
    private Long id;
    private String regNo;
    private String location;
    private String owner;
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