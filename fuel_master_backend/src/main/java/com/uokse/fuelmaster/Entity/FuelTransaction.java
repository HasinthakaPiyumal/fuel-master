package com.uokse.fuelmaster.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FuelTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "fuel_station_id", nullable = false)
    private fuelStation fuelStation;

    @Column(nullable = false)
    private Double pumpedQuantity;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false)
    private Double availableQuantity;


    public FuelTransaction(Long id, Vehicle vehicle, Employee employee, com.uokse.fuelmaster.Entity.fuelStation fuelStation, Double pumpedQuantity, LocalDateTime transactionDate, Double availableQuantity) {
        this.id = id;
        this.vehicle = vehicle;
        this.employee = employee;
        this.fuelStation = fuelStation;
        this.pumpedQuantity = pumpedQuantity;
        this.transactionDate = transactionDate;
        this.availableQuantity = availableQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public com.uokse.fuelmaster.Entity.fuelStation getFuelStation() {
        return fuelStation;
    }

    public void setFuelStation(com.uokse.fuelmaster.Entity.fuelStation fuelStation) {
        this.fuelStation = fuelStation;
    }

    public Double getPumpedQuantity() {
        return pumpedQuantity;
    }

    public void setPumpedQuantity(Double pumpedQuantity) {
        this.pumpedQuantity = pumpedQuantity;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Double availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @Override
    public String toString() {
        return "FuelTransaction{" +
                "id=" + id +
                ", vehicle=" + vehicle +
                ", employee=" + employee +
                ", fuelStation=" + fuelStation +
                ", pumpedQuantity=" + pumpedQuantity +
                ", transactionDate=" + transactionDate +
                ", availableQuantity=" + availableQuantity +
                '}';
    }
}
