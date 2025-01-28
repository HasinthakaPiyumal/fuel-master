package com.uokse.fuelmaster.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "fuel_quota")
public class FuelQuota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false)
    private Long defaultQuota; // Total quota

    @Column(nullable = false)
    private Long usedQuota; // Track used quota

    @Column(nullable = false)
    private LocalDate quotaDate; // Date of the quota

    public FuelQuota() {
        this.usedQuota = 0L; // Initialize used quota to 0
    }

    // Getters and Setters

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

    public Long getDefaultQuota() {
        return defaultQuota;
    }

    public void setDefaultQuota(Long defaultQuota) {
        this.defaultQuota = defaultQuota;
    }

    public Long getUsedQuota() {
        return usedQuota;
    }

    public void setUsedQuota(Long usedQuota) {
        this.usedQuota = usedQuota;
    }

    public LocalDate getQuotaDate() {
        return quotaDate;
    }

    public void setQuotaDate(LocalDate quotaDate) {
        this.quotaDate = quotaDate;
    }

    public Long getAvailableQuota() {
        return defaultQuota - usedQuota; // Calculate available quota
    }
}