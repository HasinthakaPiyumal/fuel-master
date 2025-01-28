package com.uokse.fuelmaster.model;

import jakarta.persistence.*;

@Entity
@Table(name="vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="vehicleType_id",nullable = false)
    private VehicleType vehicleType;
    @Column(nullable = false)
    private String vehicleRegistrationPart1;

    @Column(nullable = false)
    private Long vehicleRegistrationPart2;

    @Column(nullable = false, unique = true)
    private String chassisNumber;

    @Column
    private String qrId;



    public Vehicle(Long id, User user, VehicleType vehicleType, String vehicleRegistrationPart1, Long vehicleRegistrationPart2, String chassisNumber, String qrId) {
        this.id = id;
        this.user = user;
        this.vehicleType = vehicleType;
        this.vehicleRegistrationPart1 = vehicleRegistrationPart1;
        this.vehicleRegistrationPart2 = vehicleRegistrationPart2;
        this.chassisNumber = chassisNumber;
        this.qrId= qrId;
    }


    public Vehicle() {
    }

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

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getQrId() {
        return qrId;
    }

    public void setQrId(String qrId) {
        this.qrId = qrId;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", user=" + user +
                ", vehicleType=" + vehicleType +
                ", vehicleRegistrationPart1='" + vehicleRegistrationPart1 + '\'' +
                ", vehicleRegistrationPart2=" + vehicleRegistrationPart2 +
                ", chassisNumber='" + chassisNumber + '\'' +
                ", qrId='" + qrId + '\'' +
                '}';
    }

}
