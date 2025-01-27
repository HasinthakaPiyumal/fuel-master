package com.uokse.fuelmaster.dto;

import java.time.LocalDate;

public class QuotaSummaryDTO {
    private Long defaultQuota;
    private Long usedQuota;
    private Long availableQuota;
    private LocalDate renewalDate;
    private String message;

    public QuotaSummaryDTO(Long defaultQuota, Long usedQuota, Long availableQuota, LocalDate renewalDate) {
        this.defaultQuota = defaultQuota;
        this.usedQuota = usedQuota;
        this.availableQuota = availableQuota;
        this.renewalDate = renewalDate;
    }

    // Constructor for error message
    public QuotaSummaryDTO(String message) {
        this.message = message;
    }

    // Getters and Setters


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

    public Long getAvailableQuota() {
        return availableQuota;
    }

    public void setAvailableQuota(Long availableQuota) {
        this.availableQuota = availableQuota;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
    }
}
