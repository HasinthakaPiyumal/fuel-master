package com.uokse.fuelmaster.model;

import jakarta.persistence.*;


@Entity

public class fuelStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String regNo;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private Integer employeeCount;

    @Column(updatable = false)
    private String createdAt;

    private String updatedAt;

    public fuelStation(Long id, String regNo, String location, String owner, Integer employeeCount, String createdAt, String updatedAt) {
        this.id = id;
        this.regNo = regNo;
        this.location = location;
        this.owner = owner;
        this.employeeCount = employeeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    @Override
    public String toString() {
        return "fuelStation{" +
                "id=" + id +
                ", regNo='" + regNo + '\'' +
                ", location='" + location + '\'' +
                ", owner='" + owner + '\'' +
                ", employeeCount=" + employeeCount +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
